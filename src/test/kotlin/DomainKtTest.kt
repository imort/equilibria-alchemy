import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class DomainKtTest {
    @Test
    fun address() {
        assertEquals(
            "0xdde05da1122494c9af1694b377adbb43b47582c9",
            "0x000000000000000000000000dde05da1122494c9af1694b377adbb43b47582c9".address(),
        )
    }

    @Test
    fun lockCreated() {
        assertEquals(
            DataLockCreated(
                amount = BigDecimal("329.777955229913430660"),
                duration = BigInteger.valueOf(45),
            ),
            DataLockCreated(buildString {
                append("0x")
                append("000000000000000000000000000000000000000000000011e096a70b09a9ba84")
                append("000000000000000000000000000000000000000000000000000000000000002d")
            }),
        )
    }

    @Test
    fun lockExtended() {
        assertEquals(
            DataLockExtended(
                amount = BigDecimal("329.777955229913430660"),
                oldWeeks = BigInteger.valueOf(45),
                newWeeks = BigInteger.valueOf(52),
            ),
            DataLockExtended(buildString {
                append("0x")
                append("000000000000000000000000000000000000000000000011e096a70b09a9ba84")
                append("000000000000000000000000000000000000000000000000000000000000002d")
                append("0000000000000000000000000000000000000000000000000000000000000034")
            }),
        )
    }

    @Test
    fun format() {
        val log = Event.LockCreated(
            network = "eth",
            address = "0xdde05da1122494c9af1694b377adbb43b47582c9",
            amount = BigDecimal.valueOf(1234.56789),
            duration = BigInteger.TEN,
        )
        assertEquals("0xdde0..82c9 just locked 1234.57 EQB for 10 week(s) on eth", log.toString())
    }
}