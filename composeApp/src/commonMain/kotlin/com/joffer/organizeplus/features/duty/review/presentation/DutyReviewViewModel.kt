package com.joffer.organizeplus.features.duty.review.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.duty.review.domain.repositories.DutyReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DutyReviewViewModel(
    private val repository: DutyReviewRepository,
    private val categoryFilter: String? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(DutyReviewUiState(isLoading = true))
    val uiState: StateFlow<DutyReviewUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onIntent(intent: DutyReviewIntent) {
        when (intent) {
            DutyReviewIntent.LoadData -> loadData()
            DutyReviewIntent.Retry -> retry()
            DutyReviewIntent.ClearError -> clearError()
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
                        handleError(error.message ?: "Unknown error occurred")
                    }
                )
            }
        }
    }

    private fun retry() {
        _uiState.value = _uiState.value.copy(error = null, isLoading = true)
        loadData()
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun handleError(message: String) {
        _uiState.value = DutyReviewUiState(
            isLoading = false,
            dutyReviewData = null,
            error = message
        )
    }
}
