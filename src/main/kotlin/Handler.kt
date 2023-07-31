interface Handler {
    suspend fun dispatch(events: List<EventLog>)
}