package graph

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    data class Monkey(
        val y: Int,
        val x: Int,
        val k: Int,
        val cnt: Int,
    )

    val k = readLine().toInt()
    val (w, h) = readLine().split(" ").map { it.toInt() }
    val map = Array(h) { IntArray(w) { 0 } }
    val visited = Array(k + 1) { Array(h) { BooleanArray(w) { false } } }
    val mv = arrayOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

    val horseMv = arrayOf(
        Pair(-1, -2), Pair(-1, 2), Pair(-2, -1), Pair(-2, 1),
        Pair(1, -2), Pair(1, 2), Pair(2, -1), Pair(2, 1))
    val q: Queue<Monkey> = LinkedList()
    fun isIn(y: Int, x: Int) = (y in 0 until h) && (x in 0 until w)

    repeat(h) { i ->
        map[i] = readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    fun bfs(): Int {
        while (!q.isEmpty()) {
            val curMonkey = q.poll()

            if (curMonkey.y == h - 1 && curMonkey.x == w - 1) {
                return curMonkey.cnt
            }

            if (curMonkey.k > 0) {
                horseMv.forEach { pt ->
                    val (ny, nx) = arrayOf(curMonkey.y + pt.first, curMonkey.x + pt.second)

                    if (isIn(ny, nx)) {
                        if (map[ny][nx] != 1 && !visited[curMonkey.k - 1][ny][nx]) {
                            visited[curMonkey.k - 1][ny][nx] = true
                            q.add(Monkey(ny, nx, curMonkey.k - 1, curMonkey.cnt + 1))
                        }
                    }
                }
            }

            mv.forEach { pt ->
                val (ny, nx) = arrayOf(curMonkey.y + pt.first, curMonkey.x + pt.second)

                if (isIn(ny, nx)) {
                    if (map[ny][nx] != 1 && !visited[curMonkey.k][ny][nx]) {
                        visited[curMonkey.k][ny][nx] = true
                        q.add(Monkey(ny, nx, curMonkey.k, curMonkey.cnt + 1))
                    }
                }
            }
        }
        return -1
    }

    visited[k][0][0] = true
    q.add(Monkey(0, 0, k, 0))
    println(bfs())
}