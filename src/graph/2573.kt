package graph

import java.io.BufferedReader
import java.io.InputStreamReader

fun isIn(y: Int, x: Int): Boolean = (y in 0 until n) && (x in 0 until m)

fun calcMeltAmount(map: Array<IntArray>, meltAmount: Array<IntArray>) {
    for (row in map.indices) {
        meltAmount[row].fill(0)
        for (col in map[row].indices) {
            if (map[row][col] > 0) {
                near.forEach { pt ->
                    val (nearY, nearX) = arrayOf(row + pt.first, col + pt.second)
                    if (isIn(nearY, nearX)) {
                        if (map[nearY][nearX] == 0)
                            meltAmount[row][col]++
                    }
                }
            }
        }
    }
}

fun melt(map: Array<IntArray>, meltAmountRecord: Array<IntArray>) {
    for (row in meltAmountRecord.indices) {
        for (col in meltAmountRecord[row].indices) {
            map[row][col] -= meltAmountRecord[row][col]
            if (map[row][col] < 0) {
                map[row][col] = 0
            }
        }
    }
}

fun bfs(
    map: Array<IntArray>,
    ice: java.util.ArrayDeque<Pair<Int, Int>>,
    visited: Array<BooleanArray>,
) {
    while (!ice.isEmpty()) {
        val curIce = ice.pollFirst()

        if (map[curIce.first][curIce.second] > 0) {
            near.forEach { pt ->
                val (nearY, nearX) = arrayOf(curIce.first + pt.first, curIce.second + pt.second)
                if (isIn(nearY, nearX)) {
                    if (map[nearY][nearX] > 0 && !visited[nearY][nearX]) {
                        visited[nearY][nearX] = true
                        ice.add(Pair(nearY, nearX))
                    }
                }
            }
        }
    }
}

fun calcIceLumpCnt(map: Array<IntArray>, ice: java.util.ArrayDeque<Pair<Int, Int>>, visited: Array<BooleanArray>): Int {
    var lumpCnt = 0

    for (row in map.indices) {
        for (col in map[row].indices) {
            if (map[row][col] > 0 && !visited[row][col]) {
                lumpCnt++
                ice.add(Pair(row, col))
                visited[row][col] = true
                bfs(map, ice, visited)
            }
        }
    }

    return lumpCnt
}

var n = -1
var m = -1
val near = arrayOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (r, c) = readLine().split(" ").map { it.toInt() }
    n = r
    m = c
    val map = Array(n) { IntArray(m) { 0 } }
    val visited = Array(n) { BooleanArray(m) { false } }
    val meltAmount = Array(n) { IntArray(m) { 0 } }
    val ice = java.util.ArrayDeque<Pair<Int, Int>>()

    repeat(n) { row ->
        readLine().split(" ").map { it.toInt() }.forEachIndexed { col, year ->
            map[row][col] = year
        }
    }

    var lumpCnt = 0
    var year = 0
    while (true) {
        calcMeltAmount(map, meltAmount)
        melt(map, meltAmount)
        lumpCnt = calcIceLumpCnt(map, ice, visited)
        year++

        if (lumpCnt > 1) {
            println(year)
            return
        } else if (lumpCnt == 0) {
            println(0)
            return
        }

        for (row in visited.indices) {
            visited[row].fill(false)
        }
    }
}

