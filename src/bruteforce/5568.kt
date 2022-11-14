package bruteforce

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val k = readLine().toInt()
    val card = IntArray(n) { 0 }
    val chosen = BooleanArray(n) { false }
    val makingNumber = arrayListOf<Int>()
    val answer = mutableSetOf<Int>()

    repeat(n) { i ->
        card[i] = readLine().toInt()
    }

    fun dfs(depth: Int) {
        if (depth == k) {
            answer.add(makingNumber.joinToString("").toInt())
            return
        }

        for (i in card.indices) {
            if (!chosen[i]) {
                chosen[i] = true
                makingNumber.add(card[i])
                dfs(depth + 1)
                chosen[i] = false
                makingNumber.removeLast()
            }
        }
    }

    dfs(0)
    println(answer.size)
}