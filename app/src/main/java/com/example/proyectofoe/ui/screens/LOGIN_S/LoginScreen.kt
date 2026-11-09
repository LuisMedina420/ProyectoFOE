package com.example.proyectofoe.ui.screens.LOGIN_S

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofoe.local.UsuarioDao
import com.example.proyectofoe.local.UsuarioEntity
import com.example.proyectofoe.ui.theme.BrandNavy
import com.example.proyectofoe.ui.theme.BrandRingGold
import com.example.proyectofoe.ui.theme.FoeCardBackground
import com.example.proyectofoe.ui.theme.FoeError
import com.example.proyectofoe.ui.theme.FoeErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnPrimaryContainer
import com.example.proyectofoe.ui.theme.FoeOnSurface
import com.example.proyectofoe.ui.theme.FoeOnSurfaceVariant
import com.example.proyectofoe.ui.theme.FoePrimary
import com.example.proyectofoe.ui.theme.FoePrimaryContainer
import com.example.proyectofoe.ui.theme.FoeSurface
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerLow
import com.example.proyectofoe.ui.theme.OnBrandNavy
import com.example.proyectofoe.ui.theme.OnBrandSubtitle
import com.example.proyectofoe.ui.theme.ProyectoFOETheme
import kotlinx.coroutines.launch

const val USUARIO_DEMO = "mzelaya"
const val CONTRASENA_DEMO = "ruta2026"
const val MAX_INTENTOS = 5
const val MINUTOS_BLOQUEO = 15

fun crearUsuarioPorDefecto(): UsuarioEntity {
    return UsuarioEntity(
        id = 1,
        usuario = USUARIO_DEMO,
        contrasena = CONTRASENA_DEMO,
        nombre = "Mario Zelaya",
        iniciales = "MZ",
        rol = "Vendedor",
        subtituloRol = "Autoventa · factura al momento",
        ruta = "R-07 Talanga - Guaimaca",
        vehiculo = "Camión C-12",
        placa = "HDA 4821",
        empresa = "Distribuidora La Montaña"
    )
}

fun validarCredencialesDemo(usuario: String, contrasena: String): Boolean {
    return usuario.trim().lowercase() == USUARIO_DEMO && contrasena == CONTRASENA_DEMO
}

fun construirMensajeIntentoFallido(intentosFallidos: Int): String {
    val restantes = MAX_INTENTOS - intentosFallidos
    return if (restantes <= 0) {
        "Demasiados intentos. Espera $MINUTOS_BLOQUEO:00 o pide a tu supervisor que te desbloquee."
    } else {
        val avisoIntentos = if (restantes <= 2) {
            if (restantes == 1) {
                " Te queda 1 intento antes de que se bloquee $MINUTOS_BLOQUEO minutos."
            } else {
                " Te quedan $restantes intentos antes de que se bloquee $MINUTOS_BLOQUEO minutos."
            }
        } else {
            ""
        }
        "Usuario o contraseña incorrectos.$avisoIntentos"
    }
}

