package graph

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    data class Record(
        val y: Int,
        val x: Int,
        val cnt: Int,
    )
    val (r, c) = readLine().split(" ").map { it.toInt() }
    val arr = Array(r) { CharArray(c) { '.' } }
    val burned = Array(r) { IntArray(c) { Int.MAX_VALUE } }
    val visited = Array(r) { BooleanArray(c) { false } }
    val mv = arrayOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
    val queue = ArrayDeque<Record>()
    var jihoon = Record(-1, -1, 0)

    repeat(r) { row ->
        readLine().forEachIndexed { col, c ->
            when (c) {
                'F' -> {
                    queue.add(Record(row, col, 0))
                    burned[row][col] = 0
                }
                'J' -> {
                    jihoon = Record(row, col, 0)
                    visited[row][col] = true
                }
            }
            arr[row][col] = c
        }
    }

    fun isIn(y: Int, x: Int): Boolean = (y in 0 until r) && (x in 0 until c)

    fun burning(q: ArrayDeque<Record>) {
        while (!q.isEmpty()) {
            val cur = q.pollFirst()

            mv.forEach { pt ->
                val (ny, nx) = arrayOf(cur.y + pt.first, cur.x + pt.second)
                if (isIn(ny, nx)) {
                    if (arr[ny][nx] != '#' && burned[ny][nx] > cur.cnt + 1) {
                        burned[ny][nx] = cur.cnt + 1
                        q.addLast(Record(ny, nx, cur.cnt + 1))
                    }
                }
            }
        }
    }

    fun escape(q: ArrayDeque<Record>): Int {
        while (!q.isEmpty()) {
            val cur = q.pollFirst()

            mv.forEach { pt ->
                val (ny, nx) = arrayOf(cur.y + pt.first, cur.x + pt.second)
                if (isIn(ny, nx)) {
                    if (burned[ny][nx] > cur.cnt + 1 && !visited[ny][nx] && arr[ny][nx] == '.') {
                        visited[ny][nx] = true
                        q.addLast(Record(ny, nx, cur.cnt + 1))
                    }
                } else {
                    return cur.cnt + 1
                }
            }
        }
        return -1
    }

    burning(queue)
    queue.add(jihoon)
    val cnt = escape(queue)
    if (cnt == -1) {
        println("IMPOSSIBLE")
        return
    }
    println(cnt)
}