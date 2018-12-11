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
    val lastMarbleNumber = matchResult.groupValues[2].toLong()

    val ring = Ring(0L)

    val playersScore = (1..maxPlayers).map { 0L }.toMutableList()
    var currentPlayer = 1
    val totalLastMarble = lastMarbleNumber * factor
    for (marbleNumber in 1..totalLastMarble) {

        if (marbleNumber % 23 == 0L) {
            ring.move(-7)
            playersScore[currentPlayer - 1] += marbleNumber
            playersScore[currentPlayer - 1] += ring.delete()
        } else {
            ring.move(1)
            ring.insert(marbleNumber)
        }
        currentPlayer = currentPlayer.rem(maxPlayers) + 1
    }
    return playersScore
}

private class Ring<T>(element: T) {
    private var root = Node(element)

    fun insert(element: T) {
        root = Node(element, root, root.right)
    }

    fun delete(): T {
        require(root.left != root && root.right != root)
        val element = root.element
        root.left.right = root.right
        root.right.left = root.left
        root = root.right
        return element
    }

    fun move(n: Int) {
        if (n < 0) repeat(-n) { root = root.left } else repeat(n) { root = root.right }
    }

    private class Node<T> {
        var element: T
        var left: Node<T>
        var right: Node<T>

        constructor(element: T) {
            this.element = element
            left = this
            right = this
        }

        constructor(element: T, left: Node<T>, right: Node<T>) {
            this.element = element
            this.left = left
            left.right = this
            this.right = right
            right.left = this
        }
    }
}