@Composable
fun LoginScreen(
    usuarioDao: UsuarioDao,
    onLoginExitoso: (UsuarioEntity) -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var intentosFallidos by remember { mutableStateOf(0) }
    var bloqueado by remember { mutableStateOf(false) }
    var entrando by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }
    var mostrarAvisoOlvide by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // Semilla inicial del usuario del sistema si la base local está vacía
    LaunchedEffect(usuarioDao) {
        try {
            val existentes = usuarioDao.obtenerTodos()
            if (existentes.isEmpty()) {
                usuarioDao.insertar(crearUsuarioPorDefecto())
            }
        } catch (e: Exception) {
            // Se manejará en el inicio de sesión si ocurre algún problema
        }
    }

    val botonHabilitado = usuario.trim().isNotEmpty() &&
            contrasena.isNotEmpty() &&
            !bloqueado &&
            !entrando

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BrandNavy
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            // CABECERA AZUL MARINO (lg-hero del demo FOE 360)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandNavy)
                    .padding(top = 32.dp, bottom = 36.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo FOE 360
                Card(
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BrandNavy
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(BrandNavy)
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "360°",
                            color = BrandRingGold,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "FOE",
                            color = OnBrandNavy,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Text(
                    text = "Ventas en ruta",
                    color = OnBrandSubtitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            // TARJETA PRINCIPAL DE LOGIN (lg-card del demo FOE 360)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(FoeSurface)
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Inicia sesión",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoeOnSurface
                    )

                    Text(
                        text = "Con el usuario y la contraseña que te dio tu supervisor.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoeOnSurfaceVariant
                    )

                    // Campo Usuario
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { nuevo ->
                            usuario = nuevo
                            mostrarError = false
                            mensajeError = ""
                        },
                        label = { Text("Usuario") },
                        singleLine = true,
                        isError = mostrarError,
                        enabled = !bloqueado && !entrando,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo Contraseña
                    OutlinedTextField(
                        value = if (mostrarContrasena) contrasena else "•".repeat(contrasena.length),
                        onValueChange = { nuevo ->
                            if (mostrarContrasena) {
                                contrasena = nuevo
                            } else {
                                if (nuevo.length > contrasena.length) {
                                    val agregados = nuevo.filter { it != '•' }
                                    contrasena += agregados
                                } else if (nuevo.length < contrasena.length) {
                                    contrasena = contrasena.take(nuevo.length)
                                }
                            }
                            mostrarError = false
                            mensajeError = ""
                        },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        isError = mostrarError,
                        enabled = !bloqueado && !entrando,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Fila de opciones: Mostrar/Ocultar contraseña y Olvidé mi contraseña
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            selected = mostrarContrasena,
                            onClick = { mostrarContrasena = !mostrarContrasena },
                            label = {
                                Text(
                                    if (mostrarContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        )

                        OutlinedButton(
                            onClick = { mostrarAvisoOlvide = !mostrarAvisoOlvide }
                        ) {
                            Text("Olvidé mi contraseña")
                        }
                    }

                    // Aviso de "Olvidé mi contraseña" (igual al diálogo del demo)
                    if (mostrarAvisoOlvide) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = FoeSurfaceContainerLow
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "¿Olvidaste tu contraseña?",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = FoeOnSurface
                                )
                                Text(
                                    text = "Pide a tu supervisor que te asigne una nueva desde el escritorio de FOE 360. Mientras tanto no se puede entrar en este teléfono.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = FoeOnSurfaceVariant
                                )
                                Button(
                                    onClick = { mostrarAvisoOlvide = false },
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Text("Entendido")
                                }
                            }
                        }
                    }

                    // Mensaje de error / bloqueo (lgMsg del demo)
                    if (mensajeError.isNotBlank()) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = FoeErrorContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = mensajeError,
                                color = FoeOnErrorContainer,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    // Botón principal Entrar (lgGo del demo)
                    Button(
                        onClick = {
                            val usuarioLimpio = usuario.trim()
                            if (usuarioLimpio.isEmpty() || contrasena.isEmpty() || bloqueado || entrando) {
                                return@Button
                            }

                            entrando = true
                            mostrarError = false
                            mensajeError = ""

                            scope.launch {
                                try {
                                    val existentes = usuarioDao.obtenerTodos()
                                    if (existentes.isEmpty()) {
                                        usuarioDao.insertar(crearUsuarioPorDefecto())
                                    }

                                    val encontrado = usuarioDao.iniciarSesion(
                                        usuario = usuarioLimpio,
                                        contrasena = contrasena
                                    ) ?: if (validarCredencialesDemo(usuarioLimpio, contrasena)) {
                                        crearUsuarioPorDefecto()
                                    } else {
                                        null
                                    }

                                    if (encontrado != null) {
                                        intentosFallidos = 0
                                        mostrarError = false
                                        mensajeError = ""
                                        onLoginExitoso(encontrado)
                                    } else {
                                        intentosFallidos += 1
                                        contrasena = ""
                                        mostrarError = true
                                        if (MAX_INTENTOS - intentosFallidos <= 0) {
                                            bloqueado = true
                                        }
                                        mensajeError = construirMensajeIntentoFallido(intentosFallidos)
                                    }
                                } catch (e: Exception) {
                                    mensajeError = "Error al validar el usuario. Intenta de nuevo."
                                } finally {
                                    entrando = false
                                }
                            }
                        },
                        enabled = botonHabilitado,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FoePrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(
                            text = if (entrando) "Entrando..." else "Entrar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Cuadro de credenciales de prueba (lg-demo del demo FOE 360)
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = FoeSurfaceContainerLow
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Prueba:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FoeOnSurfaceVariant
                                )
                                Text(
                                    text = "$USUARIO_DEMO · $CONTRASENA_DEMO",
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = FoeOnSurface,
                                    fontSize = 14.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    if (!bloqueado) {
                                        usuario = USUARIO_DEMO
                                        contrasena = CONTRASENA_DEMO
                                        mostrarError = false
                                        mensajeError = ""
                                    }
                                },
                                enabled = !bloqueado && !entrando,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = FoePrimaryContainer,
                                    contentColor = FoeOnPrimaryContainer
                                )
                            ) {
                                Text(
                                    text = "Llenar",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Pie de página (lg-foot del demo FOE 360)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Teléfono T-04 · punto de emisión 004",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "SYS FOE · versión de prueba",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val fakeDao = object : UsuarioDao {
        override suspend fun insertar(usuario: UsuarioEntity) {}
        override suspend fun obtenerTodos(): List<UsuarioEntity> = listOf(crearUsuarioPorDefecto())
        override suspend fun iniciarSesion(usuario: String, contrasena: String): UsuarioEntity? =
            crearUsuarioPorDefecto()
    }
    ProyectoFOETheme {
        LoginScreen(
            usuarioDao = fakeDao,
            onLoginExitoso = {}
        )
    }
}
