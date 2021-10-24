package id.nanoid

/**
 * Predefined character sets per https://github.com/CyberAP/nanoid-dictionary
 */
object Alphabets {
    val numbers = "0123456789"
    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val alphanumeric = numbers + lowercase + uppercase
    val hexadecimalLowercase = numbers + "abcdef"
    val hexadecimalUppercase = numbers + "ABCDEF"
    val nolookalikes = "346789ABCDEFGHJKLMNPQRTUVWXYabcdefghijkmnpqrtwxyz"
    val nolookalikesSafe = "6789BCDFGHJKLMNPQRTWbcdfghjkmnpqrtwz"
}
