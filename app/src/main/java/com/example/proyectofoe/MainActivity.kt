package com.example.proyectofoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.proyectofoe.ui.screens.CLIENTES_S.ClientesScreen
import com.example.proyectofoe.ui.screens.HOME_S.HomeScreen
import com.example.proyectofoe.ui.screens.LOGIN_S.LoginScreen
import com.example.proyectofoe.ui.screens.PRODUCTOS_S.ProductosScreen
import com.example.proyectofoe.ui.theme.ProyectoFOETheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db: FoeDatabase = Room.databaseBuilder(
            applicationContext,
            FoeDatabase::class.java,
            "foe360_db"
        ).fallbackToDestructiveMigration(true)
            .build()

        setContent {
            ProyectoFOETheme {
                PantallaPrincipal(db)
            }
        }
    }
}

@Composable
fun PantallaPrincipal(
    db: FoeDatabase
) {
    var usuarioAutenticado by remember {
        mutableStateOf<UsuarioEntity?>(null)
    }

    var mostrarClientes by remember {
        mutableStateOf(false)
    }

    var mostrarProductos by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            usuarioAutenticado == null -> {
                LoginScreen(
                    usuarioCLFK = db.usuarioCLFK(),
                    onLoginExitoso = { usuario ->
                        usuarioAutenticado = usuario
                        mostrarClientes = false
                        mostrarProductos = false
                    }
                )
            }

            mostrarClientes -> {
                ClientesScreen(
                    clienteCLFK = db.clienteCLFK(),
                    onVolverInicio = {
                        mostrarClientes = false
                    }
                )
            }

            mostrarProductos -> {
                ProductosScreen(
                    productoCLFK = db.productoCLFK(),
                    onVolverInicio = {
                        mostrarProductos = false
                    }
                )
            }

            else -> {
                HomeScreen(
                    usuario = usuarioAutenticado!!,
                    clienteCLFK = db.clienteCLFK(),
                    productoCLFK = db.productoCLFK(),
                    onAbrirClientes = {
                        mostrarClientes = true
                        mostrarProductos = false
                    },
                    onAbrirProductos = {
                        mostrarProductos = true
                        mostrarClientes = false
                    },
                    onCerrarSesion = {
                        usuarioAutenticado = null
                        mostrarClientes = false
                        mostrarProductos = false
                    }
                )
            }
        }
    }
}
