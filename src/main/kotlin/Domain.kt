import java.math.BigDecimal
import java.math.BigInteger

data class EventLog(
    val network: String,
    val address: String,
    val lock: EventLock,
) {
    override fun toString() =
        "${address.mask()} just locked ${"%.2f".format(lock.value)} EQB for ${lock.duration} week(s) on $network"

    companion object {
        fun from(network: String, log: AlchemyLog): EventLog = EventLog(
            network = network,
            address = log.topics[1].address(),
            lock = log.data.lock(),
        )
    }
}

data class EventLock(val value: BigDecimal, val duration: BigInteger)

internal fun String.address() = "0x${substring(26)}"
internal fun String.mask() = if (length > 12) replace(substring(6, length - 4), "..") else this
private fun String.uint256() = BigInteger(this, 16)
internal fun String.lock() = EventLock(
    value = substring(2, 66).uint256().toBigDecimal() / BigInteger.TEN.pow(18).toBigDecimal(),
    duration = substring(66, 130).uint256(),
)

