import kotlinx.serialization.Serializable

@Serializable
data class AlchemyInfo(val event: AlchemyEvent = AlchemyEvent())

@Serializable
data class AlchemyEvent(val data: AlchemyData = AlchemyData())

@Serializable
data class AlchemyData(val block: AlchemyBlock = AlchemyBlock())

@Serializable
data class AlchemyBlock(val logs: List<AlchemyLog> = emptyList())

@Serializable
data class AlchemyLog(val data: String, val topics: List<String>)
