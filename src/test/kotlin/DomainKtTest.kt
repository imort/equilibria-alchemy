import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

class DomainKtTest {
    @Test
    fun extractLock() {
        assertEquals(
            EventLock(BigInteger.valueOf(20000L), BigInteger.valueOf(4L)),
            buildString {
                append("0x")
                append("00000000000000000000000000000000000000000000043c33c1937564800000")
                append("0000000000000000000000000000000000000000000000000000000000000004")
            }.extractLock(),
        )
    }
}