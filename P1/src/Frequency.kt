import java.io.File

fun main(args: Array<String>) {

    val lines = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()

    // part 1
    println("part 1: " +
            lines.map { line ->  line.toInt()}.sum())

    // part 2
    // 72889
    solveP2(lines)
}

fun solveP2(lines: List<String>) {
    val fChangeList = lines.map { line -> line.toInt() }

    var freq = 0
    val frequencies = hashSetOf<Int>()

//    val fChangeSeq = generateSequence { fChangeList }.flatten()
//    fChangeSeq
//            .map { i -> i + freq }
//            .takeWhile { i -> frequencies.add(i)
//            }
//    println("part 2: $freq")
//    return

    while (true) {
        for (freqChange in fChangeList) {
            freq = freq.plus(freqChange)
            if (frequencies.contains(freq)) {
                println("part 2: " + freq)
                return
            }
            frequencies.add(freq)
        }
    }
}
