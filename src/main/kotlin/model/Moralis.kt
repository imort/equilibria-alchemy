package model

import kotlinx.serialization.Serializable

@Serializable
data class MoralisEvent(
    val confirmed: Boolean = false,
    val chainId: Chain = Chain.UNKNOWN,
    val logs: List<MoralisLog> = emptyList(),
)

@Serializable
data class MoralisLog(
    val address: String,
    val data: String,
    val topic0: String,
    val topic1: String? = null,
    val topic2: String? = null,
    val topic3: String? = null,
)
