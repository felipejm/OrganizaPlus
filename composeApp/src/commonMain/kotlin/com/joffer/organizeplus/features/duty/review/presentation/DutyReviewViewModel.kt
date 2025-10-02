package com.joffer.organizeplus.features.duty.review.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewData
import com.joffer.organizeplus.features.duty.review.domain.repositories.DutyReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class DutyReviewIntent {
    object LoadData : DutyReviewIntent()
    object Retry : DutyReviewIntent()
    object ClearError : DutyReviewIntent()
}

data class DutyReviewUiState(
    val isLoading: Boolean = false,
    val dutyReviewData: DutyReviewData? = null,
    val error: String? = null
)

class DutyReviewViewModel(
    private val categoryFilter: String? = null
) : ViewModel(), KoinComponent {

    private val repository: DutyReviewRepository by inject()

    private val _uiState = MutableStateFlow(DutyReviewUiState(isLoading = true))
    val uiState: StateFlow<DutyReviewUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onIntent(intent: DutyReviewIntent) {
        when (intent) {
            is DutyReviewIntent.LoadData -> loadData()
            is DutyReviewIntent.Retry -> {
                _uiState.value = _uiState.value.copy(error = null, isLoading = true)
                loadData()
            }
            is DutyReviewIntent.ClearError -> {
                _uiState.value = _uiState.value.copy(error = null)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getDutyReviewData(categoryFilter).collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiState.value = DutyReviewUiState(
                            isLoading = false,
                            dutyReviewData = data,
                            error = null
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = DutyReviewUiState(
                            isLoading = false,
                            dutyReviewData = null,
                            error = error.message ?: "Unknown error occurred"
                        )
                    }
                )
            }
        }
    }
}
