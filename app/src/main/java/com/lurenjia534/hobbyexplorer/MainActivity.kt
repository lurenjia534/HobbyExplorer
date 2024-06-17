package com.lurenjia534.hobbyexplorer

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
    val hobbies by hobbyViewModel.allHobbies.observeAsState(emptyList())

    // 在这里展示 hobbies 列表
    // 例如：LazyColumn { items(hobbies) { hobby -> Text(hobby.info) } }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        items(hobbies) { hobby ->
            Text(hobby.info ?: "Unknown")
        }
    }
}

@Composable
fun HobbyItem(hobby: Hobby) {
    // 在这里定义如何展示每个 Hobby 项目
    // 例如：
    androidx.compose.material3.Text(text = hobby.info ?: "No Info")
}