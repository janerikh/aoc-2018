import java.io.File
import java.util.*

fun main(args: Array<String>) {

    val lines = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()

    // part 1
    val line = lines[0]

    val inputCharList = line.toCharArray().toList()
    solvePart1(inputCharList)

    // part 2
    sovlePart2(inputCharList)
}

private fun sovlePart2(inputCharList: List<Char>) {
    val fullyReactedTestMap = ('a'..'z').toList().map { charToTest ->
        inputCharList
                .filterNot { charToFilter -> charEqualIgnoreCase(charToFilter, charToTest) }
                .fold(Stack<Char>()) { acc, c -> react(acc, c) }.size
    }
    val shortestPolymer = fullyReactedTestMap.sorted().first()

    // 4552
    println("part 2: $shortestPolymer")
}

private fun solvePart1(inputCharList: List<Char>) {
    val unitList = inputCharList.fold(Stack<Char>()) { acc, c -> react(acc, c) }
    println("part 1: " + unitList.size)
}

private fun react(list: Stack<Char>, c: Char): Stack<Char> {
    if (list.empty().not() && react(list.peek(), c)) {
        list.pop()
    } else {
        list.push(c)
    }
    return list
}

private fun react(last: Char, c: Char) =
        charEqualIgnoreCase(last, c) && last != c

private fun charEqualIgnoreCase(c1: Char, c2: Char) = c1.toUpperCase() == c2.toUpperCase()