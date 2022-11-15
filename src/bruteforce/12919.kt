package bruteforce

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val s = readLine()
    val k = readLine()
    var isChangeable = false

    fun dfs(len: Int, cur: String) {
        if (len == s.length) {
            if (cur == s) {
                isChangeable = true
            }
            return
        }

        if (cur.first() == 'B') {
            val next = cur.substring(1, cur.length).reversed()
            dfs(next.length, next)
        }

        if (cur.last() == 'A') {
            val next = cur.substring(0, cur.length - 1)
            dfs(next.length, next)
        }
    }
    dfs(k.length, k)

    if (isChangeable) {
        println(1)
    } else {
        println(0)
    }
}