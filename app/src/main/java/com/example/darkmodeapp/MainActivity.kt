package com.example.darkmodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.darkmodeapp.ui.theme.DarkModeAppTheme
import com.example.darkmodeapp.ui.theme.StoreDarkMode
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val modoOscuro = remember { mutableStateOf(false)}
            DarkModeAppTheme (darkTheme = modoOscuro.value){
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Paleta(modoOscuro = modoOscuro)
                }
            }
        }
    }
}

@Composable
fun DarkMode(darkMode: MutableState<Boolean>, onDarkModeToggle: () -> Unit) {
    Button(onClick = {
        darkMode.value = !darkMode.value
        onDarkModeToggle()
    }) {
        Icon(imageVector = Icons.Default.Star, contentDescription = "DarkMode")
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Dark Mode", fontSize = 30.sp)
    }
}

@Composable
fun Paleta(modoOscuro: MutableState<Boolean>) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreDarkMode(context)
    val isOscActivo = dataStore.getModoOscuro.collectAsState(initial = false)
    modoOscuro.value = isOscActivo.value
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        DarkMode(darkMode = modoOscuro) {
            scope.launch {
                dataStore.saveModoOscuro(modoOscuro.value)
            }
        }
    }
}