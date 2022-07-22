fun main(){
    println(parse("(2 * (3 + 1) * (1 + 2 + 3))"))
    println(parse("5 + 3 * (2 + 4) + 7 * 2"))
}

// Syntax: Spaces between numbers and operators. brackets without spaces
fun parse(text: String): Double {
    //println(text)

    // check syntax
    if(text.count { c -> c == '(' } != text.count{ c -> c == ')'}) {
        println("The term needs to contain equal numbers of '(' and ')'.")
        return -1.0
    }


    // Case 1: only result left in string
    try{
        return text.toDouble()
    } catch (e: Exception){

    }

    // Case 2: no brackets in string
    // rechnen
    if(!text.contains("(")) {
        var mutableDingList = text.split(" ").toMutableList()

        // *, /
        // von rechts nach links (wegen indexverschiebung) und alle strich rechnungen machen,
        var result = 0.0
        for(i in mutableDingList.lastIndex-1 downTo 1 step 2){
            if(mutableDingList[i] != "*" && mutableDingList[i] != "/")
                continue
            var first = mutableDingList[i-1].toDouble()
            var second = mutableDingList[i+1].toDouble()
            when(mutableDingList[i]){
                "*" -> result = first * second
                "/" -> result = first / second
            }
            mutableDingList.removeAt(i-1)
            mutableDingList.removeAt(i-1)
            mutableDingList[i-1] = result.toString()
        }

        // +, -
        // number of operators in this string
        var operatorCount = (mutableDingList.size - 1) / 2
        for(i in 1 .. operatorCount){
            // take 3 and calc
            var first = mutableDingList[0].toDouble()
            var second = mutableDingList[2].toDouble()
            when (mutableDingList[1]) {
                "+" -> result = first + second
                "-" -> result = first - second
            }
            // remove first and second
            mutableDingList.removeFirst()
            mutableDingList.removeFirst()
            // write solution
            mutableDingList[0] = result.toString()
        }

        return result
    }

    // Case 3: brackets left in string
    var startIndex: Int = 0
    var endIndex: Int = text.lastIndex
    // find last opening bracket in string
    for (i in text.indices)
        if(text[i] == '(')
            startIndex = i
    // find matching closing bracket
    for(i in text.lastIndex downTo startIndex + 1)
        if(text[i] == ')' )
            endIndex = i
    // recursive call for string, where substring between the brackets is substituted with its solution by recursive call
    return parse(text.substring(0, startIndex) + parse(text.substring(startIndex + 1, endIndex)) + text.substring(endIndex + 1, text.length))

}