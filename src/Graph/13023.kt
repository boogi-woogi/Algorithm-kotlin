package Graph

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val friendRelationRecord = Array(n) { ArrayList<Int>() }
    val isChecked = BooleanArray(n) { false }

    repeat(m) {
        val (friendA, friendB) = readLine().split(" ").map { it.toInt() }
        friendRelationRecord[friendA].add(friendB)
        friendRelationRecord[friendB].add(friendA)
    }

    fun dfs(
        depth: Int,
        curFriend: Int,
    ): Boolean {
        if (depth == 4) return true
        friendRelationRecord[curFriend].forEach { nextFriend ->
            if (!isChecked[nextFriend]) {
                isChecked[nextFriend] = true
                if (dfs(depth + 1, nextFriend)){
                    return true
                }
                isChecked[nextFriend] = false
            }
        }
        return false
    }

    for (friend in 0 until n) {
        isChecked[friend] = true
        if (dfs(0, friend)) {
            return println(1)
        }
        isChecked[friend] = false
    }
    println(0)
}
