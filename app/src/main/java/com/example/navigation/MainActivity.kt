package com.example.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Example application showcasing how to share a view model between different
// composable functions inside Jetpack Compose Navigation.

// Converts Fahrenheit to Celsius

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationSystem()
        }
    }
}

@Composable
fun NavigationSystem() {
    val navController = rememberNavController()
    val viewModel: ConversionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("result") { ResultScreen(navController, viewModel) }
    }
}

@Composable
fun HomeScreen(navController: NavController, viewModel: ConversionViewModel) {
    var temp by remember { mutableStateOf("") }
    val fahrenheit = temp.toIntOrNull() ?: 0

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(.65f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = temp,
            onValueChange = { temp = it },
            label = { Text("Fahrenheit") },
            modifier = Modifier.fillMaxWidth(.75f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(.75f),
            onClick = {
            if (fahrenheit !in 1..160) return@Button

            viewModel.onCalculate(fahrenheit)
            navController.navigate("result")
        }) {
            Text("Calculate")
        }

    }
}

@Composable
fun ResultScreen(navController: NavController, viewModel: ConversionViewModel) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "${viewModel.fahrenheit.toString()} °F = ${viewModel.celsiusText} °C",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.padding(top = 24.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Calculate Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationSystem()
}