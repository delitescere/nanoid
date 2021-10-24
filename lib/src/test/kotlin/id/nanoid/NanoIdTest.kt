package id.nanoid

import org.assertj.core.api.Assertions.*
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test
import java.util.*
import java.util.regex.Pattern

class NanoIdTest {
    @Test
    fun shouldBeUnique() {
        val count = 5_000_000  // 5M takes approx 7 seconds
        val list = (0 until count).map { NanoId() }
        assertThat(list.distinct().size).isEqualTo(list.size)
    }

    @Test
    fun shouldUseRNG() {
        val random = Random(0)
        val expectedIds = arrayOf("uOuVSfniU9hXYKTpQ7HxU", "HBg6Fbbj9KP_x0pwbDJqJ", "MOPKLeaHdwx4KgqemBZuQ")
        expectedIds.forEach {
            assertThat(NanoId.id(random)).isEqualTo(it)
        }
    }

    @Test
    fun shouldMatchAlphabetOfVariousSizes() {
        (1..MAX_ALPHABET_SIZE).forEach { symbols ->
            val alphabet = (0 until symbols).map { it.toChar() }.toCharArray()
            val id = NanoId(alphabet = alphabet)
            val patternBuilder = "^[${alphabet.map { Pattern.quote(it.toString()) }}]+$"
            assertThat(id.value).matches(patternBuilder)
        }
    }

    @Test
    fun shouldGenerateVariousSizedNanoIds() {
        for (size in 1..1000) {
            val id = NanoId(size = size)
            assertThat(id.value.length).isEqualTo(size)
        }
    }

    @Test
    fun shouldHaveWellDistributedValues() {
        val count = 1_000_000
        val size = 50
        val distribution = 1 - (0.012 * size) // ok for sizes from 1 to 50
        val alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        val init = alphabet.associateWith { 0 }
        val map = (0 until count)
            .map { NanoId.id(alphabet = alphabet, size = size) }
            .fold(init) { acc, s ->
                acc + s.toCharArray().associateWith {
                    acc[it]!!.plus(1)
                }
            }

        map.values.map { it.toDouble() * alphabet.size / (count * size) }.forEach {
            assertThat(it).isCloseTo(distribution, Offset.offset(0.05))
        }
    }

    @Test
    fun shouldRejectAlphabetTooShort() {
        assertThatIllegalArgumentException().isThrownBy {
            NanoId(alphabet = charArrayOf())
        }
    }

    @Test
    fun shouldRejectAlphabetTooLong() {
        val alphabet = (0..MAX_ALPHABET_SIZE).map { it.toChar() }.toCharArray()
        assertThatIllegalArgumentException().isThrownBy {
            NanoId(alphabet = alphabet)
        }
    }

    @Test
    fun shouldRejectZeroSizeId() {
        assertThatIllegalArgumentException().isThrownBy {
            NanoId(size = 0)
        }
    }

    @Test
    fun shouldRejectNegativeSizeId() {
        assertThatIllegalArgumentException().isThrownBy {
            NanoId(size = -1)
        }
    }
}
