package com.lurenjia534.hobbyexplorer.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lurenjia534.hobbyexplorer.IpInfoText
import com.lurenjia534.hobbyexplorer.hobby.Hobby
import com.lurenjia534.hobbyexplorer.hobby.HobbyViewModel

@Composable
fun RefreshFAB(hobbyViewModel: HobbyViewModel) {
    FloatingActionButton(onClick = {
        hobbyViewModel.updateDisplayedHobbies()
        hobbyViewModel.isDataLoaded = true
    }) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "刷新")
    }
}

@Composable
fun HobbyCard(hobby: Hobby, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            IpInfoText(label = hobby.info ?: "No Info", value = hobby.nicheInfo ?: "建议者太懒了,没有介绍该爱好")
        }
    }
}

@Composable
fun IpInfoText(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Default
        )
    }
}

@Composable
fun BackButton(navController: NavHostController) {
    FloatingActionButton(onClick = {
        navController.popBackStack()
    }) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "上一页")
    }
}
