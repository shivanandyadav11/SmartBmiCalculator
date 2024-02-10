package online.nsandroid.smartbmicalculator.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import online.nsandroid.smartbmicalculator.model.UserData

class BmiCalculatorViewModel : ViewModel() {

    private val _userData: MutableStateFlow<UserData> = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> get() =  _userData

    internal fun setUserData(userData: UserData) {
        _userData.value = userData
    }
}