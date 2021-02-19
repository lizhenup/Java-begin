package com.example.materialtest

import java.lang.RuntimeException

/*fun Max(vararg nums: Int): Int {
    var maxNum = Int.MIN_VALUE
    for (num in nums) {
        maxNum = kotlin.math.max(maxNum, num)
    }
    return maxNum
}*/

fun<T : Comparable<T>> Max(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("Params can not be empty.")
    var maxNum = nums[0]
    for (num in nums) {
        if( num > maxNum) maxNum = num
    }
    return maxNum
}
fun main() {
    val a = 10.66
    val b = 20.963
    val c = 30.2
    println(Max(a, b, c))
}