package com.example.proyectofoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.proyectofoe.local.FoeDatabase
import com.example.proyectofoe.local.UsuarioEntity
import com.example.proyectofoe.ui.screens.HOME_S.HomeScreen
import com.example.proyectofoe.ui.screens.LOGIN_S.LoginScreen
import com.example.proyectofoe.ui.theme.ProyectoFOETheme

class MainActivity : ComponentActivity() {

    private lateinit var database: FoeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            FoeDatabase::class.java,
            "foe360_db"
        ).fallbackToDestructiveMigration(true).build()

        setContent {
            ProyectoFOETheme {
                PantallaPrincipal(database)
            }
        }
    }
}

@Composable
private fun PantallaPrincipal(database: FoeDatabase) {
    var usuarioAutenticado by remember { mutableStateOf<UsuarioEntity?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            usuarioAutenticado != null -> {
                HomeScreen(
                    usuario = usuarioAutenticado!!,
                    onCerrarSesion = { usuarioAutenticado = null }
                )
            }

            else -> {
                LoginScreen(
                    usuarioDao = database.usuarioDao(),
                    onLoginExitoso = { usuario ->
                        usuarioAutenticado = usuario
                    }
                )
            }
        }
    }
}
