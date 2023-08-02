package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
enum class Chain(val label: String) {
    @SerialName("0x1")
    ETH("Etherium"),

    @SerialName("0xa4b1")
    ARB("Arbitrum"),

    @SerialName("0x38")
    BSC("Binance Smart Chain"),

    UNKNOWN("Unknown"),
}