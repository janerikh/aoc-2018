import java.io.File
import java.util.*

fun main(args: Array<String>) {

    val lines = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()

    // part 1
    val line = lines.get(0)

    val inputCharList = line.toCharArray().toList()
    solvePart1(inputCharList)

    // part 2
    sovlePart2(inputCharList)
}

private fun sovlePart2(inputCharList: List<Char>) {
    val fullyReactedTestMap = ('a'..'z').toList().map { charToTest ->
        inputCharList
                .filterNot { charToFilter -> charEqualIgnoreCase(charToFilter, charToTest) }
                .fold(LinkedList<Char>(), { acc, c -> react(acc, c) }).size
    }
    val shortesPolymer = fullyReactedTestMap.sorted().first()

    // 4552
    println("part 2: $shortesPolymer")
}

private fun solvePart1(inputCharList: List<Char>) {
    val unitList = inputCharList.fold(LinkedList<Char>(), { acc, c -> react(acc, c) })
    println("part 1: " + unitList.size)
}

private fun react(list: LinkedList<Char>, c: Char): LinkedList<Char> {
    val last = list.peekLast()
    if (last != null && react(last, c)) {
        list.removeLast()
    } else {
        list.add(c)
    }
    return list
}

private fun react(last: Char, c: Char) =
        charEqualIgnoreCase(last, c) && !last.equals(c)

private fun charEqualIgnoreCase(c1: Char, c2: Char) = c1.toUpperCase().equals(c2.toUpperCase())