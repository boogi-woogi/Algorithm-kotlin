package graph

import java.io.BufferedReader
import java.io.InputStreamReader

fun calcMeltAmount() {
    for (row in map.indices) {
        meltAmount[row].fill(0)
        for (col in map[row].indices) {
            if (map[row][col] > 0) {
                near.forEach { pt ->
                    val (nearY, nearX) = arrayOf(row + pt.first, col + pt.second)
                    if (map[nearY][nearX] == 0)
                        meltAmount[row][col]++
                }
            }
        }
    }
}

fun melt() {
    for (row in meltAmount.indices) {
        for (col in meltAmount[row].indices) {
            map[row][col] -= meltAmount[row][col]
            if (map[row][col] < 0) {
                map[row][col] = 0
            }
        }
    }
}

fun bfs() {
    while (!ice.isEmpty()) {
        val curIce = ice.pollFirst()

        if (map[curIce.first][curIce.second] > 0) {
            near.forEach { pt ->
                val (nearY, nearX) = arrayOf(curIce.first + pt.first, curIce.second + pt.second)
                if (map[nearY][nearX] > 0 && !visited[nearY][nearX]) {
                    visited[nearY][nearX] = true
                    ice.add(Pair(nearY, nearX))
                }
            }
        }
    }
}

fun calcIceLumpCnt(): Int {
    var lumpCnt = 0

    for (row in map.indices) {
        for (col in map[row].indices) {
            if (map[row][col] > 0 && !visited[row][col]) {
                lumpCnt++
                ice.add(Pair(row, col))
                visited[row][col] = true
                bfs()
            }
        }
    }

    return lumpCnt
}

lateinit var map: Array<IntArray>
lateinit var visited: Array<BooleanArray>
lateinit var meltAmount: Array<IntArray>
val ice = java.util.ArrayDeque<Pair<Int, Int>>()
val near = arrayOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    map = Array(n) { IntArray(m) { 0 } }
    visited = Array(n) { BooleanArray(m) { false } }
    meltAmount = Array(n) { IntArray(m) { 0 } }

    repeat(n) { row ->
        readLine().split(" ").map { it.toInt() }.forEachIndexed { col, year ->
            map[row][col] = year
        }
    }

    var (year, lumpCnt) = arrayOf(0, -1)

    do {
        calcMeltAmount()
        melt()
        year++
        lumpCnt = calcIceLumpCnt()
        for (row in visited.indices) {
            visited[row].fill(false)
        }
    } while (lumpCnt == 1)

    if (lumpCnt > 1) {
        println(year)
    } else if (lumpCnt == 0) {
        println(0)
    }
}

