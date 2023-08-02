import model.AlchemyLog
import model.MoralisLog
import java.math.BigDecimal
import java.math.BigInteger

sealed class Event {
    abstract val network: String
    abstract val address: String
    abstract val amount: BigDecimal

    data class LockCreated(
        override val network: String,
        override val address: String,
        override val amount: BigDecimal,
        val duration: BigInteger,
    ) : Event() {
        override fun toString() =
            "${address.mask()} just locked ${amount.format2()} EQB for $duration week(s) on $network"
    }

    data class LockExtended(
        override val network: String,
        override val address: String,
        override val amount: BigDecimal,
        val oldWeeks: BigInteger,
        val newWeeks: BigInteger,
    ) : Event() {
        override fun toString() =
            "${address.mask()} just extended ${amount.format2()} EQB $oldWeeks->$newWeeks week(s) on $network"
    }

    companion object {
        private const val LOCK_CREATED = "0x167357c41e38a45e1950f61b1f5accf902c878d83f1685f7f72fb666203ce047"
        private const val LOCK_EXTENDED = "0x2880f4fc08dec6bc3c61687b882e88598cb950c1dbff72d4a2d20223174215b4"
        //private const val UNLOCK = "0x3f2f29fa02cc34566ac167b446be0be9e0254cac18eda93b2dfe6a7a7c8affb9"

        fun from(network: String, log: AlchemyLog): Event {
            val topic = log.topics[0]
            val address = log.topics[1].address()
            val data = log.data
            return from(network, topic, address, data)
        }

        fun from(network: String, log: MoralisLog): Event {
            val topic = log.topic0
            val address = log.topic1?.address() ?: error("Missing topic1 as address in $log")
            val data = log.data
            return from(network, topic, address, data)
        }

        private fun from(network: String, topic: String, address: String, data: String): Event {
            return when (topic) {
                LOCK_CREATED -> {
                    val (amount, duration) = DataLockCreated(data)
                    LockCreated(
                        network = network,
                        address = address,
                        amount = amount,
                        duration = duration,
                    )
                }

                LOCK_EXTENDED -> {
                    val (amount, oldWeeks, newWeeks) = DataLockExtended(data)
                    LockExtended(
                        network = network,
                        address = address,
                        amount = amount,
                        oldWeeks = oldWeeks,
                        newWeeks = newWeeks,
                    )
                }

                else -> error("Unknown topic[0]: $topic")
            }
        }
    }
}

internal data class DataLockCreated(val amount: BigDecimal, val duration: BigInteger) {
    constructor(data: String) : this(
        amount = data.substring(2, 66).uint256().asAmount(),
        duration = data.substring(66, 130).uint256(),
    )
}

internal data class DataLockExtended(val amount: BigDecimal, val oldWeeks: BigInteger, val newWeeks: BigInteger) {
    constructor(data: String) : this(
        amount = data.substring(2, 66).uint256().asAmount(),
        oldWeeks = data.substring(66, 130).uint256(),
        newWeeks = data.substring(130, 194).uint256(),
    )
}

internal fun String.address() = "0x${substring(26)}"
internal fun String.mask() = if (length > 12) replace(substring(6, length - 4), "..") else this
internal fun String.uint256() = BigInteger(this, 16)
internal fun BigInteger.asAmount() = toBigDecimal().setScale(18) / BigDecimal.TEN.pow(18)
internal fun BigDecimal.format2() = "%.2f".format(this)
