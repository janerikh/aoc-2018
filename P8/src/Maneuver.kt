import java.io.File

fun main(args: Array<String>) {

    val lines = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()

    val input = lines.get(0)

    // part 1
    solvePart1(input)

    // part 2
    sovlePart2(input)
}

private fun solvePart1(input: String) {

    val inputList = input.split(" ")
    val iterator = inputList.iterator()
    var sum = 0
    while (iterator.hasNext()) {
        sum += readSumOfMetadataFromNodes(iterator, 1)
    }
    println("Part 1 $sum") //37439
}

fun readSumOfMetadataFromNodes(iterator: Iterator<String>, nodesToRead: Int): Int {
    var sum = 0
    for (i in 1..nodesToRead) {
        val childNodes = iterator.next().toInt()
        val metadataEntries = iterator.next().toInt()
        if (childNodes > 0) {
            sum += readSumOfMetadataFromNodes(iterator, childNodes)
        }
        (1..metadataEntries).forEach {
            sum += iterator.next().toInt()
        }
    }
    return sum
}

private fun sovlePart2(input: String) {
    val inputList = input.split(" ")
    val iterator = inputList.iterator()

    val rootNode = Node()
    val childNodes = iterator.next().toInt()
    val metadataEntries = iterator.next().toInt()
    if (childNodes > 0) {
        rootNode.children = readChildren(iterator, childNodes)
        rootNode.childIndexes = (1..metadataEntries).map {
            iterator.next().toInt()
        }
    } else {
        (1..metadataEntries).forEach {
            rootNode.noChildrenSum += iterator.next().toInt()
        }
    }

    val sum = sumForChildNode(rootNode)

    println("Part 2 $sum")
}

fun readChildren(iterator: Iterator<String>, childrenToRead: Int): List<Node> {
    val nodes = mutableListOf<Node>()
    for (i in 1..childrenToRead) {
        val childNodes = iterator.next().toInt()
        val metadataEntries = iterator.next().toInt()
        val node = Node()
        if (childNodes > 0) {
            node.children = readChildren(iterator, childNodes)
            node.childIndexes = (1..metadataEntries).map {
                iterator.next().toInt()
            }
        } else {
            (1..metadataEntries).forEach {
                node.noChildrenSum += iterator.next().toInt()
            }
        }
        nodes.add(node)
    }
    return nodes
}

fun sumForChildNode(parentNode: Node): Int {
    var sum = 0
    for (i in parentNode.childIndexes) {
        if (i <= 0 || i > parentNode.children.size) {
            continue
        }
        val node = parentNode.children[i-1]
        if (node.children.isNotEmpty()) {
            sum += sumForChildNode(node)

        } else {
            sum += node.noChildrenSum

        }
    }

    return sum
}

class Node {
    var childIndexes: List<Int> = mutableListOf()
    var children: List<Node> = mutableListOf()
    var noChildrenSum: Int = 0
}
