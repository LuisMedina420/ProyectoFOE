package com.example.proyectofoe.ui.screens.HOME_S

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.proyectofoe.local.UsuarioEntity
import com.example.proyectofoe.ui.screens.LOGIN_S.crearUsuarioPorDefecto
import com.example.proyectofoe.ui.theme.FoeCardBackground
import com.example.proyectofoe.ui.theme.FoeOnPrimaryContainer
import com.example.proyectofoe.ui.theme.FoeOnSurface
import com.example.proyectofoe.ui.theme.FoeOnSurfaceVariant
import com.example.proyectofoe.ui.theme.FoePrimary
import com.example.proyectofoe.ui.theme.FoePrimaryContainer
import com.example.proyectofoe.ui.theme.FoeSurface
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerLow
import com.example.proyectofoe.ui.theme.ModuloAcero
import com.example.proyectofoe.ui.theme.ModuloAmbar
import com.example.proyectofoe.ui.theme.ModuloCoral
import com.example.proyectofoe.ui.theme.ModuloRuta
import com.example.proyectofoe.ui.theme.ModuloVerde
import com.example.proyectofoe.ui.theme.ModuloVioleta
import com.example.proyectofoe.ui.theme.ProyectoFOETheme

@Composable
fun HomeScreen(
    usuario: UsuarioEntity,
    onCerrarSesion: () -> Unit
) {
    var moduloSeleccionado by remember { mutableStateOf("") }
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

                    // Progreso de paradas del día
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "4 de 12 paradas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FoeOnSurface
                        )
                        Text(
                            text = "75 % efectividad",
                            style = MaterialTheme.typography.bodySmall,
                            color = FoeOnSurfaceVariant
                        )
                    }

                    // Indicadores del día (Vendido, Cobrado, Sin venta)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IndicadorDiaCard(
                            titulo = "Vendido",
                            valor = "L 8,940.75",
                            modifier = Modifier.weight(1f)
                        )
                        IndicadorDiaCard(
                            titulo = "Cobrado",
                            valor = "L 2,150.00",
                            modifier = Modifier.weight(1f)
                        )
                        IndicadorDiaCard(
                            titulo = "Sin venta",
                            valor = "1",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // TARJETA SIGUIENTE PARADA (next del demo FOE 360)
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FoeCardBackground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
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
                            text = "SIGUIENTE PARADA",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = FoeOnSurfaceVariant
                        )
                        Text(
                            text = "2.4 km · 7 min",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = FoeOnSurface
                        )
                    }

                    Text(
                        text = "Parada 5 de 12",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = FoePrimary
                    )

                    Text(
                        text = "Abarrotes Mi Esperanza",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )

                    Text(
                        text = "Rosa Mejía · Bo. Abajo, Cerro Grande",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoeOnSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { moduloSeleccionado = "Navegación hacia Abarrotes Mi Esperanza" },
                            colors = ButtonDefaults.buttonColors(containerColor = FoePrimary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Ir")
                        }

                        Button(
                            onClick = { moduloSeleccionado = "Llegada a Abarrotes Mi Esperanza" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FoePrimaryContainer,
                                contentColor = FoeOnPrimaryContainer
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Llegué")
                        }
                    }
                }
            }

            // MENÚ DE MÓDULOS (tiles del demo FOE 360)
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

                if (moduloSeleccionado.isNotBlank()) {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = FoePrimaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Módulo seleccionado: $moduloSeleccionado",
                            color = FoeOnPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Fila 1: Ruta y Clientes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TarjetaModulo(
                        titulo = "Ruta",
                        subtitulo = "8 por visitar",
                        insignia = "8",
                        colorFondo = ModuloRuta,
                        onClick = { moduloSeleccionado = "Ruta" },
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaModulo(
                        titulo = "Clientes",
                        subtitulo = "Cartera y saldos",
                        insignia = null,
                        colorFondo = ModuloVerde,
                        onClick = { moduloSeleccionado = "Clientes" },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Fila 2: Vender y Camión
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TarjetaModulo(
                        titulo = "Vender",
                        subtitulo = "Factura al momento",
                        insignia = null,
                        colorFondo = ModuloCoral,
                        onClick = { moduloSeleccionado = "Vender" },
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaModulo(
                        titulo = "Camión",
                        subtitulo = "Lo que llevas",
                        insignia = null,
                        colorFondo = ModuloAcero,
                        onClick = { moduloSeleccionado = "Camión" },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Fila 3: Cobros y Caja y cierre
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TarjetaModulo(
                        titulo = "Cobros",
                        subtitulo = "2 con mora",
                        insignia = "2",
                        colorFondo = ModuloAmbar,
                        onClick = { moduloSeleccionado = "Cobros" },
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaModulo(
                        titulo = "Caja y cierre",
                        subtitulo = "Arqueo y cierre",
                        insignia = null,
                        colorFondo = ModuloVioleta,
                        onClick = { moduloSeleccionado = "Caja y cierre" },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun IndicadorDiaCard(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = FoeSurfaceContainerLow
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(
                text = titulo,
                fontSize = 11.sp,
                color = FoeOnSurfaceVariant
            )
            Text(
                text = valor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = FoeOnSurface
            )
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
        modifier = modifier.height(116.dp)
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
                    text = "FOE",
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
            onCerrarSesion = {}
        )
    }
}
