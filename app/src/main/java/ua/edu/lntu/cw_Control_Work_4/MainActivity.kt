package ua.edu.lntu.cw_Control_Work_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import ua.edu.lntu.cw_Control_Work_4.ui.theme.IPZ_CP_4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPZ_CP_4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun Screen1(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        for (i in 1..5) {
            Button(
                onClick = { navController.navigate("screen2/$i") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Button $i")
            }
        }
    }
}

@Composable
fun Screen2(navController: NavHostController, buttonNumber: Int) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "You pressed Button $buttonNumber",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { scope.launch { navController.popBackStack() } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Go back")
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(navController, startDestination = "screen1") {
            composable("screen1") { Screen1(navController) }
            composable(
                route = "screen2/{buttonNumber}",
                arguments = listOf(navArgument("buttonNumber") { type = NavType.IntType })
            ) { backStackEntry ->
                val buttonNumber = backStackEntry.arguments?.getInt("buttonNumber") ?: 0
                Screen2(navController, buttonNumber)
            }
        }
    }
}