package online.nsandroid.smartbmicalculator.model

sealed interface BMIStatus {
    data object UnderWeight: BMIStatus
    data object HealthyWeight: BMIStatus
    data object OverWeightWeight: BMIStatus
}