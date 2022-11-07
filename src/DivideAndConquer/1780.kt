package DivideAndConquer

import java.io.BufferedReader
import java.io.InputStreamReader

var zeroPaper = 0
var onePaper = 0
var minusOnePaper = 0

fun divideArea(size: Int) =
    arrayOf(
        Pair(0, 0),
        Pair(0, size / 3),
        Pair(0, size / 3 * 2),
        Pair(size / 3, 0),
        Pair(size / 3, size / 3),
        Pair(size / 3, size / 3 * 2),
        Pair(size / 3 * 2, 0),
        Pair(size / 3 * 2, size / 3),
        Pair(size / 3 * 2, size / 3 * 2)
    )

fun addPaper(kindOfPaper: Int) {
    when (kindOfPaper) {
        -1 -> minusOnePaper++
        0 -> zeroPaper++
        1 -> onePaper++
    }
}

fun cut(startRowIndex: Int, startColIndex: Int, size: Int, paper: Array<IntArray>) {
    val existNum = paper[startRowIndex][startColIndex]
    if (size == 1) {
        addPaper(existNum)
        return
    }
    for (rowIndex in startRowIndex until startRowIndex + size) {
        for (colIndex in startColIndex until startColIndex + size) {
            if (paper[rowIndex][colIndex] != existNum) {
                divideArea(size).forEach { divide ->
                    cut(startRowIndex + divide.first, startColIndex + divide.second, size / 3, paper)
                }
                return
            }
        }
    }
    addPaper(existNum)
}

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val size = readLine().toInt()
    val paper = Array(size) { IntArray(size) { 0 } }
    var rowIndex = 0

    repeat(size) {
        paper[rowIndex++] = readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    cut(0, 0, size, paper)

    println(minusOnePaper)
    println(zeroPaper)
    println(onePaper)
}