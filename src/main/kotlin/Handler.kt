interface Handler {
    suspend fun dispatch(events: List<Event>)
}