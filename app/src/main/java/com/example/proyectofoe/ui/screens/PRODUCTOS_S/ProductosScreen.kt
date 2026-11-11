package com.example.proyectofoe.ui.screens.PRODUCTOS_S

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofoe.local.ProductoCLFK
import com.example.proyectofoe.local.ProductoEntity
import com.example.proyectofoe.ui.screens.CLIENTES_S.convertirLempirasACentavos
import com.example.proyectofoe.ui.screens.CLIENTES_S.crearProductosPorDefecto
import com.example.proyectofoe.ui.screens.CLIENTES_S.formatearLempiras
import com.example.proyectofoe.ui.screens.CLIENTES_S.validarNuevoProducto
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
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerHigh
import com.example.proyectofoe.ui.theme.FoeSurfaceContainerLow
import com.example.proyectofoe.ui.theme.ModuloAcero
import com.example.proyectofoe.ui.theme.ModuloAmbar
import com.example.proyectofoe.ui.theme.ModuloCoral
import com.example.proyectofoe.ui.theme.ModuloVerde
import com.example.proyectofoe.ui.theme.ModuloVioleta
import com.example.proyectofoe.ui.theme.OnBrandNavy
import com.example.proyectofoe.ui.theme.OnBrandSubtitle
import com.example.proyectofoe.ui.theme.ProyectoFOETheme
import kotlinx.coroutines.launch

