package com.example.navigationwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationwithcompose.ui.theme.NavigationwithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationwithComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavController()
                }
            }
        }
    }
}

@Composable
fun MyNavController(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    startDestination: String = "home"
) {
    // NavHost作成
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToFriends = {
                    navController.navigate("friendsList")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateToFriends = {
                    navController.navigate("friendsList") {
                        popUpTo("home")
                    }
                }
            )
        }
        composable("friendsList") {
            FriendsListScreen {
                navController.navigate("profile") {
                    popUpTo("home")
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onNavigateToFriends: () -> Unit
) {
    Button(onClick = onNavigateToFriends) {
        Text(text = "See friends list")
    }
}

@Composable
fun FriendsListScreen(
    onNavigateToProfile: () -> Unit
) {
    Button(onClick = onNavigateToProfile) {
        Text(text = "Back to Profile Screen")
    }
}

@Composable
fun HomeScreen(
    onNavigateToFriends: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Button(onClick = onNavigateToFriends) {
            Text(text = "To Profile Screen")
        }
        Button(onClick = onNavigateToProfile) {
            Text(text = "See friends list")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    NavigationwithComposeTheme {
        ProfileScreen({ })
    }
}