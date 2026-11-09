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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofoe.local.UsuarioEntity
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
    onAbrirClientes: () -> Unit,
    onAbrirProductosDesdeClientes: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val primerNombre = usuario.nombre.split(" ").firstOrNull() ?: usuario.nombre

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
            // ENCABEZADO DE INICIO (hero del demo FOE 360)
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
                    // Barra superior con marca, iniciales y botón de cerrar sesión
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
                        text = "Hola, $primerNombre",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoeOnSurface
                    )

                    Text(
                        text = "Viernes 9 oct · ${usuario.ruta}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoeOnSurfaceVariant
                    )

                    // Chip de rol y vehículo
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

            // MENÚ DE MÓDULOS DISPONIBLES (Únicamente Clientes y Productos)
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
                        subtitulo = "Cartera y saldos",
                        insignia = "12",
                        colorFondo = ModuloVerde,
                        onClick = onAbrirClientes,
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaModulo(
                        titulo = "Productos",
                        subtitulo = "Desde clientes",
                        insignia = "14",
                        colorFondo = ModuloAcero,
                        onClick = onAbrirProductosDesdeClientes,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Tarjeta de acceso rápido al apartado de Clientes y visualización de Productos
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = FoeCardBackground
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Apartado de Clientes y Productos",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FoeOnSurface
                        )
                        Text(
                            text = "Ingresa a Clientes para consultar la cartera de la ruta, registrar nuevos clientes y visualizar los productos asignados a cada cliente.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = FoeOnSurfaceVariant
                        )
                        Button(
                            onClick = onAbrirClientes,
                            colors = ButtonDefaults.buttonColors(containerColor = ModuloVerde),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Entrar al módulo de Clientes")
                        }
                    }
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
    ProyectoFOETheme {
        HomeScreen(
            usuario = crearUsuarioPorDefecto(),
            onAbrirClientes = {},
            onAbrirProductosDesdeClientes = {},
            onCerrarSesion = {}
        )
    }
}
