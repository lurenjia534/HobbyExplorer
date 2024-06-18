package com.lurenjia534.hobbyexplorer

import android.app.Application
import android.os.Bundle
import android.telecom.Call.Details
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lurenjia534.hobbyexplorer.hobby.Hobby
import com.lurenjia534.hobbyexplorer.hobby.HobbyViewModel
import com.lurenjia534.hobbyexplorer.hobby.HobbyViewModelFactory
import com.lurenjia534.hobbyexplorer.ui.theme.HobbyExplorerTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HobbyExplorerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "all", modifier = modifier) {
        composable("all") { All(navController) }
        composable("Search") { Search() }
        composable("Star") { HomeScreen(navController) }
        composable("details/{hobbyId}") { backStackEntry ->
            val hobbyId = backStackEntry.arguments?.getString("hobbyId")
            hobbyId?.let { DetailsScreen(it) }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val hobbyViewModel: HobbyViewModel = viewModel(
        factory = HobbyViewModelFactory(context.applicationContext as Application)
    )
    val displayedHobbies by hobbyViewModel.displayedHobbies.observeAsState(emptyList())
    val isLoading by hobbyViewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        hobbyViewModel.updateDisplayedHobbies()
    }

    Scaffold(
        floatingActionButton = { RefreshFAB(hobbyViewModel) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LinearProgressIndicator(color = MaterialTheme.colorScheme.primary, strokeCap = StrokeCap.Round)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Loading..",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Default
                            )
                        )
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(displayedHobbies) { hobby ->
                        HobbyCard(hobby, onClick = {
                            navController.navigate("details/${hobby.id}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun RefreshFAB(hobbyViewModel: HobbyViewModel) {
    FloatingActionButton(onClick = {
        hobbyViewModel.updateDisplayedHobbies()
    }) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "刷新")
    }
}


@Composable
fun HobbyCard(hobby: Hobby, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = hobby.info ?: "No Info")
            Text(text = hobby.nicheInfo ?: "没内容")
        }
    }
}


@Composable
fun Search() {
    Column {
        Text(text = "Favorites Screen")
    }
}

@Composable
fun All(navController: NavHostController) {
    Text(text = "Profile Screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(hobbyId: String) {
    val context = LocalContext.current
    val hobbyViewModel: HobbyViewModel = viewModel(
        factory = HobbyViewModelFactory(context.applicationContext as Application)
    )
    val hobby by hobbyViewModel.getHobbyById(hobbyId).observeAsState()

    Scaffold(
    ) { innerPadding ->
        hobby?.let {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Text(text = it.info ?: "No Info", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it.nicheInfo ?: "没内容", style = TextStyle(fontSize = 18.sp))
                Text(text = it.contactTime ?: "没内容", style = TextStyle(fontSize = 18.sp))
                Text(text = it.costCount ?: "没内容", style = TextStyle(fontSize = 18.sp))
                Text(text = it.timeOfDay ?: "没内容", style = TextStyle(fontSize = 18.sp))
                Text(text = it.level ?: "没内容", style = TextStyle(fontSize = 18.sp))
                Text(text = it.levelVal.toString(), style = TextStyle(fontSize = 18.sp))
                Text(text = it.putIntoCostLevel ?: "没内容", style = TextStyle(fontSize = 18.sp))
                Text(text = it.putIntoTimeLevel ?: "没内容", style = TextStyle(fontSize = 18.sp))
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }
    }
}