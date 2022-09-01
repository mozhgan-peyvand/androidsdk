package ir.part.sdk.ara.base.event

import kotlinx.coroutines.flow.MutableSharedFlow


object MeratEventPublisher {
    val events = MutableSharedFlow<MeratEvent>(extraBufferCapacity = 1)
    fun publishEvent(officeEvent: MeratEvent) {
        events.tryEmit(officeEvent)
    }
}