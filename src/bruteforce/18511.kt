package bruteforce

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Integer.max

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, k) = readLine().split(" ").map { it.toInt() }
    var numberSet = readLine().split(" ").map { it.toInt() }.sortedDescending()
    var answer:Int=Int.MIN_VALUE

    fun dfs(number: String) {
        var curNumber = number

        numberSet.forEach { numberInSet ->
            val nextNumber = (curNumber + numberInSet.toString()).toInt()

            if (nextNumber <= n) {
                dfs(nextNumber.toString())
            }else{
                answer=max(answer, curNumber.toInt())
            }
        }
    }
    dfs("")
    println(answer.toString())
}