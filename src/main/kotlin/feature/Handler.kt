package feature

import EventLog

interface Handler {
    suspend fun dispatch(event: EventLog)
}