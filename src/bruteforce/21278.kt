package bruteforce

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Math.max
import java.lang.Math.min
import kotlin.collections.ArrayList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    data class Answer(var smallCity: Int, var bigCity: Int, var totalDistance: Int)
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val chosen = BooleanArray(n + 1) { false }
    val distanceEachCity = Array(n + 1) { IntArray(n + 1) { Int.MAX_VALUE } }
    var answer: Answer = Answer(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
    val chosenCities = ArrayList<Int>()

    for (i in 1..n) {
        distanceEachCity[i][i] = 0
    }
    repeat(m) {
        val (from, to) = readLine().split(" ").map { it.toInt() }
        distanceEachCity[from][to] = 1
        distanceEachCity[to][from] = 1
    }
    for (by in 1..n) {
        for (start in 1..n) {
            for (to in 1..n) {
                if (distanceEachCity[start][by] != Int.MAX_VALUE && distanceEachCity[by][to] != Int.MAX_VALUE) {
                    distanceEachCity[start][to] =
                        min(distanceEachCity[start][to], distanceEachCity[start][by] + distanceEachCity[by][to])
                }
            }
        }
    }

    fun getTotalDistance(): Answer {
        var totalDistance = 0
        val calculated: Answer = Answer(Int.MAX_VALUE, Int.MIN_VALUE, Int.MAX_VALUE)

        for (city in 1..n) {
            totalDistance += min(distanceEachCity[city][chosenCities[0]], distanceEachCity[city][chosenCities[1]]) * 2
        }
        calculated.smallCity = min(chosenCities[0], chosenCities[1])
        calculated.bigCity = max(chosenCities[0], chosenCities[1])
        calculated.totalDistance = totalDistance
        return calculated
    }

    fun judgeAnswer(calculated: Answer) {
        if (answer.totalDistance > calculated.totalDistance) {
            answer = calculated
        } else if (answer.totalDistance == calculated.totalDistance) {
            answer = if (answer.smallCity > calculated.smallCity) {
                calculated
            } else if (answer.smallCity == calculated.smallCity && answer.bigCity > calculated.bigCity) {
                calculated
            } else {
                answer
            }
        }
    }

    fun makeCombination(cur: Int, cnt: Int) {
        if (cnt == 2) {
            judgeAnswer(getTotalDistance())
            return
        }
        for (city in cur..n) {
            if (!chosen[city]) {
                chosen[city] = true
                chosenCities.add(city)
                makeCombination(city + 1, cnt + 1)
                chosen[city] = false
                chosenCities.remove(city)
            }
        }
    }

    makeCombination(1, 0)
    println("${answer.smallCity} ${answer.bigCity} ${answer.totalDistance}")
}