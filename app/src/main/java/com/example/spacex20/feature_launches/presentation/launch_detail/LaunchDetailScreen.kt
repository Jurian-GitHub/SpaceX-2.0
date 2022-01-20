package com.example.spacex20.feature_launches.presentation.launch_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LaunchDetailScreen(
    viewModel: LaunchDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        state.launch?.let { launch ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Text(
                        text = "Rocket Details",
                        style = MaterialTheme.typography.h2
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Name:  ${launch.rocket.rocket_name}\n\nType:  ${launch.rocket.rocket_type}",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight (8f)
                        )
                        Text(
                            text = if (launch.launch_success == true) "success" else "failure",
                            color = if (launch.launch_success == true) Color.Green else Color.Red,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(2f)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = if (launch.details != null && launch.details.isNotBlank()) "Further Information" else "",
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    if (launch.details != null && launch.details.isNotBlank()) {
                        Text(
                            text = launch.details,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}