@Composable
fun ProductosScreen(
    productoCLFK: ProductoCLFK,
    onVolverInicio: () -> Unit
) {
    var listaProductos by remember { mutableStateOf(emptyList<ProductoEntity>()) }

    // Subvistas del módulo independiente de Productos:
    // "lista" -> Catálogo e inventario de productos
    // "detalle" -> Ficha de un producto específico
    // "nuevo" -> Formulario para crear un nuevo producto dentro del módulo
    var subvista by remember { mutableStateOf("lista") }

    var productoSeleccionado by remember { mutableStateOf<ProductoEntity?>(null) }

    var busquedaProducto by remember { mutableStateOf("") }
    var categoriaProducto by remember { mutableStateOf("todo") }

    // Estados para crear un nuevo producto
    var nuevoProdNombre by remember { mutableStateOf("") }
    var nuevoProdPresentacion by remember { mutableStateOf("") }
    var nuevoProdCategoria by remember { mutableStateOf("granos") }
    var nuevoProdTipoEmpaque by remember { mutableStateOf("Bolsa") }
    var nuevoProdPrecioLempiras by remember { mutableStateOf("") }
    var nuevoProdIsvPorcentaje by remember { mutableStateOf(15) }
    var nuevoProdStockCamion by remember { mutableStateOf("10") }
    var nuevoProdStockDeposito by remember { mutableStateOf("100") }

    var mensajeAviso by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(productoCLFK) {
        try {
            var productosDb = productoCLFK.obtenerTodos()
            if (productosDb.isEmpty()) {
                productoCLFK.insertarTodos(crearProductosPorDefecto())
                productosDb = productoCLFK.obtenerTodos()
            }
            listaProductos = productosDb
            if (productoSeleccionado == null) {
                productoSeleccionado = productosDb.firstOrNull()
            }
        } catch (e: Exception) {
            listaProductos = crearProductosPorDefecto()
            if (productoSeleccionado == null) {
                productoSeleccionado = listaProductos.firstOrNull()
            }
        }
    }

    val productoActivo = productoSeleccionado ?: listaProductos.firstOrNull()

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
            // BARRA SUPERIOR DEL MÓDULO DE PRODUCTOS
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
                            "detalle" -> productoActivo?.nombre ?: "Detalle del producto"
                            "nuevo" -> "Nuevo producto"
                            else -> "Productos"
                        }
                        val subtituloBarra = when (subvista) {
                            "detalle" -> "${productoActivo?.presentacion ?: ""} · ${productoActivo?.tipoEmpaque ?: ""}"
                            "nuevo" -> "Registro de producto en el catálogo"
                            else -> "${listaProductos.size} productos registrados · catálogo"
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
                                nuevoProdNombre = ""
                                nuevoProdPresentacion = ""
                                nuevoProdCategoria = "granos"
                                nuevoProdTipoEmpaque = "Bolsa"
                                nuevoProdPrecioLempiras = ""
                                nuevoProdIsvPorcentaje = 15
                                nuevoProdStockCamion = "10"
                                nuevoProdStockDeposito = "100"
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

            when (subvista) {
                "lista" -> {
                    VistaCatalogoProductos(
                        productos = listaProductos,
                        busqueda = busquedaProducto,
                        onBusquedaChange = { busquedaProducto = it },
                        categoriaSeleccionada = categoriaProducto,
                        onCategoriaChange = { categoriaProducto = it },
                        onAbrirDetalleProducto = { prod ->
                            productoSeleccionado = prod
                            subvista = "detalle"
                        },
                        onNuevoProducto = {
                            nuevoProdNombre = ""
                            nuevoProdPresentacion = ""
                            nuevoProdCategoria = "granos"
                            nuevoProdTipoEmpaque = "Bolsa"
                            nuevoProdPrecioLempiras = ""
                            nuevoProdIsvPorcentaje = 15
                            nuevoProdStockCamion = "10"
                            nuevoProdStockDeposito = "100"
                            mensajeAviso = ""
                            subvista = "nuevo"
                        }
                    )
                }

                "detalle" -> {
                    if (productoActivo != null) {
                        VistaDetalleProducto(
                            producto = productoActivo,
                            onVolverLista = { subvista = "lista" }
                        )
                    }
                }

                "nuevo" -> {
                    VistaFormularioNuevoProducto(
                        nombre = nuevoProdNombre,
                        onNombreChange = { nuevoProdNombre = it },
                        presentacion = nuevoProdPresentacion,
                        onPresentacionChange = { nuevoProdPresentacion = it },
                        categoria = nuevoProdCategoria,
                        onCategoriaChange = { nuevoProdCategoria = it },
                        tipoEmpaque = nuevoProdTipoEmpaque,
                        onTipoEmpaqueChange = { nuevoProdTipoEmpaque = it },
                        precioLempiras = nuevoProdPrecioLempiras,
                        onPrecioLempirasChange = { nuevoProdPrecioLempiras = it },
                        isvPorcentaje = nuevoProdIsvPorcentaje,
                        onIsvPorcentajeChange = { nuevoProdIsvPorcentaje = it },
                        stockCamion = nuevoProdStockCamion,
                        onStockCamionChange = { nuevoProdStockCamion = it.filter { c -> c.isDigit() } },
                        stockDeposito = nuevoProdStockDeposito,
                        onStockDepositoChange = { nuevoProdStockDeposito = it.filter { c -> c.isDigit() } },
                        onCancelar = {
                            subvista = "lista"
                        },
                        onGuardarProducto = {
                            val centavos = convertirLempirasACentavos(nuevoProdPrecioLempiras)
                            val camion = nuevoProdStockCamion.toIntOrNull() ?: 0
                            val deposito = nuevoProdStockDeposito.toIntOrNull() ?: 0
                            val nuevoProducto = ProductoEntity(
                                id = 0,
                                categoria = nuevoProdCategoria,
                                nombre = nuevoProdNombre.trim(),
                                presentacion = nuevoProdPresentacion.trim(),
                                precioCentavos = centavos,
                                isvPorcentaje = nuevoProdIsvPorcentaje,
                                stockCamion = camion,
                                stockDeposito = deposito,
                                tipoEmpaque = nuevoProdTipoEmpaque
                            )
                            scope.launch {
                                try {
                                    productoCLFK.insertar(nuevoProducto)
                                    listaProductos = productoCLFK.obtenerTodos()
                                } catch (e: Exception) {
                                    val siguienteId = (listaProductos.maxOfOrNull { it.id } ?: 14) + 1
                                    listaProductos = listaProductos + nuevoProducto.copy(id = siguienteId)
                                }
                                categoriaProducto = "todo"
                                mensajeAviso =
                                    "Producto registrado: ${nuevoProducto.nombre} (Total en catálogo: ${listaProductos.size})."
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
private fun VistaCatalogoProductos(
    productos: List<ProductoEntity>,
    busqueda: String,
    onBusquedaChange: (String) -> Unit,
    categoriaSeleccionada: String,
    onCategoriaChange: (String) -> Unit,
    onAbrirDetalleProducto: (ProductoEntity) -> Unit,
    onNuevoProducto: () -> Unit
) {
    val consulta = busqueda.trim().lowercase()

    val productosFiltrados = productos.filter { p ->
        val coincideCategoria = categoriaSeleccionada == "todo" || p.categoria == categoriaSeleccionada
        val coincideBusqueda = consulta.isEmpty() ||
                p.nombre.lowercase().contains(consulta) ||
                p.presentacion.lowercase().contains(consulta) ||
                p.categoria.lowercase().contains(consulta)
        coincideCategoria && coincideBusqueda
    }

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
            label = { Text("Buscar producto, presentación o categoría") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CATEGORÍAS DE PRODUCTOS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = FoeOnSurfaceVariant
            )

            Button(
                onClick = onNuevoProducto,
                colors = ButtonDefaults.buttonColors(
                    containerColor = FoePrimaryContainer,
                    contentColor = FoeOnPrimaryContainer
                )
            ) {
                Text("+ Nuevo producto")
            }
        }

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
                selected = categoriaSeleccionada == "granos",
                onClick = { onCategoriaChange("granos") },
                label = { Text("Granos") }
            )
            FilterChip(
                selected = categoriaSeleccionada == "aceites",
                onClick = { onCategoriaChange("aceites") },
                label = { Text("Aceites") }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
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
                        text = "Ningún producto coincide",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "Puedes limpiar el filtro o registrar un producto nuevo.",
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
                TarjetaProductoCatalogo(
                    producto = prod,
                    onAbrirDetalle = { onAbrirDetalleProducto(prod) }
                )
            }
        }
    }
}

@Composable
private fun TarjetaProductoCatalogo(
    producto: ProductoEntity,
    onAbrirDetalle: () -> Unit
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

    val agotado = producto.stockCamion <= 0

    Card(
        onClick = onAbrirDetalle,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = FoeCardBackground),
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
                    colors = CardDefaults.cardColors(containerColor = colorCategoria)
                ) {
                    Box(
                        modifier = Modifier
                            .width(44.dp)
                            .height(44.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "#${producto.id}",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = producto.nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "${producto.presentacion} · $nombreCategoria",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                    Text(
                        text = "Empaque: ${producto.tipoEmpaque}",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = formatearLempiras(producto.precioCentavos),
                        fontSize = 16.sp,
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

            HorizontalDivider(color = FoeOutlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            agotado -> FoeErrorContainer
                            producto.stockCamion <= 3 -> ModuloAmbar
                            else -> FoeSurfaceContainerHigh
                        }
                    )
                ) {
                    Text(
                        text = if (agotado) {
                            "Sin existencia en camión · Depósito: ${producto.stockDeposito}"
                        } else {
                            "Camión: ${producto.stockCamion} · Depósito: ${producto.stockDeposito}"
                        },
                        color = when {
                            agotado -> FoeOnErrorContainer
                            producto.stockCamion <= 3 -> Color.White
                            else -> FoeOnSurface
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

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
private fun VistaDetalleProducto(
    producto: ProductoEntity,
    onVolverLista: () -> Unit
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

    val isvMontoCentavos = (producto.precioCentavos * producto.isvPorcentaje) / 100
    val precioConIsvCentavos = producto.precioCentavos + isvMontoCentavos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = colorCategoria)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(56.dp)
                                .height(56.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "#${producto.id}",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = producto.nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = FoeOnSurface
                        )
                        Text(
                            text = "Presentación: ${producto.presentacion}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = FoeOnSurfaceVariant
                        )
                        Text(
                            text = "Categoría: $nombreCategoria · Empaque: ${producto.tipoEmpaque}",
                            style = MaterialTheme.typography.bodySmall,
                            color = FoeOnSurfaceVariant
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
                Text(
                    text = "PRECIO E IMPUESTO",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = FoeOnSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Precio base:", color = FoeOnSurfaceVariant)
                    Text(
                        text = formatearLempiras(producto.precioCentavos),
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (producto.isvPorcentaje > 0) "ISV (${producto.isvPorcentaje} %):" else "Impuesto:",
                        color = FoeOnSurfaceVariant
                    )
                    Text(
                        text = if (producto.isvPorcentaje > 0) formatearLempiras(isvMontoCentavos) else "Exento",
                        fontWeight = FontWeight.SemiBold,
                        color = FoeOnSurface
                    )
                }

                HorizontalDivider(color = FoeOutlineVariant)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Precio final por unidad",
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = formatearLempiras(precioConIsvCentavos),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoePrimary
                    )
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
                Text(
                    text = "EXISTENCIAS EN INVENTARIO",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = FoeOnSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Stock en camión:", color = FoeOnSurfaceVariant)
                    Text(
                        text = "${producto.stockCamion} unidades",
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Stock en depósito / bodega:", color = FoeOnSurfaceVariant)
                    Text(
                        text = "${producto.stockDeposito} unidades",
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                }
            }
        }

        OutlinedButton(
            onClick = onVolverLista,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al catálogo de productos")
        }
    }
}

@Composable
private fun VistaFormularioNuevoProducto(
    nombre: String,
    onNombreChange: (String) -> Unit,
    presentacion: String,
    onPresentacionChange: (String) -> Unit,
    categoria: String,
    onCategoriaChange: (String) -> Unit,
    tipoEmpaque: String,
    onTipoEmpaqueChange: (String) -> Unit,
    precioLempiras: String,
    onPrecioLempirasChange: (String) -> Unit,
    isvPorcentaje: Int,
    onIsvPorcentajeChange: (Int) -> Unit,
    stockCamion: String,
    onStockCamionChange: (String) -> Unit,
    stockDeposito: String,
    onStockDepositoChange: (String) -> Unit,
    onCancelar: () -> Unit,
    onGuardarProducto: () -> Unit
) {
    val formularioValido = validarNuevoProducto(nombre, presentacion, precioLempiras)
    val centavosVistaPrevia = convertirLempirasACentavos(precioLempiras)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Datos del nuevo producto",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = FoeOnSurfaceVariant
            )
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = ModuloAcero)
            ) {
                Text(
                    text = "Catálogo",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

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
                    text = "Información general",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoeOnSurface
                )
                Text(
                    text = "Nombre, presentación, categoría y empaque",
                    style = MaterialTheme.typography.bodySmall,
                    color = FoeOnSurfaceVariant
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = onNombreChange,
                    label = { Text("Nombre del producto (Ej.: Arroz precocido 5 lb)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = presentacion,
                    onValueChange = onPresentacionChange,
                    label = { Text("Presentación (Ej.: Fardo de 10 o Caja de 12)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(color = FoeOutlineVariant)

                Text(
                    text = "Categoría del producto",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = FoeOnSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = categoria == "granos",
                        onClick = { onCategoriaChange("granos") },
                        label = { Text("Granos") }
                    )
                    FilterChip(
                        selected = categoria == "aceites",
                        onClick = { onCategoriaChange("aceites") },
                        label = { Text("Aceites") }
                    )
                    FilterChip(
                        selected = categoria == "bebidas",
                        onClick = { onCategoriaChange("bebidas") },
                        label = { Text("Bebidas") }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = categoria == "limpieza",
                        onClick = { onCategoriaChange("limpieza") },
                        label = { Text("Limpieza") }
                    )
                    FilterChip(
                        selected = categoria == "lacteos",
                        onClick = { onCategoriaChange("lacteos") },
                        label = { Text("Lácteos y café") }
                    )
                }

                HorizontalDivider(color = FoeOutlineVariant)

                Text(
                    text = "Tipo de empaque",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = FoeOnSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = tipoEmpaque == "Bolsa",
                        onClick = { onTipoEmpaqueChange("Bolsa") },
                        label = { Text("Bolsa") }
                    )
                    FilterChip(
                        selected = tipoEmpaque == "Fardo",
                        onClick = { onTipoEmpaqueChange("Fardo") },
                        label = { Text("Fardo") }
                    )
                    FilterChip(
                        selected = tipoEmpaque == "Caja",
                        onClick = { onTipoEmpaqueChange("Caja") },
                        label = { Text("Caja") }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = tipoEmpaque == "Botella",
                        onClick = { onTipoEmpaqueChange("Botella") },
                        label = { Text("Botella") }
                    )
                    FilterChip(
                        selected = tipoEmpaque == "Sobre",
                        onClick = { onTipoEmpaqueChange("Sobre") },
                        label = { Text("Sobre") }
                    )
                    FilterChip(
                        selected = tipoEmpaque == "Barra",
                        onClick = { onTipoEmpaqueChange("Barra") },
                        label = { Text("Barra") }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = tipoEmpaque == "Galón",
                        onClick = { onTipoEmpaqueChange("Galón") },
                        label = { Text("Galón") }
                    )
                    FilterChip(
                        selected = tipoEmpaque == "Lata",
                        onClick = { onTipoEmpaqueChange("Lata") },
                        label = { Text("Lata") }
                    )
                    FilterChip(
                        selected = tipoEmpaque == "Tarro",
                        onClick = { onTipoEmpaqueChange("Tarro") },
                        label = { Text("Tarro") }
                    )
                }
            }
        }

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
                    text = "Precio, impuesto y existencias",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoeOnSurface
                )
                Text(
                    text = "Configura el precio en Lempiras, ISV y existencias iniciales",
                    style = MaterialTheme.typography.bodySmall,
                    color = FoeOnSurfaceVariant
                )

                OutlinedTextField(
                    value = precioLempiras,
                    onValueChange = onPrecioLempirasChange,
                    label = { Text("Precio en Lempiras (Ej.: 420.00)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (centavosVistaPrevia > 0) {
                    Text(
                        text = "Precio registrado: ${formatearLempiras(centavosVistaPrevia)} (${if (isvPorcentaje > 0) "+ ISV $isvPorcentaje %" else "Exento"})",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = FoePrimary
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = isvPorcentaje == 0,
                        onClick = { onIsvPorcentajeChange(0) },
                        label = { Text("Exento (0 %)") }
                    )
                    FilterChip(
                        selected = isvPorcentaje == 15,
                        onClick = { onIsvPorcentajeChange(15) },
                        label = { Text("Gravado (+15 % ISV)") }
                    )
                }

                HorizontalDivider(color = FoeOutlineVariant)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = stockCamion,
                        onValueChange = onStockCamionChange,
                        label = { Text("Stock camión") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = stockDeposito,
                        onValueChange = onStockDepositoChange,
                        label = { Text("Stock depósito") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = onGuardarProducto,
                enabled = formularioValido,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ModuloVerde),
                modifier = Modifier
                    .weight(2f)
                    .height(54.dp)
            ) {
                Text(
                    text = "Guardar producto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductosScreenPreview() {
    val fakeProductoCLFK = object : ProductoCLFK {
        override suspend fun insertar(producto: ProductoEntity) {}
        override suspend fun insertarTodos(productos: List<ProductoEntity>) {}
        override suspend fun obtenerTodos(): List<ProductoEntity> = crearProductosPorDefecto()
        override suspend fun obtenerPorCategoria(categoria: String): List<ProductoEntity> =
            crearProductosPorDefecto().filter { it.categoria == categoria }
    }
    ProyectoFOETheme {
        ProductosScreen(
            productoCLFK = fakeProductoCLFK,
            onVolverInicio = {}
        )
    }
}
