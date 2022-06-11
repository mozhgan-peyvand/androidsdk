/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.part.sdk.ara.util

import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.model.InvokeError
import ir.part.sdk.ara.base.model.InvokeStarted
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.model.InvokeSuccess
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit


/********************************************************************************/

/**
 * We use this interactor for [/GET/] API requests. Since we suspend a job
 * for getting response from API, we named it suspending work interactor.
 */

//
//abstract class SuspendingWorkInteractor1<P : Any, T>() : SubjectInteractor<P, T>() {
//    override fun createObservable(params: P): Flow<InvokeStatus<T>>
//    = flow {
//        try {
//            withTimeout(3L){
//                emit(InvokeStarted())
//                emit(InvokeSuccess(doWork(params)))
//            }
//        }catch (t: TimeoutCancellationException){
//            emit(InvokeError(Exceptions.TimeoutCancellationException(cause = t)))
//        }
//
//    } /*.catch { t -> emit(InvokeError(Exceptions.IOException(cause = t))) }*/
//
//    abstract suspend fun doWork(params: P): T
//    companion object {
//        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
//    }
//}

/**
 * We use this interactor for doing [/DELETE/], [/POST/] or [/PUT/] job
 * on API or Database, where we only care about
 * status of the job.
 */
abstract class Interactor<in P, T> {

    /**
     * Executes this interactor. This interactor emits three status.
     * Visit [InvokeStatus] for more details about these states.
     * @param params Parameters sent to this interactor.
     * @param timeoutMs Timeout in millis for executing this command.
     */
    operator fun invoke(
        params: P,
        timeoutMs: Long = defaultTimeoutMs,
    ): Flow<InvokeStatus<T>> = flow {
        try {
            withTimeout(timeoutMs) {
                emit(InvokeStarted<T>())

                emit(InvokeSuccess<T>(doWork(params)))
            }
        } catch (t: TimeoutCancellationException) {
            emit(InvokeError<T>(Exceptions.TimeoutCancellationException(cause = t)))
        }
    }.catch { t -> emit(InvokeError<T>(Exceptions.IOException(cause = t))) }

    /**
     * Executes the doWork function synchronously.
     */
    suspend fun executeSync(params: P): T = doWork(params)

    /**
     * A Unit function just for doing a job.
     * @param params Parameters sent to this interactor.
     */
    protected abstract suspend fun doWork(params: P): T

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}

/********************************************************************************/


/**
 * It's useful when we want to select something from db or get
 * it from API and process it at the moment for doing
 * another job.
 * For example : Checking app version before doing a job (API request).
 */
abstract class ResultInteractor<in P, R> {
    /**
     * Executes this interactor.
     * 1. Build a Flow instance.
     * 2. Does a job in [doWork] function and emits its result inside of flow instance.
     *
     * @param params Parameters sent to this interactor.
     * @return a Flow instance.
     */
    operator fun invoke(params: P): Flow<InvokeStatus<R>> = flow {
        emit(InvokeStarted())
        emit(doWork(params))
    }

    /**
     * Executes the doWork function synchronously.
     */
    suspend fun executeSync(params: P): InvokeStatus<R> = doWork(params)

    /**
     * A suspend function for doing our job.
     * @param params Parameters sent to this interactor.
     * @return result of doWork function.
     */
    protected abstract suspend fun doWork(params: P): InvokeStatus<R>
}

/********************************************************************************/

/**
 * We use this interactor for [/GET/] API requests. Since we suspend a job
 * for getting response from API, we named it suspending work interactor.
 */
abstract class SuspendingWorkInteractor<P : Any, T> : SubjectInteractor<P, T>() {
    override fun createObservable(params: P): Flow<InvokeStatus<T>> = flow {
        emit(InvokeStarted())
        emit(doWork(params))
    }

    abstract suspend fun doWork(params: P): InvokeStatus<T>
}

/********************************************************************************/

/**
 * We use this interactor for observing a flow data source. Wait!!, what?
 * Well, for example if we want to select something from database (from DAO for example), we use
 * this kind of interactor.
 */
abstract class SubjectInteractor<P : Any, T> {
    /**
     * Ideally this would be buffer = 0, since we use flatMapLatest below, BUT invoke is not
     * suspending. This means that we can't suspend while flatMapLatest cancels any
     * existing flows. The buffer of 1 means that we can use tryEmit() and buffer the value
     * instead, resulting in mostly the same result.
     */
    private var paramState = MutableSharedFlow<PublicParam<P>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val flow: Flow<InvokeStatus<T>> = paramState
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it.params) }
        .distinctUntilChanged()


    /**
     * Emits parameters sent to this interactor to
     * the SharedFlow [paramState] which defined above.
     *
     * @param publicParam Parameters sent to this interactor.
     */
    private var publicParam: PublicParam<P>? = null
    operator fun invoke(params: P) {
        publicParam = PublicParam(params)

        publicParam?.let {
            paramState.tryEmit(it)
        }
    }

    protected abstract fun createObservable(params: P): Flow<InvokeStatus<T>>
}

data class PublicParam<P>(var params: P, var callCounter: Int = 0)
