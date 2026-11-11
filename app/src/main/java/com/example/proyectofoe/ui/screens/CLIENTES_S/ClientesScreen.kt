package com.example.proyectofoe.ui.screens.CLIENTES_S

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofoe.local.ClienteCLFK
import com.example.proyectofoe.local.ClienteEntity
import com.example.proyectofoe.local.ProductoEntity
import com.example.proyectofoe.ui.components.CamaraSection
import com.example.proyectofoe.ui.theme.BrandNavy
import com.example.proyectofoe.ui.theme.FoeCardBackground
import com.example.proyectofoe.ui.theme.FoeErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnPrimaryContainer
import com.example.proyectofoe.ui.theme.FoeOnSurface
import com.example.proyectofoe.ui.theme.FoeOnSurfaceVariant
import com.example.proyectofoe.ui.theme.FoeOutlineVariant
import com.example.proyectofoe.ui.theme.FoePrimary
import com.example.proyectofoe.ui.theme.FoePrimaryContainer
import com.example.proyectofoe.ui.theme.FoeSurface
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerLow
import com.example.proyectofoe.ui.theme.ModuloAmbar
import com.example.proyectofoe.ui.theme.ModuloCoral
import com.example.proyectofoe.ui.theme.ModuloRojo
import com.example.proyectofoe.ui.theme.ModuloVerde
import com.example.proyectofoe.ui.theme.OnBrandNavy
import com.example.proyectofoe.ui.theme.OnBrandSubtitle
import com.example.proyectofoe.ui.theme.ProyectoFOETheme
import kotlinx.coroutines.launch
import java.util.Locale

fun formatearLempiras(centavos: Int): String {
    val lempiras = centavos / 100.0
    return "L " + String.format(Locale.US, "%,.2f", lempiras)
}

fun obtenerIniciales(nombreCompleto: String): String {
    val partes = nombreCompleto.trim().split(" ").filter { it.isNotBlank() }
    if (partes.isEmpty()) return "CL"
    val primera = partes[0].first().uppercaseChar().toString()
    val segunda = if (partes.size > 1) partes[1].first().uppercaseChar().toString() else ""
    return primera + segunda
}

