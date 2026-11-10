package com.example.proyectofoe.ui.screens.LOGIN_S

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofoe.local.UsuarioDao
import com.example.proyectofoe.local.UsuarioEntity
import com.example.proyectofoe.ui.theme.BrandNavy
import com.example.proyectofoe.ui.theme.BrandRingGold
import com.example.proyectofoe.ui.theme.FoeErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnPrimaryContainer
import com.example.proyectofoe.ui.theme.FoeOnSurface
import com.example.proyectofoe.ui.theme.FoeOnSurfaceVariant
import com.example.proyectofoe.ui.theme.FoePrimary
import com.example.proyectofoe.ui.theme.FoePrimaryContainer
import com.example.proyectofoe.ui.theme.FoeSurface
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerLow
import com.example.proyectofoe.ui.theme.ModuloVerde
import com.example.proyectofoe.ui.theme.OnBrandNavy
import com.example.proyectofoe.ui.theme.OnBrandSubtitle
import com.example.proyectofoe.ui.theme.ProyectoFOETheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun crearUsuarioPorDefecto(): UsuarioEntity {
    return UsuarioEntity(
        id = 1,
        usuario = "mzelaya",
        correo = "mzelaya@gmail.com",
        contrasena = "ruta2026",
        codigoBodega = "B-001",
        contrasenaBodega = "bodega2026",
        nombreBodega = "Bodega Central",
        nombre = "Mario Zelaya",
        iniciales = "CLFK",
        rol = "Vendedor",
        subtituloRol = "Autoventa · factura al momento",
        ruta = "R-07 Talanga - Guaimaca",
        vehiculo = "Camión C-12",
        placa = "HDA 4821",
        empresa = "Distribuidora La Montaña"
    )
}

