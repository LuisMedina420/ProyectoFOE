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
import com.example.proyectofoe.ui.screens.PRODUCTOS_S.CarritoScreen
import com.example.proyectofoe.ui.screens.PRODUCTOS_S.ItemCarrito
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

    var mostrarCarrito by remember {
        mutableStateOf(false)
    }

    var carritoItems by remember {
        mutableStateOf(emptyList<ItemCarrito>())
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
                        mostrarCarrito = false
                    }
                )
            }

            mostrarCarrito -> {
                CarritoScreen(
                    items = carritoItems,
                    productoCLFK = db.productoCLFK(),
                    onCambiarCantidad = { productoId, nuevaCantidad ->
                        carritoItems = carritoItems.map { item ->
                            if (item.producto.id == productoId) {
                                val maximo = if (item.producto.stockCamion > 0) item.producto.stockCamion else nuevaCantidad
                                item.copy(cantidad = nuevaCantidad.coerceIn(1, maximo))
                            } else {
                                item
                            }
                        }
                    },
                    onQuitarProducto = { productoId ->
                        carritoItems = carritoItems.filterNot { it.producto.id == productoId }
                    },
                    onVaciarCarrito = {
                        carritoItems = emptyList()
                    },
                    onVentaCompletada = {
                        carritoItems = emptyList()
                    },
                    onVolver = {
                        mostrarCarrito = false
                    },
                    onIrAProductos = {
                        mostrarCarrito = false
                        mostrarProductos = true
                        mostrarClientes = false
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
                    carritoItems = carritoItems,
                    onAgregarAlCarrito = { producto, cantidad ->
                        val existente = carritoItems.firstOrNull { it.producto.id == producto.id }
                        carritoItems = if (existente != null) {
                            carritoItems.map { item ->
                                if (item.producto.id == producto.id) {
                                    val suma = item.cantidad + cantidad
                                    val maximo = if (producto.stockCamion > 0) producto.stockCamion else suma
                                    item.copy(producto = producto, cantidad = suma.coerceAtMost(maximo))
                                } else {
                                    item
                                }
                            }
                        } else {
                            carritoItems + ItemCarrito(producto = producto, cantidad = cantidad)
                        }
                    },
                    onAbrirCarrito = {
                        mostrarCarrito = true
                    },
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
                    totalCarrito = carritoItems.sumOf { it.cantidad },
                    onAbrirClientes = {
                        mostrarClientes = true
                        mostrarProductos = false
                        mostrarCarrito = false
                    },
                    onAbrirProductos = {
                        mostrarProductos = true
                        mostrarClientes = false
                        mostrarCarrito = false
                    },
                    onAbrirCarrito = {
                        mostrarCarrito = true
                        mostrarClientes = false
                        mostrarProductos = false
                    },
                    onCerrarSesion = {
                        usuarioAutenticado = null
                        mostrarClientes = false
                        mostrarProductos = false
                        mostrarCarrito = false
                        carritoItems = emptyList()
                    }
                )
            }
        }
    }
}
