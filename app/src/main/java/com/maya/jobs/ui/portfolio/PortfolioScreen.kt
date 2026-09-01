package com.maya.jobs.ui.portfolio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maya.jobs.domain.model.Portfolio
import com.maya.jobs.domain.repository.PortfolioRepository

@Composable
fun PortfolioScreen(portfolioRepository: PortfolioRepository) {
    val viewModel: PortfolioViewModel = viewModel(factory = PortfolioViewModel.factory(portfolioRepository))
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Portfolio Builder", style = MaterialTheme.typography.headlineMedium)

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }
        state.notice?.let {
            Text(it, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
        }

        if (state.isLoading || state.isExporting) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        ProviderImport(onImport = viewModel::import)

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        PortfolioPreview(portfolio = state.portfolio)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
            Button(onClick = viewModel::save, modifier = Modifier.weight(1f)) { Text("Save") }
            Button(onClick = viewModel::exportPdf, modifier = Modifier.weight(1f)) { Text("Export PDF") }
        }
    }
}

@Composable
private fun ProviderImport(onImport: (provider: String, username: String, apiKey: String?) -> Unit) {
    var username by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("GitHub username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { onImport("github", username.trim(), null) }, enabled = username.isNotBlank()) { Text("GitHub") }
            OutlinedButton(onClick = { onImport("gitlab", username.trim(), null) }, enabled = username.isNotBlank()) { Text("GitLab") }
            OutlinedButton(onClick = { onImport("bitbucket", username.trim(), null) }, enabled = username.isNotBlank()) { Text("Bitbucket") }
        }
    }
}

@Composable
private fun PortfolioPreview(portfolio: Portfolio) {
    if (portfolio.fullName.isEmpty()) {
        Text("No portfolio yet — import from a provider above.", style = MaterialTheme.typography.bodyMedium)
        return
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(portfolio.fullName, style = MaterialTheme.typography.headlineSmall)
                    Text(portfolio.headline, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    if (portfolio.location.isNotEmpty()) Text(portfolio.location, style = MaterialTheme.typography.bodySmall)
                    if (portfolio.summary.isNotEmpty()) Text(portfolio.summary, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        if (portfolio.skills.isNotEmpty()) {
            item {
                Text("Skills", style = MaterialTheme.typography.titleMedium)
                Row {
                    portfolio.skills.take(12).forEach { AssistChip(onClick = {}, label = { Text(it.name) }) }
                }
            }
        }
        if (portfolio.projects.isNotEmpty()) {
            item {
                Text("Projects", style = MaterialTheme.typography.titleMedium)
                portfolio.projects.forEach { project ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(project.name, style = MaterialTheme.typography.titleSmall)
                            if (project.description.isNotEmpty()) Text(project.description, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
