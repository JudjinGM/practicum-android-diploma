package ru.practicum.android.diploma.vacancy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.useCase.AddOrDelVacancyUseCase
import ru.practicum.android.diploma.favorites.domain.useCase.CheckInFavoritesUseCase
import ru.practicum.android.diploma.vacancy.domain.useCase.FindVacancyByIdUseCase
import ru.practicum.android.diploma.vacancy.ui.VacancyState

class VacancyViewModel(
    private val findVacancyByIdUseCase: FindVacancyByIdUseCase,
    private val addOrDelVacancyUseCase: AddOrDelVacancyUseCase,
    private val checkInFavoritesUseCase: CheckInFavoritesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    private var _inFavorites = MutableLiveData<Boolean>()
    val inFavorites: LiveData<Boolean> = _inFavorites

    init {
        setState(VacancyState.Load())
    }

    fun findVacancyById(id: Int) {
        viewModelScope.launch {
            val vacancyUI = findVacancyByIdUseCase.findVacancyById(id)
            if (vacancyUI.vacancy != null)
                setState(VacancyState.Content(vacancyUI.vacancy))
            else
                setState(VacancyState.Error())
        }
    }

    private fun setState(state: VacancyState) {
        _state.value = state
    }

    fun checkFavorites(id: Int) {
        viewModelScope.launch {
            _inFavorites.value = checkInFavoritesUseCase.execute(id)
        }
    }

    fun addOrDelFavorites(id: Int) {
        viewModelScope.launch {
            addOrDelVacancyUseCase.execute(id)
        }
    }
}