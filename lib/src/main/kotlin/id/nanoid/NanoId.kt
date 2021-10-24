package id.nanoid

import java.security.SecureRandom
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.ln

/**
 * Creates cryptographically strong NanoId.
 */
private val DEFAULT_NUMBER_GENERATOR = SecureRandom()

/**
 * Creates URL-friendly NanoId using 64 unique symbols.
 */
private val DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

/**
 * Creates NanoId with slightly more unique values than UUID v4.
 */
private const val DEFAULT_SIZE = 21

/**
 * Maximum size of the alphabet (`255`)
 */
internal const val MAX_ALPHABET_SIZE = 255

/**
 * The string is generated using the given random number generator.
 *
 * @param random   The random number generator.
 * @param alphabet The symbols used in the NanoId String.
 * @param size     The number of symbols in the NanoId.
 * @return A randomly generated NanoId string.
 */
class NanoId(random: Random = DEFAULT_NUMBER_GENERATOR, alphabet: CharArray = DEFAULT_ALPHABET, size: Int = DEFAULT_SIZE) {
    private var id: String? = null
    val value: String get() = "$id"

    override fun toString(): String = id!!

    init {
        require(alphabet.isNotEmpty() && alphabet.size <= MAX_ALPHABET_SIZE) { "alphabet must contain at least 1 and no more than 255 symbols." }
        require(size > 0) { "size must be greater than zero." }

        val mask = (2 shl floor(ln((alphabet.size - 1).toDouble()) / ln(2.0)).toInt()) - 1
        val step = ceil(1.6 * mask * size / alphabet.size).toInt()
        val idBuilder = StringBuilder()

        while (id == null) {
            val bytes = ByteArray(step)
            random.nextBytes(bytes)
            (0 until step).forEach {
                val alphabetIndex = bytes[it].toInt() and mask
                if (alphabetIndex < alphabet.size) {
                    idBuilder.append(alphabet[alphabetIndex])
                    if (idBuilder.length == size) {
                        id = idBuilder.toString()
                        return@forEach
                    }
                }
            }
        }
    }

    companion object {
        fun id(random: Random = DEFAULT_NUMBER_GENERATOR, alphabet: CharArray = DEFAULT_ALPHABET, size: Int = DEFAULT_SIZE): String {
            return NanoId(random, alphabet, size).toString()
        }
    }
}
