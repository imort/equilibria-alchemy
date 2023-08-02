import kotlinx.serialization.json.Json

object Dependencies {
    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
}