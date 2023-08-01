import kotlinx.serialization.json.Json
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ModelKtTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun deserialize() {
        @Language("JSON")
        val content = """
            {
              "webhookId": "wh_1qln1e1th3o1xf0g",
              "id": "whevt_86mg35xgcxzng3yo",
              "createdAt": "2023-07-30T06:44:08.837429567Z",
              "type": "GRAPHQL",
              "event": {
                "data": {
                  "block": {
                    "hash": "0xf6d7095fbd141fa30082d1ecc7a71ca8b9d7359e620470ac54c5276d8e1e5f35",
                    "number": 17804141,
                    "timestamp": 1690699439,
                    "logs": []
                  }
                },
                "sequenceNumber": "10000000000578619000"
              }
            }
        """.trimIndent()
        val expected = AlchemyInfo(AlchemyEvent(AlchemyData()))
        assertEquals(expected, json.decodeFromString<AlchemyInfo>(content))
    }
}