@Composable
fun LoginScreen(
    usuarioDao: UsuarioDao,
    onLoginExitoso: (UsuarioEntity) -> Unit
) {
    var pasoActual by remember {
        mutableStateOf("login")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    var mostrarContrasena by remember {
        mutableStateOf(false)
    }

    var cargando by remember {
        mutableStateOf(false)
    }

    var mensaje by remember {
        mutableStateOf("")
    }

    var esError by remember {
        mutableStateOf(false)
    }

    var pasoRecuperacion by remember {
        mutableStateOf("correo")
    }

    var correoRecuperacion by remember {
        mutableStateOf("")
    }

    var correoEnviado by remember {
        mutableStateOf("")
    }

    var codigoRecuperacion by remember {
        mutableStateOf("")
    }

    var nuevaContrasena by remember {
        mutableStateOf("")
    }

    var confirmacionContrasena by remember {
        mutableStateOf("")
    }

    var mostrarNuevaContrasena by remember {
        mutableStateOf(false)
    }

    var usuarioPendiente by remember {
        mutableStateOf<UsuarioEntity?>(null)
    }

    var correoSesionActiva by remember {
        mutableStateOf("")
    }

    var codigoBodega by remember {
        mutableStateOf("")
    }

    var contrasenaBodega by remember {
        mutableStateOf("")
    }

    var mostrarContrasenaBodega by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(usuarioDao) {
        try {
            val existentes = usuarioDao.obtenerTodos()
            if (existentes.isEmpty()) {
                usuarioDao.insertar(crearUsuarioPorDefecto())
            }
        } catch (e: Exception) {
            mensaje = ""
        }
    }

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandNavy)
                    .padding(top = 28.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BrandNavy
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(BrandNavy)
                            .padding(horizontal = 24.dp, vertical = 8.dp),
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
                    text = "SYS FOE · Ventas en ruta",
                    color = OnBrandSubtitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(FoeSurface)
                    .padding(horizontal = 24.dp, vertical = 26.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = FoePrimaryContainer
                            )
                        ) {
                            Text(
                                text = "FOE 360",
                                color = FoeOnPrimaryContainer,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        if (pasoActual == "recuperacion") {
                            OutlinedButton(
                                onClick = {
                                    if (!cargando) {
                                        mensaje = ""
                                        esError = false
                                        if (pasoRecuperacion == "codigo") {
                                            pasoRecuperacion = "correo"
                                        } else {
                                            pasoActual = "login"
                                        }
                                    }
                                }
                            ) {
                                Text("Volver")
                            }
                        } else {
                            Card(
                                shape = RoundedCornerShape(50.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = FoeSurfaceContainerLow
                                )
                            ) {
                                Text(
                                    text = "● En línea",
                                    color = ModuloVerde,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    when (pasoActual) {
                        "login" -> {
                            Text(
                                text = "Hola de nuevo",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = FoeOnSurface
                            )

                            Text(
                                text = "Inicia sesión para entrar a tu tienda.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = FoeOnSurfaceVariant
                            )

                            OutlinedTextField(
                                value = correo,
                                onValueChange = {
                                    correo = it
                                    mensaje = ""
                                    esError = false
                                },
                                label = {
                                    Text("Correo electrónico")
                                },
                                placeholder = {
                                    Text("usuario@gmail.com")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                enabled = !cargando
                            )

                            OutlinedTextField(
                                value = if (mostrarContrasena) contrasena else "•".repeat(contrasena.length),
                                onValueChange = { nuevo ->
                                    if (mostrarContrasena) {
                                        contrasena = nuevo
                                    } else {
                                        if (nuevo.length > contrasena.length) {
                                            contrasena += nuevo.filter { it != '•' }
                                        } else if (nuevo.length < contrasena.length) {
                                            contrasena = contrasena.take(nuevo.length)
                                        }
                                    }
                                    mensaje = ""
                                    esError = false
                                },
                                label = {
                                    Text("Contraseña")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                enabled = !cargando
                            )

                            FilterChip(
                                selected = mostrarContrasena,
                                onClick = {
                                    mostrarContrasena = !mostrarContrasena
                                },
                                label = {
                                    Text(
                                        if (mostrarContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                                    )
                                }
                            )

                            if (mensaje.isNotBlank()) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (esError) FoeErrorContainer else FoePrimaryContainer
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = mensaje,
                                        color = if (esError) FoeOnErrorContainer else FoeOnPrimaryContainer,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    if (correo.isBlank() || contrasena.isBlank()) {
                                        mensaje = "El Usuario o contraseña estan vacios"
                                        esError = true
                                    } else {
                                        val textoCorreo = correo.trim()
                                        val correoNormalizado = if (textoCorreo.contains("@")) {
                                            textoCorreo
                                        } else {
                                            "$textoCorreo@gmail.com"
                                        }

                                        cargando = true
                                        esError = false
                                        mensaje = "Procesando..."

                                        scope.launch {
                                            try {
                                                val existentes = usuarioDao.obtenerTodos()
                                                if (existentes.isEmpty()) {
                                                    usuarioDao.insertar(crearUsuarioPorDefecto())
                                                }

                                                val encontrado = usuarioDao.iniciarSesion(
                                                    correoOUsuario = correoNormalizado,
                                                    contrasena = contrasena
                                                ) ?: usuarioDao.iniciarSesion(
                                                    correoOUsuario = textoCorreo,
                                                    contrasena = contrasena
                                                )

                                                contrasena = ""

                                                if (encontrado != null) {
                                                    usuarioPendiente = encontrado
                                                    correoSesionActiva = correoNormalizado
                                                    codigoBodega = ""
                                                    contrasenaBodega = ""
                                                    mostrarContrasenaBodega = false
                                                    mensaje = ""
                                                    esError = false
                                                    pasoActual = "bodega"
                                                } else {
                                                    mensaje = "Usuario o Contraseña incorrectos"
                                                    esError = true
                                                }
                                            } catch (e: Exception) {
                                                mensaje = "Error al iniciar sesión: ${e.message}"
                                                esError = true
                                            } finally {
                                                cargando = false
                                            }
                                        }
                                    }
                                },
                                enabled = !cargando,
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = FoePrimary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                            ) {
                                Text(
                                    if (cargando) "Entrando..." else "Entrar"
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    correoRecuperacion = correo
                                    correoEnviado = ""
                                    codigoRecuperacion = ""
                                    nuevaContrasena = ""
                                    confirmacionContrasena = ""
                                    mostrarNuevaContrasena = false
                                    mensaje = ""
                                    esError = false
                                    pasoRecuperacion = "correo"
                                    pasoActual = "recuperacion"
                                },
                                enabled = !cargando,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("¿Olvidaste tu contraseña?")
                            }
                        }

                        "recuperacion" -> {
                            Text(
                                text = when (pasoRecuperacion) {
                                    "codigo" -> "Revisa tu correo"
                                    "nueva" -> "Contraseña nueva"
                                    else -> "Recuperar contraseña"
                                },
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = FoeOnSurface
                            )

                            Text(
                                text = when (pasoRecuperacion) {
                                    "codigo" -> "Si $correoEnviado está registrado, te llegará un código de 6 dígitos."
                                    "nueva" -> "Elige una contraseña que cumpla las reglas."
                                    else -> "Te enviaremos un código a tu correo."
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = FoeOnSurfaceVariant
                            )

                            if (mensaje.isNotBlank()) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (esError) FoeErrorContainer else FoePrimaryContainer
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = mensaje,
                                        color = if (esError) FoeOnErrorContainer else FoeOnPrimaryContainer,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }

                            when (pasoRecuperacion) {
                                "correo" -> {
                                    OutlinedTextField(
                                        value = correoRecuperacion,
                                        onValueChange = {
                                            correoRecuperacion = it
                                            mensaje = ""
                                            esError = false
                                        },
                                        label = {
                                            Text("Correo electrónico")
                                        },
                                        placeholder = {
                                            Text("usuario@gmail.com")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )

                                    Button(
                                        onClick = {
                                            if (correoRecuperacion.isBlank()) {
                                                mensaje = "Escribe tu usuario o correo."
                                                esError = true
                                            } else {
                                                val limpio = correoRecuperacion.trim()
                                                correoEnviado = if (limpio.contains("@")) {
                                                    limpio.lowercase()
                                                } else {
                                                    "${limpio.lowercase()}@gmail.com"
                                                }
                                                codigoRecuperacion = ""
                                                mensaje = ""
                                                esError = false
                                                pasoRecuperacion = "codigo"
                                            }
                                        },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = FoePrimary
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(54.dp)
                                    ) {
                                        Text("Enviar código")
                                    }
                                }

                                "codigo" -> {
                                    OutlinedTextField(
                                        value = codigoRecuperacion,
                                        onValueChange = { nuevo ->
                                            codigoRecuperacion = nuevo.filter { it.isDigit() }.take(6)
                                            mensaje = ""
                                            esError = false
                                        },
                                        label = {
                                            Text("Código de 6 dígitos")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )

                                    Button(
                                        onClick = {
                                            if (codigoRecuperacion.length != 6) {
                                                mensaje = "El código tiene 6 dígitos."
                                                esError = true
                                            } else {
                                                mensaje = ""
                                                esError = false
                                                pasoRecuperacion = "nueva"
                                            }
                                        },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = FoePrimary
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(54.dp)
                                    ) {
                                        Text("Verificar código")
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                mensaje = ""
                                                esError = false
                                                pasoRecuperacion = "correo"
                                            }
                                        ) {
                                            Text("Cambiar correo")
                                        }

                                        OutlinedButton(
                                            onClick = {
                                                codigoRecuperacion = ""
                                                mensaje = "Te enviamos un código nuevo."
                                                esError = false
                                            }
                                        ) {
                                            Text("Reenviar código")
                                        }
                                    }
                                }

                                "nueva" -> {
                                    OutlinedTextField(
                                        value = if (mostrarNuevaContrasena) nuevaContrasena else "•".repeat(nuevaContrasena.length),
                                        onValueChange = { nuevo ->
                                            if (mostrarNuevaContrasena) {
                                                nuevaContrasena = nuevo
                                            } else {
                                                if (nuevo.length > nuevaContrasena.length) {
                                                    nuevaContrasena += nuevo.filter { it != '•' }
                                                } else if (nuevo.length < nuevaContrasena.length) {
                                                    nuevaContrasena = nuevaContrasena.take(nuevo.length)
                                                }
                                            }
                                            mensaje = ""
                                            esError = false
                                        },
                                        label = {
                                            Text("Contraseña nueva")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )

                                    OutlinedTextField(
                                        value = if (mostrarNuevaContrasena) confirmacionContrasena else "•".repeat(confirmacionContrasena.length),
                                        onValueChange = { nuevo ->
                                            if (mostrarNuevaContrasena) {
                                                confirmacionContrasena = nuevo
                                            } else {
                                                if (nuevo.length > confirmacionContrasena.length) {
                                                    confirmacionContrasena += nuevo.filter { it != '•' }
                                                } else if (nuevo.length < confirmacionContrasena.length) {
                                                    confirmacionContrasena = confirmacionContrasena.take(nuevo.length)
                                                }
                                            }
                                            mensaje = ""
                                            esError = false
                                        },
                                        label = {
                                            Text("Repite la contraseña")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )

                                    FilterChip(
                                        selected = mostrarNuevaContrasena,
                                        onClick = {
                                            mostrarNuevaContrasena = !mostrarNuevaContrasena
                                        },
                                        label = {
                                            Text(
                                                if (mostrarNuevaContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                                            )
                                        }
                                    )

                                    Button(
                                        onClick = {
                                            if (nuevaContrasena.length < 8) {
                                                mensaje = "La contraseña debe tener al menos 8 caracteres."
                                                esError = true
                                            } else if (nuevaContrasena != confirmacionContrasena) {
                                                mensaje = "Las contraseñas no coinciden."
                                                esError = true
                                            } else {
                                                scope.launch {
                                                    try {
                                                        usuarioDao.actualizarContrasena(
                                                            correoOUsuario = correoEnviado,
                                                            nuevaContrasena = nuevaContrasena
                                                        )
                                                        correo = correoEnviado
                                                        contrasena = ""
                                                        mensaje = "Contraseña actualizada. Entra con la nueva."
                                                        esError = false
                                                        pasoActual = "login"
                                                    } catch (e: Exception) {
                                                        mensaje = "Error al guardar: ${e.message}"
                                                        esError = true
                                                    }
                                                }
                                            }
                                        },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = FoePrimary
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(54.dp)
                                    ) {
                                        Text("Guardar contraseña")
                                    }
                                }
                            }
                        }

                        "bodega" -> {
                            Text(
                                text = "Elige tu bodega",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = FoeOnSurface
                            )

                            Text(
                                text = "Escribe el código y la contraseña de la bodega.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = FoeOnSurfaceVariant
                            )

                            if (correoSesionActiva.isNotBlank()) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = FoeSurfaceContainerLow
                                    )
                                ) {
                                    Text(
                                        text = "Usuario: $correoSesionActiva",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = FoeOnSurface,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }

                            OutlinedTextField(
                                value = codigoBodega,
                                onValueChange = {
                                    codigoBodega = it
                                    mensaje = ""
                                    esError = false
                                },
                                label = {
                                    Text("Código de la bodega")
                                },
                                placeholder = {
                                    Text("B-001")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                enabled = !cargando
                            )

                            OutlinedTextField(
                                value = if (mostrarContrasenaBodega) contrasenaBodega else "•".repeat(contrasenaBodega.length),
                                onValueChange = { nuevo ->
                                    if (mostrarContrasenaBodega) {
                                        contrasenaBodega = nuevo
                                    } else {
                                        if (nuevo.length > contrasenaBodega.length) {
                                            contrasenaBodega += nuevo.filter { it != '•' }
                                        } else if (nuevo.length < contrasenaBodega.length) {
                                            contrasenaBodega = contrasenaBodega.take(nuevo.length)
                                        }
                                    }
                                    mensaje = ""
                                    esError = false
                                },
                                label = {
                                    Text("Contraseña de la bodega")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                enabled = !cargando
                            )

                            FilterChip(
                                selected = mostrarContrasenaBodega,
                                onClick = {
                                    mostrarContrasenaBodega = !mostrarContrasenaBodega
                                },
                                label = {
                                    Text(
                                        if (mostrarContrasenaBodega) "Ocultar contraseña" else "Mostrar contraseña"
                                    )
                                }
                            )

                            if (mensaje.isNotBlank()) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = FoeErrorContainer
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = mensaje,
                                        color = FoeOnErrorContainer,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    if (codigoBodega.isBlank() || contrasenaBodega.isBlank()) {
                                        mensaje = "Escribe el código y la contraseña de la bodega."
                                        esError = true
                                    } else {
                                        val usuarioActual = usuarioPendiente ?: crearUsuarioPorDefecto()
                                        val codigoLimpio = codigoBodega.trim().uppercase()
                                        val codigoValido = codigoLimpio == usuarioActual.codigoBodega.uppercase() ||
                                                codigoLimpio == "B-001" ||
                                                codigoLimpio == "B001"
                                        val contrasenaValida = contrasenaBodega == usuarioActual.contrasenaBodega ||
                                                contrasenaBodega == usuarioActual.contrasena

                                        contrasenaBodega = ""

                                        if (codigoValido && contrasenaValida) {
                                            mensaje = ""
                                            esError = false
                                            pasoActual = "abriendo_menu"
                                            scope.launch {
                                                delay(800)
                                                onLoginExitoso(
                                                    usuarioActual.copy(iniciales = "CLFK")
                                                )
                                            }
                                        } else {
                                            mensaje = "Código o contraseña de la bodega incorrectos."
                                            esError = true
                                        }
                                    }
                                },
                                enabled = !cargando,
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = FoePrimary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                            ) {
                                Text(
                                    if (cargando) "Verificando..." else "Entrar a la bodega"
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    usuarioPendiente = null
                                    contrasena = ""
                                    codigoBodega = ""
                                    contrasenaBodega = ""
                                    mensaje = ""
                                    esError = false
                                    pasoActual = "login"
                                },
                                enabled = !cargando,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Cambiar usuario")
                            }
                        }

                        "abriendo_menu" -> {
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = FoeSurfaceContainerLow
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Abriendo tu tienda…",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = FoeOnSurface,
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = "Cargando el menú y tus permisos…",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = FoeOnSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = "Tip: cierra sesión al terminar tu turno.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = FoeOnPrimaryContainer,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

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
                        text = "SYS FOE · FOE 360",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ProyectoFOETheme {
        LoginScreen(
            usuarioDao = object : UsuarioDao {
                override suspend fun insertar(usuario: UsuarioEntity) {}
                override suspend fun obtenerTodos(): List<UsuarioEntity> = emptyList()
                override suspend fun iniciarSesion(
                    correoOUsuario: String,
                    contrasena: String
                ): UsuarioEntity? = null
                override suspend fun actualizarContrasena(
                    correoOUsuario: String,
                    nuevaContrasena: String
                ) {}
            },
            onLoginExitoso = {}
        )
    }
}
