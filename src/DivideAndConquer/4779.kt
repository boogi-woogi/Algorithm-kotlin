package DivideAndConquer

import java.util.*

fun cantor(s: String): String {
    return s + " ".repeat(s.length) + s
}

fun main() {
    val sc = Scanner(System.`in`)
    while (sc.hasNextInt()) {
        val n = sc.nextInt()
        var answer = "-"

        repeat(n) {
            answer = cantor(answer)
        }
        println(answer)
    }
}
