package com.example.proyectofoe.ui.screens.HOME_S

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofoe.local.ClienteCLFK
import com.example.proyectofoe.local.ClienteEntity
import com.example.proyectofoe.local.ProductoCLFK
import com.example.proyectofoe.local.ProductoEntity
import com.example.proyectofoe.local.UsuarioEntity
import com.example.proyectofoe.ui.screens.CLIENTES_S.crearClientesPorDefecto
import com.example.proyectofoe.ui.screens.CLIENTES_S.crearProductosPorDefecto
import com.example.proyectofoe.ui.screens.LOGIN_S.crearUsuarioPorDefecto
import com.example.proyectofoe.ui.theme.FoeCardBackground
import com.example.proyectofoe.ui.theme.FoeOnPrimaryContainer
import com.example.proyectofoe.ui.theme.FoeOnSurface
import com.example.proyectofoe.ui.theme.FoeOnSurfaceVariant
import com.example.proyectofoe.ui.theme.FoePrimaryContainer
import com.example.proyectofoe.ui.theme.FoeSurface
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerLow
import com.example.proyectofoe.ui.theme.ModuloAcero
import com.example.proyectofoe.ui.theme.ModuloVerde
import com.example.proyectofoe.ui.theme.ProyectoFOETheme

@Composable
fun HomeScreen(
    usuario: UsuarioEntity,
    clienteCLFK: ClienteCLFK,
    productoCLFK: ProductoCLFK,
    onAbrirClientes: () -> Unit,
    onAbrirProductos: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val nombreUsuarioSaludo = usuario.usuario.ifBlank { usuario.nombre }

    var totalClientes by remember {
        mutableStateOf(12)
    }

    var totalProductos by remember {
        mutableStateOf(14)
    }

    LaunchedEffect(clienteCLFK, productoCLFK) {
        try {
            var clientesDb = clienteCLFK.obtenerTodosOrdenadosPorNombre()
            if (clientesDb.isEmpty()) {
                clienteCLFK.insertarTodos(crearClientesPorDefecto())
                clientesDb = clienteCLFK.obtenerTodosOrdenadosPorNombre()
            }
            totalClientes = clientesDb.size

            var productosDb = productoCLFK.obtenerTodos()
            if (productosDb.isEmpty()) {
                productoCLFK.insertarTodos(crearProductosPorDefecto())
                productosDb = productoCLFK.obtenerTodos()
            }
            totalProductos = productosDb.size
        } catch (e: Exception) {
            totalClientes = crearClientesPorDefecto().size
            totalProductos = crearProductosPorDefecto().size
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = FoeSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ENCABEZADO DE INICIO
            Card(
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FoeCardBackground
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "FOE 360",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = FoeOnSurface
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                shape = RoundedCornerShape(50.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = FoePrimaryContainer
                                )
                            ) {
                                Text(
                                    text = usuario.iniciales,
                                    color = FoeOnPrimaryContainer,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }

                            OutlinedButton(
                                onClick = onCerrarSesion
                            ) {
                                Text("Cerrar sesión")
                            }
                        }
                    }

                    Text(
                        text = "Hola, $nombreUsuarioSaludo",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoeOnSurface
                    )

                    Text(
                        text = "Viernes 9 oct · ${usuario.ruta}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoeOnSurfaceVariant
                    )

                    Card(
                        shape = RoundedCornerShape(50.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = FoeSurfaceContainerLow
                        )
                    ) {
                        Text(
                            text = "${usuario.rol} · ${usuario.vehiculo} (${usuario.placa})",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = FoeOnSurface,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // MENÚ DE MÓDULOS POR SEPARADO (Clientes y Productos)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "MENÚ",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = FoeOnSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TarjetaModulo(
                        titulo = "Clientes",
                        subtitulo = "Cartera y registro",
                        insignia = "$totalClientes",
                        colorFondo = ModuloVerde,
                        onClick = onAbrirClientes,
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaModulo(
                        titulo = "Productos",
                        subtitulo = "Catálogo y registro",
                        insignia = "$totalProductos",
                        colorFondo = ModuloAcero,
                        onClick = onAbrirProductos,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaModulo(
    titulo: String,
    subtitulo: String,
    insignia: String?,
    colorFondo: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondo
        ),
        modifier = modifier.height(124.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FOE 360",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                if (insignia != null) {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = insignia,
                            color = colorFondo,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Column {
                Text(
                    text = titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = subtitulo,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val fakeClienteCLFK = object : ClienteCLFK {
        override suspend fun insertar(cliente: ClienteEntity) {}
        override suspend fun insertarTodos(clientes: List<ClienteEntity>) {}
        override suspend fun actualizar(cliente: ClienteEntity) {}
        override suspend fun obtenerTodosOrdenadosPorNombre(): List<ClienteEntity> =
            crearClientesPorDefecto()
        override suspend fun obtenerTodosPorParada(): List<ClienteEntity> =
            crearClientesPorDefecto()
        override suspend fun obtenerPorId(id: Int): ClienteEntity? =
            crearClientesPorDefecto().firstOrNull()
    }
    val fakeProductoCLFK = object : ProductoCLFK {
        override suspend fun insertar(producto: ProductoEntity) {}
        override suspend fun insertarTodos(productos: List<ProductoEntity>) {}
        override suspend fun obtenerTodos(): List<ProductoEntity> = crearProductosPorDefecto()
        override suspend fun obtenerPorCategoria(categoria: String): List<ProductoEntity> =
            crearProductosPorDefecto()
    }
    ProyectoFOETheme {
        HomeScreen(
            usuario = crearUsuarioPorDefecto(),
            clienteCLFK = fakeClienteCLFK,
            productoCLFK = fakeProductoCLFK,
            onAbrirClientes = {},
            onAbrirProductos = {},
            onCerrarSesion = {}
        )
    }
}
