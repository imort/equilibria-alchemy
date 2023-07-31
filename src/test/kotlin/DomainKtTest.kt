import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
    fun extractLock() {
        assertEquals(
            EventLock(BigInteger.valueOf(20000L), BigInteger.valueOf(4L)),
            buildString {
                append("0x")
                append("00000000000000000000000000000000000000000000043c33c1937564800000")
                append("0000000000000000000000000000000000000000000000000000000000000004")
            }.lock(),
        )
    }
}