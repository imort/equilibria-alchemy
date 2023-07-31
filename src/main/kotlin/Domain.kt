import java.math.BigInteger

data class EventLog(
    val network: String,
    val address: String,
    val lock: EventLock,
) {
    override fun toString() = "$address just locked ${lock.value} for ${lock.duration} week(s) on $network"

    companion object {
        fun from(network: String, log: AlchemyLog): EventLog = EventLog(
            network = network,
            address = log.topics[1].address(),
            lock = log.data.lock(),
        )
    }
}

data class EventLock(val value: BigInteger, val duration: BigInteger)

internal fun String.address() = "0x${substring(26)}"
private fun String.uint256() = BigInteger(this, 16)
internal fun String.lock() = EventLock(
    value = substring(2, 66).uint256() / BigInteger.TEN.pow(18),
    duration = substring(66, 130).uint256(),
)

