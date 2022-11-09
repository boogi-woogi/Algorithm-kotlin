package Graph

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val graph = Array(n) { ArrayList<Int>() }
    val visited = BooleanArray(n) { false }

    repeat(m) {
        val (nodeA, nodeB) = readLine().split(" ").map { it.toInt() }
        graph[nodeA].add(nodeB)
        graph[nodeB].add(nodeA)
    }

    fun dfs(
        depth: Int,
        cur: Int,
    ): Boolean {
        if (depth == 4) return true
        graph[cur].forEach { next ->
            if (!visited[next]) {
                visited[next] = true
                if (dfs(depth + 1, next)){
                    return true
                }
                visited[next] = false
            }
        }
        return false
    }

    for (friend in 0 until n) {
        visited[friend] = true
        if (dfs(0, friend)) {
            return println(1)
        }
        visited[friend] = false
    }
    println(0)
}
