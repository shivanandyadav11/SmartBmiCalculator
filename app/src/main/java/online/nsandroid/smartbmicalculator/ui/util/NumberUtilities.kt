package online.nsandroid.smartbmicalculator.ui.util

import online.nsandroid.smartbmicalculator.model.BMIStatus
import online.nsandroid.smartbmicalculator.model.UserData
import kotlin.math.pow

fun MutableList<Int>.findClosest(input: Int) = fold(null) { acc: Int?, num ->
    val closest = if (num <= input && (acc == null || num > acc)) num else acc
    if (closest == input) return@findClosest closest else return@fold closest
}

fun Double.getMaxHealthWeight(): Double {
    return Math.round(24.9 * this.pow(2) * 100.0) / 100.0
}

fun Double.getMinHealthWeight(): Double {
    return Math.round(18.5 * this.pow(2) * 100.0) / 100.0
}

fun UserData.getUserAgeCategory(): String {
    return when {
        this.age < 12 -> "Child"
        this.age in 12..19 -> "teen"
        this.age in 20..65 -> "Adult"
        else -> "Old"
    }
}

fun BMIStatus.getUserCategory(): String {
    return when (this) {
        BMIStatus.UnderWeight -> "Under Weight"
        BMIStatus.HealthyWeight -> "Normal"
        else -> "over Weight"
    }
}

fun Double.calculateBMIStatus(): BMIStatus {
    return when {
        (this < 18.5) -> BMIStatus.UnderWeight
        (this >= 18.5 && this < 25) -> BMIStatus.HealthyWeight
        else -> BMIStatus.OverWeightWeight
    }
}

fun UserData.calculateBMI(): Double {
    return Math.round(this.weight / this.height.pow(2) * 100.0) / 100.0
}
