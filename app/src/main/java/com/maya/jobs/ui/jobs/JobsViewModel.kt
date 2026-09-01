package com.maya.jobs.ui.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.maya.jobs.domain.model.JobListing
import com.maya.jobs.domain.repository.JobRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class JobsUiState(
    val jobs: List<JobListing> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val page: Int = 1,
    val totalPages: Int = 1,
)

class JobsViewModel(private val repository: JobRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(JobsUiState())
    val uiState: StateFlow<JobsUiState> = _uiState

    private var keyword: String? = null
    private var category: String? = null

    init {
        loadJobs(reset = true)
    }

    fun onKeywordChange(value: String) {
        keyword = value.trim().ifEmpty { null }
    }

    fun onCategoryChange(value: String?) {
        category = value
        loadJobs(reset = true)
    }

    fun onSearch() = loadJobs(reset = true)

    fun loadNextPage() {
        val state = _uiState.value
        if (!state.isLoading && state.page < state.totalPages) loadJobs(reset = false)
    }

    private fun loadJobs(reset: Boolean) {
        val targetPage = if (reset) 1 else _uiState.value.page + 1
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val result = repository.getJobs(category = category, keyword = keyword, page = targetPage, limit = 9)
                _uiState.value = _uiState.value.copy(
                    jobs = if (reset) result.items else (_uiState.value.jobs + result.items),
                    isLoading = false,
                    page = result.page,
                    totalPages = result.pages,
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Failed to load jobs")
            }
        }
    }

    companion object {
        fun factory(repository: JobRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer { JobsViewModel(repository) }
        }
    }
}