fun crearClientesPorDefecto(): List<ClienteEntity> {
    return listOf(
        ClienteEntity(
            id = 1,
            parada = 1,
            nombreNegocio = "Pulpería La Bendición",
            dueno = "María Flores",
            iniciales = "MF",
            direccion = "Bo. El Centro, Talanga",
            telefono = "9841-1101",
            rtnODni = "0801198501234",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 0,
            estadoVisita = "venta",
            detalleVisita = "Vendida · L 3,480.50 (8:05)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "1:2,3:1"
        ),
        ClienteEntity(
            id = 2,
            parada = 2,
            nombreNegocio = "Abarrotería El Buen Precio",
            dueno = "Julio Cárcamo",
            iniciales = "JC",
            direccion = "Col. San Juan, Talanga",
            telefono = "9722-3302",
            rtnODni = "0824197900452",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 125000,
            estadoVisita = "noventa",
            detalleVisita = "Sin venta · Local cerrado (8:31)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "2:2,5:1"
        ),
        ClienteEntity(
            id = 3,
            parada = 3,
            nombreNegocio = "Mini Súper Doña Tere",
            dueno = "Teresa Andino",
            iniciales = "TA",
            direccion = "Salida a Guaimaca, km 3",
            telefono = "9910-4403",
            rtnODni = "08011982045101",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 200000,
            estadoVisita = "venta",
            detalleVisita = "Vendida · L 4,210.25 (9:02)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "1:3,7:2,10:1"
        ),
        ClienteEntity(
            id = 4,
            parada = 4,
            nombreNegocio = "Pulpería Los Pinos",
            dueno = "Marlon Reyes",
            iniciales = "MR",
            direccion = "Aldea Los Pinos",
            telefono = "9654-5504",
            rtnODni = "0824199001122",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 60000,
            estadoVisita = "venta",
            detalleVisita = "Vendida · L 1,250.00 (9:40)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "8:2,12:1"
        ),
        ClienteEntity(
            id = 5,
            parada = 5,
            nombreNegocio = "Abarrotes Mi Esperanza",
            dueno = "Rosa Mejía",
            iniciales = "RM",
            direccion = "Bo. Abajo, Cerro Grande",
            telefono = "9512-6605",
            rtnODni = "0801198803344",
            estadoCredito = "warn",
            textoCredito = "Vence en 2 días",
            saldoCentavos = 412000,
            estadoVisita = "pend",
            detalleVisita = "Siguiente parada · 2.4 km (7 min)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "1:2,7:2,5:1,2:1"
        ),
        ClienteEntity(
            id = 6,
            parada = 6,
            nombreNegocio = "Pulpería El Carmen",
            dueno = "Santos Aguilar",
            iniciales = "SA",
            direccion = "Aldea El Carmen",
            telefono = "9433-7706",
            rtnODni = "0824197500987",
            estadoCredito = "bad",
            textoCredito = "Mora 12 días",
            saldoCentavos = 634000,
            estadoVisita = "pend",
            detalleVisita = "Por visitar · 6.1 km (18 min)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "8:2,10:1"
        ),
        ClienteEntity(
            id = 7,
            parada = 7,
            nombreNegocio = "Comercial Hermanos Avila",
            dueno = "Oscar Avila",
            iniciales = "OA",
            direccion = "Bo. El Calvario, Cerro Grande",
            telefono = "9877-8807",
            rtnODni = "08011980022119",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 0,
            estadoVisita = "pend",
            detalleVisita = "Entregar pedido P-2291 · 7.8 km",
            pideCredito = false,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "7:4,8:3,12:1"
        ),
        ClienteEntity(
            id = 8,
            parada = 8,
            nombreNegocio = "Pulpería La Esquinita",
            dueno = "Ana Lagos",
            iniciales = "AL",
            direccion = "Col. Villa Nueva, Guaimaca",
            telefono = "9321-9908",
            rtnODni = "0809199200654",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 82000,
            estadoVisita = "pend",
            detalleVisita = "Por visitar · 21 km (34 min)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "8:3,12:1"
        ),
        ClienteEntity(
            id = 9,
            parada = 9,
            nombreNegocio = "Tienda San José",
            dueno = "José Ramírez",
            iniciales = "JR",
            direccion = "Bo. San José, Guaimaca",
            telefono = "9788-1009",
            rtnODni = "0809197800321",
            estadoCredito = "bad",
            textoCredito = "Mora 34 días",
            saldoCentavos = 396000,
            estadoVisita = "pend",
            detalleVisita = "Por visitar · 22 km (37 min)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "1:1,2:1"
        ),
        ClienteEntity(
            id = 10,
            parada = 10,
            nombreNegocio = "Abarrotería Doña Lupe",
            dueno = "Guadalupe Sierra",
            iniciales = "GS",
            direccion = "Bo. El Centro, Guaimaca",
            telefono = "9944-2010",
            rtnODni = "0809198400777",
            estadoCredito = "warn",
            textoCredito = "Vence mañana",
            saldoCentavos = 210000,
            estadoVisita = "pend",
            detalleVisita = "Por visitar · 22 km (39 min)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "1:2,2:2,3:1"
        ),
        ClienteEntity(
            id = 11,
            parada = 11,
            nombreNegocio = "Pulpería El Milagro",
            dueno = "Wilmer Ortez",
            iniciales = "WO",
            direccion = "Aldea Zacatal",
            telefono = "9611-3011",
            rtnODni = "0809199500118",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 0,
            estadoVisita = "pend",
            detalleVisita = "Por visitar · 27 km (48 min)",
            pideCredito = false,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "7:3,8:2"
        ),
        ClienteEntity(
            id = 12,
            parada = 12,
            nombreNegocio = "Bodega Guaimaca Centro",
            dueno = "Héctor Pineda",
            iniciales = "HP",
            direccion = "Mercado municipal, Guaimaca",
            telefono = "9800-4012",
            rtnODni = "08091976015432",
            estadoCredito = "ok",
            textoCredito = "Al día",
            saldoCentavos = 0,
            estadoVisita = "pend",
            detalleVisita = "Por visitar · 28 km (52 min)",
            pideCredito = true,
            tieneFotoFachada = true,
            tieneUbicacionGps = true,
            sugeridos = "1:5,5:3,13:2,14:2"
        )
    )
}

