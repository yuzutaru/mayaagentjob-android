package com.maya.jobs.ui.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.maya.jobs.domain.model.Portfolio
import com.maya.jobs.domain.model.PortfolioStats
import com.maya.jobs.domain.repository.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PortfolioUiState(
    val portfolio: Portfolio = Portfolio(fullName = "", stats = PortfolioStats()),
    val isLoading: Boolean = false,
    val isExporting: Boolean = false,
    val error: String? = null,
    val notice: String? = null,
)

class PortfolioViewModel(private val repository: PortfolioRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PortfolioUiState())
    val uiState: StateFlow<PortfolioUiState> = _uiState

    fun import(provider: String, username: String, apiKey: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val portfolio = repository.importFromProvider(provider, username, apiKey)
                _uiState.value = PortfolioUiState(portfolio = portfolio, notice = "Imported @$username from $provider")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Import failed")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val saved = repository.save(_uiState.value.portfolio)
                _uiState.value = _uiState.value.copy(portfolio = saved, isLoading = false, notice = "Portfolio saved")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Save failed")
            }
        }
    }

    fun exportPdf() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isExporting = true, error = null)
            try {
                repository.exportPdf(_uiState.value.portfolio)
                _uiState.value = _uiState.value.copy(isExporting = false, notice = "PDF downloaded")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isExporting = false, error = e.message ?: "PDF export failed")
            }
        }
    }

    fun clearNotice() {
        _uiState.value = _uiState.value.copy(notice = null)
    }

    companion object {
        fun factory(repository: PortfolioRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer { PortfolioViewModel(repository) }
        }
    }
}
