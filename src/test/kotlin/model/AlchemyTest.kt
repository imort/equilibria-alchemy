package model

import Dependencies
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AlchemyTest {
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
                    "logs": [
                      {
                        "data": "0x0000000000000000000000000000000000000000000000af434cb42aa0c977ff0000000000000000000000000000000000000000000000000000000000000033",
                        "topics": [
                          "0x167357c41e38a45e1950f61b1f5accf902c878d83f1685f7f72fb666203ce047",
                          "0x000000000000000000000000487d37eb22656b5a0d983f37f6cdd75a299d0a8f"
                        ],
                        "index": 636,
                        "account": {
                          "address": "0xd8967b2b15b3cdf96039b7407813b4037f73ec27"
                        },
                        "transaction": {
                          "hash": "0x6993a08d5ade03982ac8967602a61c49ace177d976547de1951c10e00a325e6c",
                          "nonce": 149,
                          "index": 149,
                          "from": {
                            "address": "0x487d37eb22656b5a0d983f37f6cdd75a299d0a8f"
                          },
                          "to": {
                            "address": "0xd8967b2b15b3cdf96039b7407813b4037f73ec27"
                          },
                          "value": "0x0",
                          "gasPrice": "0x689655670",
                          "maxFeePerGas": "0x8e7c17b8b",
                          "maxPriorityFeePerGas": "0x59a5380",
                          "gas": 645421,
                          "status": 1,
                          "gasUsed": 626045,
                          "cumulativeGasUsed": 19795202,
                          "effectiveGasPrice": "0x689655670",
                          "createdContract": null
                        }
                      }
                    ]
                  }
                },
                "sequenceNumber": "10000000000578619000"
              }
            }
        """.trimIndent()

        val expected = AlchemyInfo(
            AlchemyEvent(
                AlchemyData(
                    AlchemyBlock(
                        logs = listOf(
                            AlchemyLog(
                                data = "0x0000000000000000000000000000000000000000000000af434cb42aa0c977ff0000000000000000000000000000000000000000000000000000000000000033",
                                topics = listOf(
                                    "0x167357c41e38a45e1950f61b1f5accf902c878d83f1685f7f72fb666203ce047",
                                    "0x000000000000000000000000487d37eb22656b5a0d983f37f6cdd75a299d0a8f",
                                ),
                            ),
                        ),
                    )
                )
            )
        )

        assertEquals(expected, Dependencies.json.decodeFromString<AlchemyInfo>(content))
    }
}