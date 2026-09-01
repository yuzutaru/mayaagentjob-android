package com.maya.jobs.ui.jobs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maya.jobs.domain.model.JobListing
import com.maya.jobs.domain.repository.JobRepository

@Composable
fun JobsScreen(jobRepository: JobRepository) {
    val viewModel: JobsViewModel = viewModel(factory = JobsViewModel.factory(jobRepository))
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Job Search", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = state.jobs.firstOrNull()?.let { "" } ?: "",
            onValueChange = viewModel::onKeywordChange,
            placeholder = { Text("Job title or keyword") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            singleLine = true,
        )
        Button(onClick = viewModel::onSearch, modifier = Modifier.fillMaxWidth()) {
            Text("Search")
        }

        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.padding(24.dp))
            state.error != null -> Text(state.error!!, color = MaterialTheme.colorScheme.error)
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(state.jobs, key = { it.jobId }) { job -> JobCard(job) }
                if (state.page < state.totalPages) {
                    item { Button(onClick = viewModel::loadNextPage, modifier = Modifier.fillMaxWidth()) { Text("Load more") } }
                }
            }
        }
    }
}

@Composable
private fun JobCard(job: JobListing) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(job.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Text("${job.company} · ${job.arrangement}", style = MaterialTheme.typography.bodyMedium)
            Text(job.location, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            job.aiSummaryBullets.take(3).forEach {
                Text("• $it", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
