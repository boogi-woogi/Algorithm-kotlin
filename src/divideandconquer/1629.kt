package divideandconquer

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.math.BigInteger

//분할정복
fun pow(a: BigInteger, b: BigInteger, c: BigInteger): BigInteger {
    if (b == 0.toBigInteger()) return 1.toBigInteger()
    if (b == 1.toBigInteger()) return a % c

    val remainder = pow(a, b / 2.toBigInteger(), c) % c

    return if (b % 2.toBigInteger() == 0.toBigInteger()) {
        remainder * remainder % c
    } else {
        remainder * remainder * (a % c) % c
    }
}

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (a, b, c) = readLine().split(" ").map { it.toBigInteger() }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))

    bw.write("${pow(a, b, c)}")
    bw.flush()
    bw.close()
}

