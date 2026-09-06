package com.maya.jobs.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maya.jobs.domain.repository.JobRepository
import com.maya.jobs.domain.repository.PortfolioRepository
import com.maya.jobs.ui.home.HomeScreen
import com.maya.jobs.ui.jobs.JobsScreen
import com.maya.jobs.ui.portfolio.PortfolioScreen

sealed class Destination(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Destination("home", "Home", Icons.Filled.Home)
    data object Jobs : Destination("jobs", "Jobs", Icons.Filled.Work)
    data object Portfolio : Destination("portfolio", "Portfolio", Icons.Filled.Description)
}

@Composable
fun MayaApp(jobRepository: JobRepository, portfolioRepository: PortfolioRepository) {
    val navController = rememberNavController()
    val destinations = listOf(Destination.Portfolio, Destination.Jobs, Destination.Home)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStack by navController.currentBackStackEntryAsState()
                val currentDestination = backStack?.destination
                destinations.forEach { dest ->
                    val selected = currentDestination?.hierarchy?.any { it.route == dest.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(dest.icon, contentDescription = dest.label) },
                        label = { Text(dest.label) },
                    )
                }
            }
        },
    ) { padding ->
        NavHost(navController = navController, startDestination = Destination.Home.route, modifier = Modifier.padding(padding)) {
            composable(Destination.Home.route) {
                HomeScreen(
                    onOpenPortfolio = { navController.navigate(Destination.Portfolio.route) },
                    onOpenJobs = { navController.navigate(Destination.Jobs.route) },
                )
            }
            composable(Destination.Jobs.route) { JobsScreen(jobRepository) }
            composable(Destination.Portfolio.route) { PortfolioScreen(portfolioRepository) }
        }
    }
}
