package model

import Dependencies
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MoralisTest {
    @Test
    fun test() {
        @Language("JSON")
        val content = """
            {  
              "abi": {},  
              "block": {  
                "hash": "",  
                "number": "",  
                "timestamp": ""  
              },  
              "txs": [],  
              "txsInternal": [],  
              "logs": [],  
              "chainId": "",  
              "tag": "",  
              "streamId": "",  
              "confirmed": true,  
              "retries": 0,  
              "erc20Approvals": [],  
              "erc20Transfers": [],  
              "nftApprovals": [],  
              "nftTransfers": []  
            }
        """.trimIndent()

        val expected = MoralisEvent(confirmed = true)

        assertEquals(expected, Dependencies.json.decodeFromString<MoralisEvent>(content))
    }

    @Test
    fun deserialize() {
        @Language("JSON")
        val content = """
            {
                "confirmed": true,
                "chainId": "0xa4b1",
                "abi": [
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_address",
                                "type": "address",
                                "abi.1.inputs.0.type": "address",
                                "abi.1.inputs.0.name": "_address",
                                "abi.1.inputs.0.internalType": "address"
                            },
                            {
                                "indexed": false,
                                "internalType": "bool",
                                "name": "_status",
                                "type": "bool",
                                "abi.1.inputs.1.type": "bool",
                                "abi.1.inputs.1.name": "_status",
                                "abi.1.inputs.1.internalType": "bool"
                            }
                        ],
                        "name": "AccessSet",
                        "type": "event",
                        "abi.1.type": "event",
                        "abi.1.name": "AccessSet"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": false,
                                "internalType": "uint8",
                                "name": "version",
                                "type": "uint8",
                                "abi.2.inputs.0.type": "uint8",
                                "abi.2.inputs.0.name": "version",
                                "abi.2.inputs.0.internalType": "uint8"
                            }
                        ],
                        "name": "Initialized",
                        "type": "event",
                        "abi.2.type": "event",
                        "abi.2.name": "Initialized"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_user",
                                "type": "address",
                                "abi.3.inputs.0.type": "address",
                                "abi.3.inputs.0.name": "_user",
                                "abi.3.inputs.0.internalType": "address"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_amount",
                                "type": "uint256",
                                "abi.3.inputs.1.type": "uint256",
                                "abi.3.inputs.1.name": "_amount",
                                "abi.3.inputs.1.internalType": "uint256"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_weeks",
                                "type": "uint256",
                                "abi.3.inputs.2.type": "uint256",
                                "abi.3.inputs.2.name": "_weeks",
                                "abi.3.inputs.2.internalType": "uint256"
                            }
                        ],
                        "name": "LockCreated",
                        "type": "event",
                        "abi.3.type": "event",
                        "abi.3.name": "LockCreated"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_user",
                                "type": "address",
                                "abi.4.inputs.0.type": "address",
                                "abi.4.inputs.0.name": "_user",
                                "abi.4.inputs.0.internalType": "address"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_amount",
                                "type": "uint256",
                                "abi.4.inputs.1.type": "uint256",
                                "abi.4.inputs.1.name": "_amount",
                                "abi.4.inputs.1.internalType": "uint256"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_oldWeeks",
                                "type": "uint256",
                                "abi.4.inputs.2.type": "uint256",
                                "abi.4.inputs.2.name": "_oldWeeks",
                                "abi.4.inputs.2.internalType": "uint256"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_newWeeks",
                                "type": "uint256",
                                "abi.4.inputs.3.type": "uint256",
                                "abi.4.inputs.3.name": "_newWeeks",
                                "abi.4.inputs.3.internalType": "uint256"
                            }
                        ],
                        "name": "LockExtended",
                        "type": "event",
                        "abi.4.type": "event",
                        "abi.4.name": "LockExtended"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "previousOwner",
                                "type": "address",
                                "abi.5.inputs.0.type": "address",
                                "abi.5.inputs.0.name": "previousOwner",
                                "abi.5.inputs.0.internalType": "address"
                            },
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "newOwner",
                                "type": "address",
                                "abi.5.inputs.1.type": "address",
                                "abi.5.inputs.1.name": "newOwner",
                                "abi.5.inputs.1.internalType": "address"
                            }
                        ],
                        "name": "OwnershipTransferred",
                        "type": "event",
                        "abi.5.type": "event",
                        "abi.5.name": "OwnershipTransferred"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_rewardToken",
                                "type": "address",
                                "abi.6.inputs.0.type": "address",
                                "abi.6.inputs.0.name": "_rewardToken",
                                "abi.6.inputs.0.internalType": "address"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_reward",
                                "type": "uint256",
                                "abi.6.inputs.1.type": "uint256",
                                "abi.6.inputs.1.name": "_reward",
                                "abi.6.inputs.1.internalType": "uint256"
                            }
                        ],
                        "name": "RewardAdded",
                        "type": "event",
                        "abi.6.type": "event",
                        "abi.6.name": "RewardAdded"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_user",
                                "type": "address",
                                "abi.7.inputs.0.type": "address",
                                "abi.7.inputs.0.name": "_user",
                                "abi.7.inputs.0.internalType": "address"
                            },
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_rewardToken",
                                "type": "address",
                                "abi.7.inputs.1.type": "address",
                                "abi.7.inputs.1.name": "_rewardToken",
                                "abi.7.inputs.1.internalType": "address"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_reward",
                                "type": "uint256",
                                "abi.7.inputs.2.type": "uint256",
                                "abi.7.inputs.2.name": "_reward",
                                "abi.7.inputs.2.internalType": "uint256"
                            }
                        ],
                        "name": "RewardPaid",
                        "type": "event",
                        "abi.7.type": "event",
                        "abi.7.name": "RewardPaid"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_rewardToken",
                                "type": "address",
                                "abi.8.inputs.0.type": "address",
                                "abi.8.inputs.0.name": "_rewardToken",
                                "abi.8.inputs.0.internalType": "address"
                            }
                        ],
                        "name": "RewardTokenAdded",
                        "type": "event",
                        "abi.8.type": "event",
                        "abi.8.name": "RewardTokenAdded"
                    },
                    {
                        "anonymous": false,
                        "inputs": [
                            {
                                "indexed": true,
                                "internalType": "address",
                                "name": "_user",
                                "type": "address",
                                "abi.9.inputs.0.type": "address",
                                "abi.9.inputs.0.name": "_user",
                                "abi.9.inputs.0.internalType": "address"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_amount",
                                "type": "uint256",
                                "abi.9.inputs.1.type": "uint256",
                                "abi.9.inputs.1.name": "_amount",
                                "abi.9.inputs.1.internalType": "uint256"
                            },
                            {
                                "indexed": false,
                                "internalType": "uint256",
                                "name": "_lastUnlockedWeek",
                                "type": "uint256",
                                "abi.9.inputs.2.type": "uint256",
                                "abi.9.inputs.2.name": "_lastUnlockedWeek",
                                "abi.9.inputs.2.internalType": "uint256"
                            }
                        ],
                        "name": "Unlocked",
                        "type": "event",
                        "abi.9.type": "event",
                        "abi.9.name": "Unlocked"
                    }
                ],
                "streamId": "ca2f917e-6ca2-41ae-afcf-acc1c908d0ce",
                "tag": "lock",
                "retries": 0,
                "block": {
                    "number": "117394610",
                    "hash": "0xeb7982b8029b8abc20cbc7d0a2617cb6c1969875231375267bf08781186632ec",
                    "timestamp": "1690974416"
                },
                "logs": [
                    {
                        "logIndex": "2",
                        "transactionHash": "0x00cd3e6307fdd834c7d2abd298a9e8204a57295a3854247f8e19c112673abf21",
                        "address": "0x70f61901658aafb7ae57da0c30695ce4417e72b9",
                        "data": "0x0000000000000000000000000000000000000000000000070c1cc73b00c800000000000000000000000000000000000000000000000000000000000000000034",
                        "topic0": "0x167357c41e38a45e1950f61b1f5accf902c878d83f1685f7f72fb666203ce047",
                        "topic1": "0x0000000000000000000000008907bd00491d0b830c38276acc6d72e2419135a2",
                        "topic2": null,
                        "topic3": null
                    }
                ],
                "txs": [],
                "txsInternal": [],
                "erc20Transfers": [],
                "erc20Approvals": [],
                "nftTokenApprovals": [],
                "nftApprovals": {
                    "ERC721": [],
                    "ERC1155": []
                },
                "nftTransfers": [],
                "nativeBalances": []
            }
        """.trimIndent()

        val expected = MoralisEvent(
            confirmed = true,
            chainId = Chain.ARB,
            logs = listOf(
                MoralisLog(
                    address = "0x70f61901658aafb7ae57da0c30695ce4417e72b9",
                    data = "0x0000000000000000000000000000000000000000000000070c1cc73b00c800000000000000000000000000000000000000000000000000000000000000000034",
                    topic0 = "0x167357c41e38a45e1950f61b1f5accf902c878d83f1685f7f72fb666203ce047",
                    topic1 = "0x0000000000000000000000008907bd00491d0b830c38276acc6d72e2419135a2",
                )
            )
        )

        assertEquals(expected, Dependencies.json.decodeFromString<MoralisEvent>(content))
    }
}