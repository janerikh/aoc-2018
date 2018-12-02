import java.io.File

fun main(args: Array<String>) {

    val boxIds = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()
    // part 1 - 7105
    solveP1(boxIds)

    // part 2 - omlvgdokxfncvqyersasjziup
    solveP2(boxIds)
}

private fun solveP1(boxIds: List<String>) {
    var twoCount = 0
    var threeCount = 0
    for (boxId in boxIds) {
        val groupedInput = boxId.groupBy { c: Char -> c }
        twoCount += Math.min(
                groupedInput
                        .filter { entry -> entry.value.size == 2 }
                        .count(),
                1)
        threeCount += Math.min(
                groupedInput
                        .filter { entry -> entry.value.size == 3 }
                        .count(),
                1)
    }
    println("part 1: ${twoCount * threeCount}")
}

fun solveP2(boxIds: List<String>) {
    for (i in 0 until boxIds.size) {
        printOneOff(boxIds[i], boxIds.subList(i+1, boxIds.size))
    }
}

fun printOneOff(str1: String, list: List<String>) {
    for (str2 in list) {
        printOneOff(str1, str2)
    }
}

fun printOneOff(str1: String, str2: String) {
    for (i in 0 until str1.length) {
        if (str1.removeRange(i, i+1).equals(str2.removeRange(i, i+1))) {
            println("part 2: " + str1.removeRange(i, i+1))
        }
    }
}
