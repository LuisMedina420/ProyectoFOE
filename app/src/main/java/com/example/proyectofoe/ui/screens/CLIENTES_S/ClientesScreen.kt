package com.example.proyectofoe.ui.screens.CLIENTES_S

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofoe.local.ClienteCLFK
import com.example.proyectofoe.local.ClienteEntity
import com.example.proyectofoe.local.ProductoCLFK
import com.example.proyectofoe.local.ProductoEntity
import com.example.proyectofoe.ui.theme.BrandNavy
import com.example.proyectofoe.ui.theme.FoeCardBackground
import com.example.proyectofoe.ui.theme.FoeError
import com.example.proyectofoe.ui.theme.FoeErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnErrorContainer
import com.example.proyectofoe.ui.theme.FoeOnPrimaryContainer
import com.example.proyectofoe.ui.theme.FoeOnSurface
import com.example.proyectofoe.ui.theme.FoeOnSurfaceVariant
import com.example.proyectofoe.ui.theme.FoeOutlineVariant
import com.example.proyectofoe.ui.theme.FoePrimary
import com.example.proyectofoe.ui.theme.FoePrimaryContainer
import com.example.proyectofoe.ui.theme.FoeSurface
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerHigh
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerLow
import com.example.proyectofoe.ui.theme.ModuloAcero
import com.example.proyectofoe.ui.theme.ModuloAmbar
import com.example.proyectofoe.ui.theme.ModuloCoral
import com.example.proyectofoe.ui.theme.ModuloRojo
import com.example.proyectofoe.ui.theme.ModuloVerde
import com.example.proyectofoe.ui.theme.ModuloVioleta
import com.example.proyectofoe.ui.theme.OnBrandNavy
import com.example.proyectofoe.ui.theme.OnBrandSubtitle
import com.example.proyectofoe.ui.theme.ProyectoFOETheme
import kotlinx.coroutines.launch
import java.util.Locale

const val LIMITE_CREDITO_CENTAVOS = 1000000 // L 10,000.00
const val DESCUENTO_PORCENTAJE = 5
const val MINIMO_MAYORISTA_CENTAVOS = 500000 // L 5,000.00

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

