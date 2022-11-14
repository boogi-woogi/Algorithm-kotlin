package graph

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val graph = IntArray(n + 1) { 0 }
    val visited = ArrayList<Int>()
    val answer = mutableSetOf<Int>()

    repeat(n) { i ->
        graph[i + 1] = readLine().toInt()
    }

    fun dfs(cur: Int) {
        val next = graph[cur]

        if (visited.contains(next)) {
            when (next) {
                cur -> answer.add(next)
                else -> {
                    val visitedIndex = visited.withIndex().first { num -> num.value == next }.index
                    for (i in visitedIndex until visited.size) {
                        answer.add(visited[i])
                    }
                }
            }
            return
        } else if (!answer.contains(next)) {
            visited.add(next)
            dfs(next)
        }
    }

    for (num in 1..n) {
        if (!answer.contains(num)) {
            visited.add(num)
            dfs(num)
            visited.clear()
        }
    }

    println(answer.size)
    answer.sorted().forEach {
        println(it)
    }
}