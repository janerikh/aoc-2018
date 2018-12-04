import java.io.File

fun main(args: Array<String>) {
    val fabricSize = 1000
    var fabricMap = arrayOf<Array<Int>>()
    fabricMap = fill2DArrayWithZeros(fabricSize, fabricMap)

    val lines = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()
    //"#1 @ 1,3: 4x4"
    val regex = Regex("^#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+).*$")

    for (line in lines) {
        val matchResult = regex.find(line)
        if (matchResult != null) {
            val leftEdge = matchResult.groups[2]!!.value.toInt()
            val topEdge = matchResult.groups[3]!!.value.toInt()
            val width = matchResult.groups[4]!!.value.toInt()
            val height = matchResult.groups[5]!!.value.toInt()

            for (i in leftEdge .. leftEdge + width - 1) {
                for (j in topEdge .. topEdge + height - 1)
                    fabricMap[i][j]++
            }
        }
    }

    // part 1 - 110891
    println("part 1: " + fabricMap.map { ints -> ints.filter { i -> i>1 }.count() }.sum())

    // part 2 - 297
    for (line in lines) {
        val matchResult = regex.find(line)
        if (matchResult != null) {
            val id = matchResult.groups[1]!!.value.toInt()
            val leftEdge = matchResult.groups[2]!!.value.toInt()
            val topEdge = matchResult.groups[3]!!.value.toInt()
            val width = matchResult.groups[4]!!.value.toInt()
            val height = matchResult.groups[5]!!.value.toInt()

            var fullFit = true
            for (i in leftEdge .. leftEdge + width - 1) {
                for (j in topEdge..topEdge + height - 1) {
                    if (fabricMap[i][j] > 1) {
                        fullFit = false
                    }
                }
            }
            if (fullFit) {
                println("part 2: " + id)
            }
        }
    }

}

private fun fill2DArrayWithZeros(fabricSize: Int, fabricMap: Array<Array<Int>>): Array<Array<Int>> {
    var fabricMap1 = fabricMap
    for (i in 0..fabricSize) {
        var array = arrayOf<Int>()
        for (j in 0..fabricSize) {
            array += 0
        }
        fabricMap1 += array
    }
    return fabricMap1
}

fun print2dArray(fabricMap: Array<Array<Int>>) {
    for (array in fabricMap) {
        for (value in array) {
            print("$value ")
        }
        println()
    }
}