fun parsearSugeridos(cadena: String): Map<Int, Int> {
    if (cadena.isBlank()) return emptyMap()
    val mapa = mutableMapOf<Int, Int>()
    val pares = cadena.split(",")
    for (par in pares) {
        val partes = par.trim().split(":")
        if (partes.size == 2) {
            val idProd = partes[0].toIntOrNull()
            val cant = partes[1].toIntOrNull()
            if (idProd != null && cant != null && cant > 0) {
                mapa[idProd] = cant
            }
        }
    }
    return mapa
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

@Composable
fun ClientesScreen(
    clienteCLFK: ClienteCLFK,
    productoCLFK: ProductoCLFK,
    iniciarEnProductos: Boolean = false,
    onVolverInicio: () -> Unit
) {
    var listaClientes by remember { mutableStateOf(emptyList<ClienteEntity>()) }
    var listaProductos by remember { mutableStateOf(emptyList<ProductoEntity>()) }

    // Subvistas del módulo de Clientes:
    // "lista" -> Cartera de clientes
    // "detalle" -> Ficha de un cliente específico
    // "productos" -> Visualización de productos integrada en Clientes
    // "nuevo" -> Alta rápida de nuevo cliente en 2 pasos
    var subvista by remember {
        mutableStateOf(if (iniciarEnProductos) "productos" else "lista")
    }

    var clienteSeleccionado by remember { mutableStateOf<ClienteEntity?>(null) }

    // Estados de búsqueda y filtrado de clientes
    var busquedaCliente by remember { mutableStateOf("") }
    var filtroCartera by remember { mutableStateOf("todos") } // "todos", "ok", "warn", "bad"

    // Estados de visualización de productos dentro de Clientes
    var busquedaProducto by remember { mutableStateOf("") }
    var categoriaProducto by remember { mutableStateOf("todo") } // "todo", "sug", "granos", "aceites", "bebidas", "limpieza", "lacteos"
    var cantidadesSeleccionadas by remember { mutableStateOf(mapOf<Int, Int>()) }
    var aplicarDescuento by remember { mutableStateOf(false) }
    var mostrarSelectorClienteEnProductos by remember { mutableStateOf(false) }

    // Estados para Nuevo Cliente (2 pasos como en el demo FOE 360)
    var pasoNuevoCliente by remember { mutableStateOf(0) }
    var nuevoNombreNegocio by remember { mutableStateOf("") }
    var nuevoDueno by remember { mutableStateOf("") }
    var nuevoTelefono by remember { mutableStateOf("") }
    var nuevoRtnODni by remember { mutableStateOf("") }
    var nuevoPideCredito by remember { mutableStateOf(false) }
    var nuevoFotoTomada by remember { mutableStateOf(false) }
    var nuevoDireccion by remember { mutableStateOf("") }
    var nuevoGpsGuardado by remember { mutableStateOf(false) }
    var mensajeAviso by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Carga inicial y semilla de Clientes y Productos en Room
    LaunchedEffect(clienteCLFK, productoCLFK) {
        try {
            var clientesDb = clienteCLFK.obtenerTodosOrdenadosPorNombre()
            if (clientesDb.isEmpty()) {
                clienteCLFK.insertarTodos(crearClientesPorDefecto())
                clientesDb = clienteCLFK.obtenerTodosOrdenadosPorNombre()
            }
            listaClientes = clientesDb

            var productosDb = productoCLFK.obtenerTodos()
            if (productosDb.isEmpty()) {
                productoCLFK.insertarTodos(crearProductosPorDefecto())
                productosDb = productoCLFK.obtenerTodos()
            }
            listaProductos = productosDb

            if (clienteSeleccionado == null) {
                clienteSeleccionado = clientesDb.find { it.parada == 5 } ?: clientesDb.firstOrNull()
            }
        } catch (e: Exception) {
            listaClientes = crearClientesPorDefecto().sortedBy { it.nombreNegocio }
            listaProductos = crearProductosPorDefecto()
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
        ) {
            // BARRA SUPERIOR AZUL MARINO (bar del demo FOE 360)
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
                                "productos" -> {
                                    if (iniciarEnProductos) {
                                        onVolverInicio()
                                    } else {
                                        subvista = "lista"
                                    }
                                }
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
                            "productos" -> "Productos · Clientes"
                            "nuevo" -> "Nuevo cliente"
                            else -> "Clientes"
                        }
                        val subtituloBarra = when (subvista) {
                            "detalle" -> "Parada ${clienteActivo?.parada ?: 1} de ${listaClientes.size} · ${clienteActivo?.dueno ?: ""}"
                            "productos" -> "Catálogo integrado en el apartado de Clientes"
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

                // Selector integrado dentro del módulo de Clientes: Cartera de Clientes <-> Visualización de Productos
                if (subvista != "nuevo") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                subvista = "lista"
                                mostrarSelectorClienteEnProductos = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (subvista == "lista" || subvista == "detalle") FoePrimary else FoeSurfaceContainerLow,
                                contentColor = if (subvista == "lista" || subvista == "detalle") Color.White else FoeOnSurface
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Cartera de clientes (${listaClientes.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Button(
                            onClick = {
                                subvista = "productos"
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (subvista == "productos") ModuloAcero else FoeSurfaceContainerLow,
                                contentColor = if (subvista == "productos") Color.White else FoeOnSurface
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Productos (${listaProductos.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
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

            // CONTENIDO SEGÚN SUBVISTA
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
                        onVerProductosDeCliente = { cli ->
                            clienteSeleccionado = cli
                            categoriaProducto = "todo"
                            mostrarSelectorClienteEnProductos = false
                            subvista = "productos"
                        },
                        onNuevoCliente = {
                            pasoNuevoCliente = 0
                            nuevoNombreNegocio = ""
                            nuevoDueno = ""
                            nuevoTelefono = ""
                            nuevoRtnODni = ""
                            nuevoPideCredito = false
                            nuevoFotoTomada = false
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
                            productos = listaProductos,
                            onVolverLista = { subvista = "lista" },
                            onVerCatalogoProductos = { soloSugeridos ->
                                categoriaProducto = if (soloSugeridos) "sug" else "todo"
                                mostrarSelectorClienteEnProductos = false
                                subvista = "productos"
                            },
                            onCargarCanastaSugerida = { mapaSugeridos ->
                                cantidadesSeleccionadas = mapaSugeridos
                                categoriaProducto = "sug"
                                mostrarSelectorClienteEnProductos = false
                                mensajeAviso = "Canasta sugerida cargada para ${clienteActivo.nombreNegocio}."
                                subvista = "productos"
                            }
                        )
                    }
                }

                "productos" -> {
                    VistaProductosEnClientes(
                        clientes = listaClientes,
                        clienteActivo = clienteActivo,
                        mostrarSelectorCliente = mostrarSelectorClienteEnProductos,
                        onToggleSelectorCliente = {
                            mostrarSelectorClienteEnProductos = !mostrarSelectorClienteEnProductos
                        },
                        onSeleccionarCliente = { nuevoCli ->
                            clienteSeleccionado = nuevoCli
                            mostrarSelectorClienteEnProductos = false
                            if (categoriaProducto == "sug" && parsearSugeridos(nuevoCli.sugeridos).isEmpty()) {
                                categoriaProducto = "todo"
                            }
                        },
                        onVerFichaClienteActivo = {
                            if (clienteActivo != null) {
                                subvista = "detalle"
                            }
                        },
                        productos = listaProductos,
                        busqueda = busquedaProducto,
                        onBusquedaChange = { busquedaProducto = it },
                        categoriaSeleccionada = categoriaProducto,
                        onCategoriaChange = { categoriaProducto = it },
                        cantidades = cantidadesSeleccionadas,
                        onCambiarCantidad = { idProd, nuevaCant ->
                            val mapaMutable = cantidadesSeleccionadas.toMutableMap()
                            if (nuevaCant <= 0) {
                                mapaMutable.remove(idProd)
                            } else {
                                mapaMutable[idProd] = nuevaCant
                            }
                            cantidadesSeleccionadas = mapaMutable
                        },
                        onLimpiarSeleccion = {
                            cantidadesSeleccionadas = emptyMap()
                            aplicarDescuento = false
                        },
                        aplicarDescuento = aplicarDescuento,
                        onToggleDescuento = { aplicarDescuento = !aplicarDescuento }
                    )
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
                        onTomarFoto = { nuevoFotoTomada = true },
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
                                sugeridos = "1:1,8:1"
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
                                    "Cliente registrado: ${nuevo.nombreNegocio} (Parada $siguienteParada · cliente normal${if (nuevo.pideCredito) " · pide crédito" else ""})."
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
    onVerProductosDeCliente: (ClienteEntity) -> Unit,
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
        // Buscador de clientes (search del demo FOE 360)
        OutlinedTextField(
            value = busqueda,
            onValueChange = onBusquedaChange,
            label = { Text("Buscar por pulpería, dueño, barrio o parada") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Chips de filtro de cartera
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
                    onAbrirDetalle = { onAbrirDetalleCliente(cliente) },
                    onVerProductos = { onVerProductosDeCliente(cliente) }
                )
            }
        }
    }
}

@Composable
private fun TarjetaClienteItem(
    cliente: ClienteEntity,
    onAbrirDetalle: () -> Unit,
    onVerProductos: () -> Unit
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
                // Insignia con iniciales del dueño (igual a V.clientes del demo)
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onAbrirDetalle
                    ) {
                        Text("Ficha", fontSize = 12.sp)
                    }
                    Button(
                        onClick = onVerProductos,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ModuloAcero,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Ver productos", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun VistaDetalleCliente(
    cliente: ClienteEntity,
    productos: List<ProductoEntity>,
    onVolverLista: () -> Unit,
    onVerCatalogoProductos: (Boolean) -> Unit,
    onCargarCanastaSugerida: (Map<Int, Int>) -> Unit
) {
    val mapaSugeridos = parsearSugeridos(cliente.sugeridos)
    val productosSugeridos = mapaSugeridos.mapNotNull { (idProd, cant) ->
        val prod = productos.find { it.id == idProd }
        if (prod != null) Pair(prod, cant) else null
    }
    val totalSugeridoCentavos = productosSugeridos.sumOf { (prod, cant) ->
        val subtotal = prod.precioCentavos * cant
        val isv = (subtotal * prod.isvPorcentaje) / 100
        subtotal + isv
    }
    val disponibleCentavos = (LIMITE_CREDITO_CENTAVOS - cliente.saldoCentavos).coerceAtLeast(0)
    val porcentajeUsado = ((cliente.saldoCentavos.toDouble() / LIMITE_CREDITO_CENTAVOS) * 100)
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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Cabecera de la parada / cliente (V.parada del demo FOE 360)
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

        // Tarjeta de Crédito y Cartera del cliente (credito de V.parada en el demo)
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
                        text = "Límite ${formatearLempiras(LIMITE_CREDITO_CENTAVOS)}",
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

        // Canasta sugerida integrada con los Productos del cliente (sug de V.parada en el demo)
        if (productosSugeridos.isNotEmpty()) {
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Canasta sugerida para este cliente",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FoeOnSurface
                        )
                        Text(
                            text = formatearLempiras(totalSugeridoCentavos),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = FoePrimary
                        )
                    }

                    Text(
                        text = "Productos habituales según el historial de compras del cliente:",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )

                    productosSugeridos.forEach { (prod, cant) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(FoeSurfaceContainerLow, RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = prod.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = FoeOnSurface
                                )
                                Text(
                                    text = "${prod.presentacion} · ${formatearLempiras(prod.precioCentavos)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FoeOnSurfaceVariant
                                )
                            }
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = FoePrimaryContainer)
                            ) {
                                Text(
                                    text = "x$cant",
                                    color = FoeOnPrimaryContainer,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onCargarCanastaSugerida(mapaSugeridos) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FoePrimaryContainer,
                                contentColor = FoeOnPrimaryContainer
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cargar sugeridos")
                        }

                        Button(
                            onClick = { onVerCatalogoProductos(false) },
                            colors = ButtonDefaults.buttonColors(containerColor = ModuloAcero),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Ver todos los productos")
                        }
                    }
                }
            }
        }

        // Botón principal para visualizar todos los productos desde este cliente
        Button(
            onClick = { onVerCatalogoProductos(false) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = FoePrimary),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = "Visualizar catálogo de productos (${productos.size})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
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
private fun VistaProductosEnClientes(
    clientes: List<ClienteEntity>,
    clienteActivo: ClienteEntity?,
    mostrarSelectorCliente: Boolean,
    onToggleSelectorCliente: () -> Unit,
    onSeleccionarCliente: (ClienteEntity) -> Unit,
    onVerFichaClienteActivo: () -> Unit,
    productos: List<ProductoEntity>,
    busqueda: String,
    onBusquedaChange: (String) -> Unit,
    categoriaSeleccionada: String,
    onCategoriaChange: (String) -> Unit,
    cantidades: Map<Int, Int>,
    onCambiarCantidad: (Int, Int) -> Unit,
    onLimpiarSeleccion: () -> Unit,
    aplicarDescuento: Boolean,
    onToggleDescuento: () -> Unit
) {
    val mapaSugeridosCliente = parsearSugeridos(clienteActivo?.sugeridos ?: "")
    val consulta = busqueda.trim().lowercase()

    val productosFiltrados = productos.filter { p ->
        val coincideCategoria = when (categoriaSeleccionada) {
            "todo" -> true
            "sug" -> mapaSugeridosCliente.containsKey(p.id)
            else -> p.categoria == categoriaSeleccionada
        }
        val coincideBusqueda = consulta.isEmpty() ||
                p.nombre.lowercase().contains(consulta) ||
                p.presentacion.lowercase().contains(consulta) ||
                p.categoria.lowercase().contains(consulta)
        coincideCategoria && coincideBusqueda
    }

    // Cálculo de totales de productos seleccionados para el cliente activo (igual a cartT() del demo)
    var exentoBruto = 0
    var gravadoBruto = 0
    var totalBultos = 0
    var totalRenglones = 0
    for ((idProd, cant) in cantidades) {
        if (cant <= 0) continue
        val prod = productos.find { it.id == idProd } ?: continue
        totalRenglones += 1
        totalBultos += cant
        if (prod.isvPorcentaje > 0) {
            gravadoBruto += prod.precioCentavos * cant
        } else {
            exentoBruto += prod.precioCentavos * cant
        }
    }
    val esMayorista = (exentoBruto + gravadoBruto) >= MINIMO_MAYORISTA_CENTAVOS
    val descExento = if (aplicarDescuento) (exentoBruto * DESCUENTO_PORCENTAJE) / 100 else 0
    val descGravado = if (aplicarDescuento) (gravadoBruto * DESCUENTO_PORCENTAJE) / 100 else 0
    val exentoNeto = exentoBruto - descExento
    val gravadoNeto = gravadoBruto - descGravado
    val isvCentavos = (gravadoNeto * 15) / 100
    val totalCentavos = exentoNeto + gravadoNeto + isvCentavos
    val descuentoTotal = descExento + descGravado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // FRANJA DEL CLIENTE ACTIVO (cli-strip del demo FOE 360)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = FoeSurfaceContainerLow),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CLIENTE SELECCIONADO",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = FoePrimary
                        )
                        Text(
                            text = if (clienteActivo != null) {
                                "Parada ${clienteActivo.parada} · ${clienteActivo.nombreNegocio}"
                            } else {
                                "Sin cliente seleccionado"
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = FoeOnSurface
                        )
                        if (clienteActivo != null) {
                            Text(
                                text = "${clienteActivo.dueno} · ${clienteActivo.textoCredito} · Saldo ${formatearLempiras(clienteActivo.saldoCentavos)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = FoeOnSurfaceVariant
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(onClick = onVerFichaClienteActivo) {
                            Text("Ficha", fontSize = 12.sp)
                        }
                        Button(
                            onClick = onToggleSelectorCliente,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FoePrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Text(if (mostrarSelectorCliente) "Cerrar" else "Cambiar", fontSize = 12.sp)
                        }
                    }
                }

                // Lista desplegable para cambiar de cliente sin salir de la vista de productos
                if (mostrarSelectorCliente) {
                    HorizontalDivider(color = FoeOutlineVariant)
                    Text(
                        text = "Selecciona el cliente para ver sus productos y sugeridos:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurfaceVariant
                    )
                    clientes.sortedBy { it.parada }.forEach { cli ->
                        val seleccionado = clienteActivo?.id == cli.id
                        Card(
                            onClick = { onSeleccionarCliente(cli) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (seleccionado) FoePrimaryContainer else FoeCardBackground
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Parada ${cli.parada} · ${cli.nombreNegocio}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = if (seleccionado) FoeOnPrimaryContainer else FoeOnSurface
                                    )
                                    Text(
                                        text = "${cli.dueno} · ${cli.textoCredito}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = FoeOnSurfaceVariant
                                    )
                                }
                                if (seleccionado) {
                                    Text(
                                        text = "Activo",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = FoeOnPrimaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Buscador de productos (pq del demo FOE 360)
        OutlinedTextField(
            value = busqueda,
            onValueChange = onBusquedaChange,
            label = { Text("Buscar producto o presentación") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Categorías de productos (CATS del demo FOE 360)
        Text(
            text = "CATEGORÍAS DE PRODUCTOS",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = FoeOnSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = categoriaSeleccionada == "todo",
                onClick = { onCategoriaChange("todo") },
                label = { Text("Todo (${productos.size})") }
            )
            FilterChip(
                selected = categoriaSeleccionada == "sug",
                onClick = { onCategoriaChange("sug") },
                label = { Text("Sugeridos (${mapaSugeridosCliente.size})") }
            )
            FilterChip(
                selected = categoriaSeleccionada == "granos",
                onClick = { onCategoriaChange("granos") },
                label = { Text("Granos") }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = categoriaSeleccionada == "aceites",
                onClick = { onCategoriaChange("aceites") },
                label = { Text("Aceites") }
            )
            FilterChip(
                selected = categoriaSeleccionada == "bebidas",
                onClick = { onCategoriaChange("bebidas") },
                label = { Text("Bebidas") }
            )
            FilterChip(
                selected = categoriaSeleccionada == "limpieza",
                onClick = { onCategoriaChange("limpieza") },
                label = { Text("Limpieza") }
            )
            FilterChip(
                selected = categoriaSeleccionada == "lacteos",
                onClick = { onCategoriaChange("lacteos") },
                label = { Text("Lácteos") }
            )
        }

        // Resumen de productos seleccionados para este cliente (si hay alguno)
        if (totalBultos > 0) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = FoePrimaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Selección para ${clienteActivo?.nombreNegocio ?: "el cliente"}",
                            fontWeight = FontWeight.ExtraBold,
                            color = FoeOnPrimaryContainer,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "$totalRenglones prod. · $totalBultos bultos",
                            fontWeight = FontWeight.Bold,
                            color = FoeOnPrimaryContainer,
                            fontSize = 12.sp
                        )
                    }

                    if (esMayorista) {
                        Text(
                            text = "Compra de mayorista alcanzada (supera L 5,000.00)",
                            color = ModuloVerde,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    if (descuentoTotal > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Descuento $DESCUENTO_PORCENTAJE %:", fontSize = 12.sp, color = FoeOnPrimaryContainer)
                            Text("- ${formatearLempiras(descuentoTotal)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FoeOnPrimaryContainer)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Importe exento:", fontSize = 12.sp, color = FoeOnPrimaryContainer)
                        Text(formatearLempiras(exentoNeto), fontSize = 12.sp, color = FoeOnPrimaryContainer)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Importe gravado 15 %:", fontSize = 12.sp, color = FoeOnPrimaryContainer)
                        Text(formatearLempiras(gravadoNeto), fontSize = 12.sp, color = FoeOnPrimaryContainer)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("ISV 15 %:", fontSize = 12.sp, color = FoeOnPrimaryContainer)
                        Text(formatearLempiras(isvCentavos), fontSize = 12.sp, color = FoeOnPrimaryContainer)
                    }

                    HorizontalDivider(color = FoeOutlineVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total estimado",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = FoeOnPrimaryContainer
                        )
                        Text(
                            text = formatearLempiras(totalCentavos),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = FoeOnPrimaryContainer
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onToggleDescuento,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (aplicarDescuento) "Quitar desc. 5%" else "Aplicar desc. 5%",
                                fontSize = 12.sp
                            )
                        }
                        OutlinedButton(
                            onClick = onLimpiarSeleccion,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Limpiar selección", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Lista de tarjetas de productos (pcHTML del demo FOE 360)
        if (productosFiltrados.isEmpty()) {
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
                        text = if (categoriaSeleccionada == "sug") {
                            "Sin sugerencias para este cliente"
                        } else {
                            "Ningún producto coincide"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "Puedes ver todo el catálogo de productos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FoeOnSurfaceVariant
                    )
                    Button(onClick = { onCategoriaChange("todo") }) {
                        Text("Ver todo")
                    }
                }
            }
        } else {
            productosFiltrados.forEach { prod ->
                val cantActual = cantidades[prod.id] ?: 0
                val cantSugerida = mapaSugeridosCliente[prod.id]
                TarjetaProductoItem(
                    producto = prod,
                    cantidadSeleccionada = cantActual,
                    cantidadSugerida = cantSugerida,
                    onCambiarCantidad = { nueva -> onCambiarCantidad(prod.id, nueva) }
                )
            }
        }
    }
}

@Composable
private fun TarjetaProductoItem(
    producto: ProductoEntity,
    cantidadSeleccionada: Int,
    cantidadSugerida: Int?,
    onCambiarCantidad: (Int) -> Unit
) {
    val colorCategoria = when (producto.categoria) {
        "granos" -> ModuloAmbar
        "aceites" -> ModuloCoral
        "bebidas" -> ModuloAcero
        "limpieza" -> ModuloVerde
        "lacteos" -> ModuloVioleta
        else -> FoePrimary
    }
    val nombreCategoria = when (producto.categoria) {
        "granos" -> "Granos"
        "aceites" -> "Aceites"
        "bebidas" -> "Bebidas"
        "limpieza" -> "Limpieza"
        "lacteos" -> "Lácteos y café"
        else -> producto.categoria
    }

    val disponibleCamion = (producto.stockCamion - cantidadSeleccionada).coerceAtLeast(0)
    val agotado = producto.stockCamion <= 0

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (cantidadSeleccionada > 0) FoeSurfaceContainerLow else FoeCardBackground
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = colorCategoria)
                    ) {
                        Text(
                            text = "$nombreCategoria · ${producto.tipoEmpaque}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    if (cantidadSugerida != null) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = FoePrimaryContainer)
                        ) {
                            Text(
                                text = "Sugerido ($cantidadSugerida)",
                                color = FoeOnPrimaryContainer,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Indicador de existencia en el camión y depósito
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            agotado -> FoeErrorContainer
                            disponibleCamion <= 3 -> ModuloAmbar
                            else -> FoeSurfaceContainerHigh
                        }
                    )
                ) {
                    Text(
                        text = if (agotado) {
                            "Sin existencia"
                        } else {
                            "Camión: $disponibleCamion · Depósito: ${producto.stockDeposito}"
                        },
                        color = when {
                            agotado -> FoeOnErrorContainer
                            disponibleCamion <= 3 -> Color.White
                            else -> FoeOnSurface
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = producto.presentacion,
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = formatearLempiras(producto.precioCentavos),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = if (producto.isvPorcentaje > 0) "+ ISV ${producto.isvPorcentaje} %" else "Exento",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                }
            }

            // Controles para seleccionar / ajustar unidades del producto para el cliente
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (cantidadSeleccionada > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onCambiarCantidad(cantidadSeleccionada - 1) }
                        ) {
                            Text(if (cantidadSeleccionada == 1) "Quitar" else "-")
                        }
                        Text(
                            text = "$cantidadSeleccionada",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = FoeOnSurface
                        )
                        Button(
                            onClick = {
                                if (cantidadSeleccionada < producto.stockCamion) {
                                    onCambiarCantidad(cantidadSeleccionada + 1)
                                }
                            },
                            enabled = cantidadSeleccionada < producto.stockCamion,
                            colors = ButtonDefaults.buttonColors(containerColor = FoePrimary)
                        ) {
                            Text("+")
                        }
                    }
                } else {
                    Button(
                        onClick = { onCambiarCantidad(1) },
                        enabled = !agotado,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FoePrimaryContainer,
                            contentColor = FoeOnPrimaryContainer
                        )
                    ) {
                        Text(
                            text = if (agotado) "Sin existencia" else "+ Seleccionar producto",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
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
    onTomarFoto: () -> Unit,
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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Indicador de progreso por pasos (nc-prog-wrap del demo FOE 360)
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

                    // Bloque de Foto de la fachada
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
                            Button(
                                onClick = onTomarFoto,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (fotoTomada) ModuloVerde else FoePrimary
                                )
                            ) {
                                Text(if (fotoTomada) "Repetir foto de fachada" else "Tomar foto de la fachada")
                            }
                        }
                    }

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = onDireccionChange,
                        label = { Text("Barrio, colonia o aldea (Ej.: Bo. El Centro, Talanga)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Bloque de Ubicación GPS
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
    val fakeProductoCLFK = object : ProductoCLFK {
        override suspend fun insertar(producto: ProductoEntity) {}
        override suspend fun insertarTodos(productos: List<ProductoEntity>) {}
        override suspend fun obtenerTodos(): List<ProductoEntity> = crearProductosPorDefecto()
        override suspend fun obtenerPorCategoria(categoria: String): List<ProductoEntity> =
            crearProductosPorDefecto().filter { it.categoria == categoria }
    }
    ProyectoFOETheme {
        ClientesScreen(
            clienteCLFK = fakeClienteCLFK,
            productoCLFK = fakeProductoCLFK,
            iniciarEnProductos = false,
            onVolverInicio = {}
        )
    }
}
