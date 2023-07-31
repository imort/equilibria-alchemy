import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class AlchemyInfo(val event: AlchemyEvent)

@Serializable
data class AlchemyEvent(val data: AlchemyData)

@Serializable
data class AlchemyData(val logs: List<AlchemyLog>)

@Serializable
data class AlchemyLog(val data: String, val topics: List<String>)
