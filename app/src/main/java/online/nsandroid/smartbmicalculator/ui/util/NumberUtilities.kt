package online.nsandroid.smartbmicalculator.ui.util

fun MutableList<Int>.findClosest(input: Int) = fold(null) { acc: Int?, num ->
    val closest = if (num <= input && (acc == null || num > acc)) num else acc
    if (closest == input) return@findClosest closest else return@fold closest
}
