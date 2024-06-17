package com.lurenjia534.hobbyexplorer

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lurenjia534.hobbyexplorer.hobby.Hobby
import com.lurenjia534.hobbyexplorer.hobby.HobbyViewModel
import com.lurenjia534.hobbyexplorer.hobby.HobbyViewModelFactory
import com.lurenjia534.hobbyexplorer.ui.theme.HobbyExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HobbyExplorerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = { RefreshFAB() }
                ) { innerPadding ->
                    MyApp(innerPadding)
                }
            }
        }
    }
}

@Composable
fun MyApp(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val hobbyViewModel: HobbyViewModel = viewModel(
        factory = HobbyViewModelFactory(context.applicationContext as Application)
    )
    val displayedHobbies by hobbyViewModel.displayedHobbies.observeAsState(emptyList())

    val isLoading by hobbyViewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        hobbyViewModel.updateDisplayedHobbies()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(color = Color.Black, strokeCap = StrokeCap.Round)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Loading..", style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Default
                    ))
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(displayedHobbies) { hobby ->
                    HobbyCard(hobby)
                }
            }
        }
    }
}

@Composable
fun RefreshFAB() {
    val context = LocalContext.current
    val hobbyViewModel: HobbyViewModel = viewModel(
        factory = HobbyViewModelFactory(context.applicationContext as Application)
    )
    FloatingActionButton(onClick = {
        hobbyViewModel.updateDisplayedHobbies()
    }) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "刷新")
    }
}

@Composable
fun HobbyCard(hobby: Hobby) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = hobby.info ?: "No Info")
            Text(text = hobby.nicheInfo ?: "没内容")
            // 添加其他需要显示的爱好信息
        }
    }
}