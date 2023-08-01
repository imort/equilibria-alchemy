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
    fun lock() {
        assertEquals(
            EventLock(BigDecimal.valueOf(20000), BigInteger.valueOf(4L)),
            buildString {
                append("0x")
                append("00000000000000000000000000000000000000000000043c33c1937564800000")
                append("0000000000000000000000000000000000000000000000000000000000000004")
            }.lock(),
        )
    }

    @Test
    fun format() {
        val log = EventLog(
            network = "eth",
            address = "0xdde05da1122494c9af1694b377adbb43b47582c9",
            lock = EventLock(BigDecimal.valueOf(1234.56789), BigInteger.TEN),
        )
        assertEquals("0xdde0..82c9 just locked 1234.57 EQB for 10 week(s) on eth", log.toString())
    }
}