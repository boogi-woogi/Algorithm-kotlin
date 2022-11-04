package DivideAndConquer

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

var compressedResult=StringBuilder()

fun solve(startRow: Int, startColumn: Int, size: Int, video: Array<IntArray>) {
    if (size == 1) {
        compressedResult.append(video[startRow][startColumn])
        return
    }

    var (zeroExist, oneExist) = arrayOf(false, false)

    for (curRow in startRow until startRow + size) {
        for (curColumn in startColumn until startColumn + size) {
            when (video[curRow][curColumn]) {
                0 -> zeroExist = true
                1 -> oneExist = true
            }
        }
    }

    when {
        zeroExist && oneExist -> {
            compressedResult.append('(')
            solve(startRow, startColumn, size / 2, video)
            solve(startRow, startColumn + size / 2, size / 2, video)
            solve(startRow + size / 2, startColumn, size / 2, video)
            solve(startRow + size / 2, startColumn + size / 2, size / 2, video)
            compressedResult.append(')')
        }
        zeroExist -> {
            compressedResult.append('0')
        }
        oneExist -> {
            compressedResult.append('1')
        }
    }
}

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val video = Array(n) { IntArray(n) { 0 } }

    repeat(n) { row ->
        var column = 0
        readLine().forEach { eachValue ->
            video[row][column++] = Character.getNumericValue(eachValue)
        }
    }

    solve(0, 0, n, video)

    BufferedWriter(OutputStreamWriter(System.`out`)).use { bw ->
        bw.write(compressedResult.toString())
        bw.flush()
    }
}