fun crearProductosPorDefecto(): List<ProductoEntity> {
    return listOf(
        ProductoEntity(1, "granos", "Arroz precocido 5 lb", "Fardo de 10", 42000, 0, 14, 220, "Bolsa"),
        ProductoEntity(2, "granos", "Frijol rojo 2 lb", "Fardo de 12", 39600, 0, 11, 160, "Bolsa"),
        ProductoEntity(3, "granos", "Azúcar blanca 5 lb", "Fardo de 10", 45500, 0, 7, 95, "Bolsa"),
        ProductoEntity(4, "granos", "Harina de trigo 1 lb", "Fardo de 25", 30000, 0, 0, 0, "Bolsa"),
        ProductoEntity(5, "aceites", "Aceite vegetal 1 L", "Caja de 12", 54000, 15, 5, 80, "Botella"),
        ProductoEntity(6, "aceites", "Manteca vegetal 1 lb", "Caja de 24", 64800, 15, 4, 40, "Tarro"),
        ProductoEntity(7, "bebidas", "Refresco de cola 3 L", "Fardo de 6", 22800, 15, 22, 300, "Botella"),
        ProductoEntity(8, "bebidas", "Agua purificada 600 ml", "Fardo de 24", 15600, 15, 19, 410, "Fardo"),
        ProductoEntity(9, "bebidas", "Jugo de naranja 1 L", "Caja de 12", 34800, 15, 6, 75, "Caja"),
        ProductoEntity(10, "limpieza", "Jabón de lavar en barra", "Caja de 50", 57500, 15, 15, 120, "Barra"),
        ProductoEntity(11, "limpieza", "Detergente en polvo 500 g", "Fardo de 24", 61200, 15, 9, 66, "Sobre"),
        ProductoEntity(12, "limpieza", "Cloro 1 L", "Caja de 12", 26400, 15, 8, 90, "Galón"),
        ProductoEntity(13, "lacteos", "Leche en polvo 360 g", "Caja de 24", 156000, 0, 4, 30, "Lata"),
        ProductoEntity(14, "lacteos", "Café molido 400 g", "Caja de 20", 118000, 15, 7, 55, "Sobre")
    )
}

fun validarPaso1NuevoCliente(nombreNegocio: String, dueno: String, telefono: String): Boolean {
    val digitosTelefono = telefono.filter { it.isDigit() }
    return nombreNegocio.trim().length > 2 &&
            dueno.trim().length > 2 &&
            digitosTelefono.length >= 8
}

fun validarPaso2NuevoCliente(
    tieneFoto: Boolean,
    tieneGps: Boolean,
    direccion: String
): Boolean {
    return tieneFoto && tieneGps && direccion.trim().length > 2
}

fun convertirLempirasACentavos(textoPrecio: String): Int {
    val limpio = textoPrecio.trim().replace(",", ".")
    val valor = limpio.toDoubleOrNull() ?: 0.0
    return (valor * 100).toInt().coerceAtLeast(0)
}

fun validarNuevoProducto(
    nombre: String,
    presentacion: String,
    precioLempiras: String
): Boolean {
    val centavos = convertirLempirasACentavos(precioLempiras)
    return nombre.trim().length > 2 &&
            presentacion.trim().length > 1 &&
            centavos > 0
}

