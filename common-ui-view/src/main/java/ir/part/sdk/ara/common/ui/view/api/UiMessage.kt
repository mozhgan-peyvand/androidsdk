/*
 * Copyright 2019 Google LLC
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

package ir.part.sdk.ara.common.ui.view.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class UiMessage(
    val id: String,
    val message: String,
    val code: String,
    val icon: Int
)

class UiMessageManager {
    private val mutex = Mutex()

    private val _messages = MutableStateFlow(emptyList<UiMessage>())

    /**
     * A flow emitting the current message to display.
     */
    val message: Flow<UiMessage?> = _messages.map { it.firstOrNull() }.distinctUntilChanged()

//    suspend fun emitMessage(message: UiMessage) {
//        mutex.withLock {
//            _messages.value = _messages.value + message
//        }
//    }

    suspend fun emitMessage(message: UiMessage) {
        mutex.withLock {
            if (_messages.value.none { it.id == message.id }) {
                _messages.value =
                    _messages.value + UiMessage(
                        id = message.id,
                        message = message.message,
                        code = message.code,
                        icon = message.icon
                    )
            }
        }
    }

    suspend fun clearMessage(id: String) {
        mutex.withLock {
            _messages.value = _messages.value.filterNot { it.id == id }
        }
    }

    suspend fun clearAllMessage() {
        mutex.withLock {
            _messages.value = listOf()
        }
    }
}
