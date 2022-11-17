package bruteforce

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Math.max

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    fun String.tokenizer() = this.split(" ").map { it.toInt() }
    val (n, m) = readLine().tokenizer()
    fun isIn(y: Int, x: Int) = y in 0 until n && x in 0 until m
    val mv = arrayOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
    val visited = Array(n) { BooleanArray(m) { false } }
    val specialShape = arrayOf(
        arrayOf(Pair(0, 1), Pair(1, 1), Pair(0, 2)),
        arrayOf(Pair(1, 0), Pair(2, 0), Pair(1, -1)),
        arrayOf(Pair(1, 0), Pair(2, 0), Pair(1, 1)),
        arrayOf(Pair(0, 1), Pair(-1, 1), Pair(0, 2)),
    )
    val tetromino = Array(n) { IntArray(m) { 0 } }
    var answer = 0

    repeat(n) { i ->
        tetromino[i] = readLine().tokenizer().toIntArray()
    }

    fun dfs(cy: Int, cx: Int, chosenCnt: Int, curScore: Int) {
        if (chosenCnt == 4) {
            answer = max(answer, curScore)
            return
        }
        for (amount in mv) {
            val (ny, nx) = arrayOf(cy + amount.first, cx + amount.second)

            if (isIn(ny, nx) && !visited[ny][nx]) {
                val nextScore = curScore + tetromino[ny][nx]
                visited[ny][nx] = true
                dfs(ny, nx, chosenCnt + 1, nextScore)
                visited[ny][nx] = false
            }
        }
    }

    fun checkSpecialShape(cy: Int, cx: Int) {
        loop@ for (type in specialShape) {
            var score = tetromino[cy][cx]

            for (amount in type) {
                val (ny, nx) = arrayOf(cy + amount.first, cx + amount.second)

                if (isIn(ny, nx)) {
                    score += tetromino[ny][nx]
                } else {
                    continue@loop
                }
            }
            answer = max(answer, score)
        }
    }

    for (sy in 0 until n) {
        for (sx in 0 until m) {
            checkSpecialShape(sy, sx)
            visited[sy][sx]=true
            dfs(sy, sx, 1, tetromino[sy][sx])
            visited[sy][sx]=false
        }
    }

    println(answer)
}