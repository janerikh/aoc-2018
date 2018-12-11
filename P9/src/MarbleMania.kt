import java.io.File
import java.lang.RuntimeException

fun main(args: Array<String>) {

    val lines = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()
    //9 players; last marble is worth 48 points: high score is 63
    //1 players; last marble is worth 48 points: high score is 95
    val input = lines.get(0)

    // part 1
    solvePart1(input)

    // part 2
    sovlePart2(input)
}

private fun solvePart1(input: String) {

    val factor = 1
    val playersScore = solve(input, factor)

    // part 1:
    println("part 1 : " + playersScore.max())
}

private fun sovlePart2(input: String) {

    val factor = 100
    val playersScore = solve(input, factor)

    // part 2:
    println("part 2 : " + playersScore.max())
}

private fun solve(input: String, factor: Int): MutableList<Long> {
    val matchResult = Regex("^([0-9]+) .+ ([0-9]+) .+$").find(input)
    if (matchResult == null) {
        throw RuntimeException("No match for regex")
    }
    val maxPlayers = matchResult.groupValues[1].toInt()
    val lastMarbleNumber: Long = matchResult.groupValues[2].toLong()

    val circle = mutableListOf(0L)

    val playersScore = (1..maxPlayers).map { 0L }.toMutableList()
    var currentPlayer = 1
    var marbleNumber: Long = 1
    var currentIndex = 0
    val totalLastMarble: Long = lastMarbleNumber * factor
    while (marbleNumber <= totalLastMarble) {
//        println("currentPlayer: $currentPlayer marbleNumber: $marbleNumber size: ${circle.size} circle: $circle currentIndex: $currentIndex")

        if (marbleNumber % 23 == 0L) {
            playersScore[currentPlayer - 1] += marbleNumber
            var indexToRemove = (currentIndex - 7)
            if (indexToRemove < 0) {
                indexToRemove = circle.size + indexToRemove
            }
//            println("index to remove: $indexToRemove") //6
            val marbleRemoved = circle.removeAt(indexToRemove)
            playersScore[currentPlayer - 1] += marbleRemoved
            currentIndex = indexToRemove
        } else {
            currentIndex = (currentIndex + 2) % circle.size
            if (currentIndex == 0) {
                currentIndex = circle.size
            }
//        println("new currentIndex: $currentIndex size: ${circle.size} circle: $circle")
            circle.add(currentIndex, marbleNumber)
        }
        currentPlayer = currentPlayer.rem(maxPlayers) + 1
        marbleNumber++
//        println()
    }
    return playersScore
}