@Composable
fun ClientesScreen(
    clienteCLFK: ClienteCLFK,
    onVolverInicio: () -> Unit
) {
    var listaClientes by remember { mutableStateOf(emptyList<ClienteEntity>()) }

    // Subvistas del módulo independiente de Clientes:
    // "lista" -> Cartera de clientes
    // "detalle" -> Ficha de un cliente específico
    // "nuevo" -> Alta rápida de nuevo cliente en 2 pasos
    var subvista by remember { mutableStateOf("lista") }

    var clienteSeleccionado by remember { mutableStateOf<ClienteEntity?>(null) }

    // Estados de búsqueda y filtrado de clientes
    var busquedaCliente by remember { mutableStateOf("") }
    var filtroCartera by remember { mutableStateOf("todos") } // "todos", "ok", "warn", "bad"

    // Estados para Nuevo Cliente (2 pasos)
    var pasoNuevoCliente by remember { mutableStateOf(0) }
    var nuevoNombreNegocio by remember { mutableStateOf("") }
    var nuevoDueno by remember { mutableStateOf("") }
    var nuevoTelefono by remember { mutableStateOf("") }
    var nuevoRtnODni by remember { mutableStateOf("") }
    var nuevoPideCredito by remember { mutableStateOf(false) }
    var nuevoFotoTomada by remember { mutableStateOf(false) }
    var nuevoFotoUri by remember { mutableStateOf<String?>(null) }
    var nuevoDireccion by remember { mutableStateOf("") }
    var nuevoGpsGuardado by remember { mutableStateOf(false) }

    var mensajeAviso by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Carga inicial y semilla de Clientes en Room
    LaunchedEffect(clienteCLFK) {
        try {
            var clientesDb = clienteCLFK.obtenerTodosOrdenadosPorNombre()
            if (clientesDb.isEmpty()) {
                clienteCLFK.insertarTodos(crearClientesPorDefecto())
                clientesDb = clienteCLFK.obtenerTodosOrdenadosPorNombre()
            }
            listaClientes = clientesDb

            if (clienteSeleccionado == null) {
                clienteSeleccionado = clientesDb.find { it.parada == 5 } ?: clientesDb.firstOrNull()
            }
        } catch (e: Exception) {
            listaClientes = crearClientesPorDefecto().sortedBy { it.nombreNegocio }
            if (clienteSeleccionado == null) {
                clienteSeleccionado = listaClientes.find { it.parada == 5 } ?: listaClientes.firstOrNull()
            }
        }
    }

    val clienteActivo = clienteSeleccionado ?: listaClientes.firstOrNull()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = FoeSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // BARRA SUPERIOR DEL MÓDULO DE CLIENTES
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandNavy)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            when (subvista) {
                                "detalle" -> subvista = "lista"
                                "nuevo" -> subvista = "lista"
                                else -> onVolverInicio()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FoePrimaryContainer,
                            contentColor = FoeOnPrimaryContainer
                        )
                    ) {
                        Text("Volver")
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    ) {
                        val tituloBarra = when (subvista) {
                            "detalle" -> clienteActivo?.nombreNegocio ?: "Detalle del cliente"
                            "nuevo" -> "Nuevo cliente"
                            else -> "Clientes"
                        }
                        val subtituloBarra = when (subvista) {
                            "detalle" -> "Parada ${clienteActivo?.parada ?: 1} de ${listaClientes.size} · ${clienteActivo?.dueno ?: ""}"
                            "nuevo" -> "Registro guiado del cliente"
                            else -> "${listaClientes.size} en la ruta · cartera"
                        }

                        Text(
                            text = tituloBarra,
                            color = OnBrandNavy,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = subtituloBarra,
                            color = OnBrandSubtitle,
                            fontSize = 12.sp
                        )
                    }

                    if (subvista == "lista") {
                        Button(
                            onClick = {
                                pasoNuevoCliente = 0
                                nuevoNombreNegocio = ""
                                nuevoDueno = ""
                                nuevoTelefono = ""
                                nuevoRtnODni = ""
                                nuevoPideCredito = false
                                nuevoFotoTomada = false
                                nuevoFotoUri = null
                                nuevoDireccion = ""
                                nuevoGpsGuardado = false
                                mensajeAviso = ""
                                subvista = "nuevo"
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ModuloVerde,
                                contentColor = Color.White
                            )
                        ) {
                            Text("+ Nuevo")
                        }
                    }
                }
            }

            // Aviso informativo si existe
            if (mensajeAviso.isNotBlank()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = FoePrimaryContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = mensajeAviso,
                            color = FoeOnPrimaryContainer,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedButton(
                            onClick = { mensajeAviso = "" }
                        ) {
                            Text("Cerrar")
                        }
                    }
                }
            }

            // CONTENIDO SEGÚN SUBVISTA DEL MÓDULO DE CLIENTES
            when (subvista) {
                "lista" -> {
                    VistaCarteraClientes(
                        clientes = listaClientes,
                        busqueda = busquedaCliente,
                        onBusquedaChange = { busquedaCliente = it },
                        filtroCartera = filtroCartera,
                        onFiltroCarteraChange = { filtroCartera = it },
                        onAbrirDetalleCliente = { cli ->
                            clienteSeleccionado = cli
                            subvista = "detalle"
                        },
                        onNuevoCliente = {
                            pasoNuevoCliente = 0
                            nuevoNombreNegocio = ""
                            nuevoDueno = ""
                            nuevoTelefono = ""
                            nuevoRtnODni = ""
                            nuevoPideCredito = false
                            nuevoFotoTomada = false
                            nuevoFotoUri = null
                            nuevoDireccion = ""
                            nuevoGpsGuardado = false
                            subvista = "nuevo"
                        }
                    )
                }

                "detalle" -> {
                    if (clienteActivo != null) {
                        VistaDetalleCliente(
                            cliente = clienteActivo,
                            onVolverLista = { subvista = "lista" }
                        )
                    }
                }

                "nuevo" -> {
                    VistaNuevoCliente(
                        paso = pasoNuevoCliente,
                        nombreNegocio = nuevoNombreNegocio,
                        onNombreNegocioChange = { nuevoNombreNegocio = it },
                        dueno = nuevoDueno,
                        onDuenoChange = { nuevoDueno = it },
                        telefono = nuevoTelefono,
                        onTelefonoChange = { nuevoTelefono = it },
                        rtnODni = nuevoRtnODni,
                        onRtnODniChange = { nuevoRtnODni = it.filter { c -> c.isDigit() }.take(14) },
                        pideCredito = nuevoPideCredito,
                        onPideCreditoChange = { nuevoPideCredito = it },
                        fotoTomada = nuevoFotoTomada,
                        fotoUri = nuevoFotoUri,
                        onFotoCapturada = { uri ->
                            nuevoFotoUri = uri
                            nuevoFotoTomada = true
                        },
                        direccion = nuevoDireccion,
                        onDireccionChange = { nuevoDireccion = it },
                        gpsGuardado = nuevoGpsGuardado,
                        onGuardarGps = { nuevoGpsGuardado = true },
                        onPasoAnterior = { pasoNuevoCliente = 0 },
                        onPasoSiguiente = { pasoNuevoCliente = 1 },
                        onGuardarCliente = {
                            val siguienteParada = (listaClientes.maxOfOrNull { it.parada } ?: 12) + 1
                            val nuevo = ClienteEntity(
                                parada = siguienteParada,
                                nombreNegocio = nuevoNombreNegocio.trim(),
                                dueno = nuevoDueno.trim(),
                                iniciales = obtenerIniciales(nuevoDueno),
                                direccion = nuevoDireccion.trim(),
                                telefono = nuevoTelefono.trim(),
                                rtnODni = nuevoRtnODni.trim(),
                                estadoCredito = "ok",
                                textoCredito = "Al día",
                                saldoCentavos = 0,
                                estadoVisita = "pend",
                                detalleVisita = "Parada $siguienteParada · recién registrado",
                                pideCredito = nuevoPideCredito,
                                tieneFotoFachada = nuevoFotoTomada,
                                tieneUbicacionGps = nuevoGpsGuardado,
                                sugeridos = "1:1,8:1",
                                fotoUri = nuevoFotoUri
                            )
                            scope.launch {
                                try {
                                    clienteCLFK.insertar(nuevo)
                                    listaClientes = clienteCLFK.obtenerTodosOrdenadosPorNombre()
                                } catch (e: Exception) {
                                    listaClientes = (listaClientes + nuevo).sortedBy { it.nombreNegocio }
                                }
                                clienteSeleccionado = listaClientes.find {
                                    it.nombreNegocio == nuevo.nombreNegocio && it.parada == nuevo.parada
                                } ?: nuevo
                                mensajeAviso =
                                    "Cliente registrado: ${nuevo.nombreNegocio} (Total en ruta: ${listaClientes.size})."
                                subvista = "lista"
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun VistaCarteraClientes(
    clientes: List<ClienteEntity>,
    busqueda: String,
    onBusquedaChange: (String) -> Unit,
    filtroCartera: String,
    onFiltroCarteraChange: (String) -> Unit,
    onAbrirDetalleCliente: (ClienteEntity) -> Unit,
    onNuevoCliente: () -> Unit
) {
    val consulta = busqueda.trim().lowercase()
    val clientesFiltrados = clientes.filter { c ->
        val coincideFiltro = when (filtroCartera) {
            "ok" -> c.estadoCredito == "ok"
            "warn" -> c.estadoCredito == "warn"
            "bad" -> c.estadoCredito == "bad"
            else -> true
        }
        val coincideBusqueda = consulta.isEmpty() ||
                c.nombreNegocio.lowercase().contains(consulta) ||
                c.dueno.lowercase().contains(consulta) ||
                c.direccion.lowercase().contains(consulta) ||
                c.parada.toString().contains(consulta)
        coincideFiltro && coincideBusqueda
    }

    val totalAlDia = clientes.count { it.estadoCredito == "ok" }
    val totalPorVencer = clientes.count { it.estadoCredito == "warn" }
    val totalEnMora = clientes.count { it.estadoCredito == "bad" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = busqueda,
            onValueChange = onBusquedaChange,
            label = { Text("Buscar por pulpería, dueño, barrio o parada") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = filtroCartera == "todos",
                onClick = { onFiltroCarteraChange("todos") },
                label = { Text("Todos (${clientes.size})") }
            )
            FilterChip(
                selected = filtroCartera == "ok",
                onClick = { onFiltroCarteraChange("ok") },
                label = { Text("Al día ($totalAlDia)") }
            )
            FilterChip(
                selected = filtroCartera == "warn",
                onClick = { onFiltroCarteraChange("warn") },
                label = { Text("Por vencer ($totalPorVencer)") }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = filtroCartera == "bad",
                onClick = { onFiltroCarteraChange("bad") },
                label = { Text("En mora ($totalEnMora)") }
            )

            Button(
                onClick = onNuevoCliente,
                colors = ButtonDefaults.buttonColors(
                    containerColor = FoePrimaryContainer,
                    contentColor = FoeOnPrimaryContainer
                )
            ) {
                Text("+ Nuevo cliente")
            }
        }

        if (clientesFiltrados.isEmpty()) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = FoeCardBackground),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Cliente no encontrado en la ruta",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "Revisa el nombre o busca por el barrio.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoeOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            clientesFiltrados.forEach { cliente ->
                TarjetaClienteItem(
                    cliente = cliente,
                    onAbrirDetalle = { onAbrirDetalleCliente(cliente) }
                )
            }
        }
    }
}

@Composable
private fun TarjetaClienteItem(
    cliente: ClienteEntity,
    onAbrirDetalle: () -> Unit
) {
    val colorEstado = when (cliente.estadoCredito) {
        "bad" -> ModuloRojo
        "warn" -> ModuloAmbar
        else -> ModuloVerde
    }

    Card(
        onClick = onAbrirDetalle,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = FoeCardBackground
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = FoePrimaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .width(44.dp)
                            .height(44.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cliente.iniciales,
                            color = FoeOnPrimaryContainer,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = cliente.nombreNegocio,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "${cliente.dueno} · Parada ${cliente.parada}",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                    Text(
                        text = cliente.direccion,
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorEstado
                        )
                    ) {
                        Text(
                            text = cliente.textoCredito,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = if (cliente.saldoCentavos > 0) {
                            "Saldo ${formatearLempiras(cliente.saldoCentavos)}"
                        } else {
                            "Sin saldo"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = FoeOnSurface
                    )
                }
            }

            HorizontalDivider(color = FoeOutlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cliente.detalleVisita,
                    style = MaterialTheme.typography.bodySmall,
                    color = FoeOnSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = onAbrirDetalle
                ) {
                    Text("Ver ficha", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun VistaDetalleCliente(
    cliente: ClienteEntity,
    onVolverLista: () -> Unit
) {
    val context = LocalContext.current
    val fotoBitmap = remember(cliente.fotoUri) {
        cliente.fotoUri
            ?.takeIf { it.isNotBlank() }
            ?.let { uriTexto ->
                try {
                    context.contentResolver.openInputStream(
                        Uri.parse(uriTexto)
                    )?.use { input ->
                        BitmapFactory.decodeStream(input)
                    }
                } catch (e: Exception) {
                    null
                }
            }
    }

    val limiteCreditoCentavos = 1000000
    val disponibleCentavos = (limiteCreditoCentavos - cliente.saldoCentavos).coerceAtLeast(0)
    val porcentajeUsado = ((cliente.saldoCentavos.toDouble() / limiteCreditoCentavos) * 100)
        .toInt()
        .coerceIn(0, 100)

    val colorEstado = when (cliente.estadoCredito) {
        "bad" -> ModuloRojo
        "warn" -> ModuloAmbar
        else -> ModuloVerde
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = FoeCardBackground),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (fotoBitmap != null) {
                    Image(
                        bitmap = fotoBitmap.asImageBitmap(),
                        contentDescription = "Fotografía de la fachada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = FoePrimary)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(56.dp)
                                .height(56.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${cliente.parada}",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = cliente.nombreNegocio,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = FoeOnSurface
                        )
                        Text(
                            text = "${cliente.dueno} · Tel. ${cliente.telefono}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = FoeOnSurfaceVariant
                        )
                        Text(
                            text = cliente.direccion,
                            style = MaterialTheme.typography.bodySmall,
                            color = FoeOnSurfaceVariant
                        )
                        if (cliente.rtnODni.isNotBlank()) {
                            Text(
                                text = "RTN / DNI: ${cliente.rtnODni}",
                                style = MaterialTheme.typography.bodySmall,
                                color = FoeOnSurfaceVariant
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = colorEstado)
                    ) {
                        Text(
                            text = cliente.textoCredito,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = FoeSurfaceContainerLow)
                    ) {
                        Text(
                            text = cliente.detalleVisita,
                            color = FoeOnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = FoeCardBackground),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CRÉDITO Y CARTERA",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurfaceVariant
                    )
                    Text(
                        text = "$porcentajeUsado % ocupado",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = colorEstado
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Saldo actual",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoeOnSurfaceVariant
                    )
                    Text(
                        text = formatearLempiras(cliente.saldoCentavos),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoeOnSurface
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Disponible ${formatearLempiras(disponibleCentavos)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                    Text(
                        text = "Límite ${formatearLempiras(limiteCreditoCentavos)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                }

                if (cliente.estadoCredito == "bad") {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = FoeErrorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Crédito bloqueado por mora (${cliente.textoCredito}). Solo ventas de contado o cobrar saldo pendiente primero.",
                            color = FoeOnErrorContainer,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onVolverLista,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a la lista de clientes")
        }
    }
}

@Composable
private fun VistaNuevoCliente(
    paso: Int,
    nombreNegocio: String,
    onNombreNegocioChange: (String) -> Unit,
    dueno: String,
    onDuenoChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit,
    rtnODni: String,
    onRtnODniChange: (String) -> Unit,
    pideCredito: Boolean,
    onPideCreditoChange: (Boolean) -> Unit,
    fotoTomada: Boolean,
    fotoUri: String?,
    onFotoCapturada: (String) -> Unit,
    direccion: String,
    onDireccionChange: (String) -> Unit,
    gpsGuardado: Boolean,
    onGuardarGps: () -> Unit,
    onPasoAnterior: () -> Unit,
    onPasoSiguiente: () -> Unit,
    onGuardarCliente: () -> Unit
) {
    val paso1Valido = validarPaso1NuevoCliente(nombreNegocio, dueno, telefono)
    val paso2Valido = validarPaso2NuevoCliente(fotoTomada, gpsGuardado, direccion)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (paso == 0) "Paso 1 de 2 · Datos del cliente" else "Paso 2 de 2 · Foto y ubicación",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = FoeOnSurfaceVariant
            )
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (paso == 0) ModuloVerde else ModuloCoral
                )
            ) {
                Text(
                    text = "${paso + 1}/2",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        if (paso == 0) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = FoeCardBackground),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Datos del cliente",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "Negocio, contacto y condiciones",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )

                    OutlinedTextField(
                        value = nombreNegocio,
                        onValueChange = onNombreNegocioChange,
                        label = { Text("Nombre del negocio (Ej.: Pulpería La Esperanza)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = dueno,
                        onValueChange = onDuenoChange,
                        label = { Text("Dueño o encargado (Nombre y apellido)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = onTelefonoChange,
                        label = { Text("Teléfono (mínimo 8 dígitos, ej. 9999-9999)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = rtnODni,
                        onValueChange = onRtnODniChange,
                        label = { Text("RTN o DNI (opcional, solo números)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(color = FoeOutlineVariant)

                    Text(
                        text = "Condiciones de venta",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "Empieza como cliente normal. Pasa a mayorista solo cuando una compra llega a L 5,000.00.",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Pide crédito",
                                fontWeight = FontWeight.SemiBold,
                                color = FoeOnSurface
                            )
                            Text(
                                text = "El límite se define al aprobar",
                                style = MaterialTheme.typography.bodySmall,
                                color = FoeOnSurfaceVariant
                            )
                        }
                        FilterChip(
                            selected = pideCredito,
                            onClick = { onPideCreditoChange(!pideCredito) },
                            label = { Text(if (pideCredito) "Sí pide crédito" else "Solo contado") }
                        )
                    }
                }
            }

            Button(
                onClick = onPasoSiguiente,
                enabled = paso1Valido,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FoePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = FoeCardBackground),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Foto y ubicación",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "Para encontrar el local en la próxima visita",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (fotoTomada) FoePrimaryContainer else FoeSurfaceContainerLow
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (fotoTomada) {
                                    "Foto de la fachada capturada correctamente"
                                } else {
                                    "Toma una foto del local"
                                },
                                fontWeight = FontWeight.Bold,
                                color = if (fotoTomada) FoeOnPrimaryContainer else FoeOnSurface
                            )
                            Text(
                                text = "La fachada, con el rótulo si tiene. Ayuda a encontrarlo en la próxima visita.",
                                style = MaterialTheme.typography.bodySmall,
                                color = FoeOnSurfaceVariant,
                                textAlign = TextAlign.Center
                            )

                            CamaraSection(
                                fotoUri = fotoUri,
                                onFotoCapturada = onFotoCapturada
                            )
                        }
                    }

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = onDireccionChange,
                        label = { Text("Barrio, colonia o aldea (Ej.: Bo. El Centro, Talanga)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (gpsGuardado) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = FoeSurfaceContainerLow),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Ubicación guardada · 4 m de precisión",
                                    color = ModuloVerde,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                OutlinedButton(onClick = onGuardarGps) {
                                    Text("Actualizar", fontSize = 12.sp)
                                }
                            }
                        }
                    } else {
                        Button(
                            onClick = onGuardarGps,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FoePrimaryContainer,
                                contentColor = FoeOnPrimaryContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Usar mi ubicación actual")
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onPasoAnterior,
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text("Paso anterior")
                }

                Button(
                    onClick = onGuardarCliente,
                    enabled = paso1Valido && paso2Valido,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ModuloVerde),
                    modifier = Modifier
                        .weight(2f)
                        .height(54.dp)
                ) {
                    Text(
                        text = "Guardar cliente",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClientesScreenPreview() {
    val fakeClienteCLFK = object : ClienteCLFK {
        override suspend fun insertar(cliente: ClienteEntity) {}
        override suspend fun insertarTodos(clientes: List<ClienteEntity>) {}
        override suspend fun actualizar(cliente: ClienteEntity) {}
        override suspend fun obtenerTodosOrdenadosPorNombre(): List<ClienteEntity> =
            crearClientesPorDefecto().sortedBy { it.nombreNegocio }
        override suspend fun obtenerTodosPorParada(): List<ClienteEntity> =
            crearClientesPorDefecto()
        override suspend fun obtenerPorId(id: Int): ClienteEntity? =
            crearClientesPorDefecto().firstOrNull()
    }
    ProyectoFOETheme {
        ClientesScreen(
            clienteCLFK = fakeClienteCLFK,
            onVolverInicio = {}
        )
    }
}
