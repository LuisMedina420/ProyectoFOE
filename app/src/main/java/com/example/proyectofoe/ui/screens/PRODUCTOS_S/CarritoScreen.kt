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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.proyectofoe.ui.screens.CLIENTES_S.crearProductosPorDefecto
import com.example.proyectofoe.ui.screens.CLIENTES_S.formatearLempiras
import com.example.proyectofoe.ui.theme.BrandNavy
import com.example.proyectofoe.ui.theme.FoeCardBackground
import com.example.proyectofoe.ui.theme.FoeError
import com.example.proyectofoe.ui.theme.FoeOnPrimaryContainer
import com.example.proyectofoe.ui.theme.FoeOnSurface
import com.example.proyectofoe.ui.theme.FoeOnSurfaceVariant
import com.example.proyectofoe.ui.theme.FoeOutlineVariant
import com.example.proyectofoe.ui.theme.FoePrimary
import com.example.proyectofoe.ui.theme.FoePrimaryContainer
import com.example.proyectofoe.ui.theme.FoeSurface
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

data class ItemCarrito(
    val producto: ProductoEntity,
    val cantidad: Int
)

data class VentaRealizadaResumen(
    val numeroVenta: Int,
    val items: List<ItemCarrito>,
    val subtotalCentavos: Int,
    val isvCentavos: Int,
    val totalCentavos: Int
)

fun calcularSubtotalCarritoCentavos(items: List<ItemCarrito>): Int =
    items.sumOf { it.producto.precioCentavos * it.cantidad }

fun calcularIsvCarritoCentavos(items: List<ItemCarrito>): Int =
    items.sumOf { ((it.producto.precioCentavos * it.producto.isvPorcentaje) / 100) * it.cantidad }

fun calcularTotalCarritoCentavos(items: List<ItemCarrito>): Int =
    calcularSubtotalCarritoCentavos(items) + calcularIsvCarritoCentavos(items)

@Composable
fun CarritoScreen(
    items: List<ItemCarrito>,
    productoCLFK: ProductoCLFK,
    onCambiarCantidad: (Int, Int) -> Unit,
    onQuitarProducto: (Int) -> Unit,
    onVaciarCarrito: () -> Unit,
    onVentaCompletada: () -> Unit,
    onVolver: () -> Unit,
    onIrAProductos: () -> Unit
) {
    var ventaRealizada by remember { mutableStateOf<VentaRealizadaResumen?>(null) }
    var contadorVentas by remember { mutableStateOf(1) }
    var procesandoVenta by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val totalUnidades = items.sumOf { it.cantidad }
    val subtotalCentavos = calcularSubtotalCarritoCentavos(items)
    val isvCentavos = calcularIsvCarritoCentavos(items)
    val totalCentavos = subtotalCentavos + isvCentavos

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
            // BARRA SUPERIOR DEL CARRITO DE COMPRA
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
                        onClick = onVolver,
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
                        Text(
                            text = "Carrito de compra",
                            color = OnBrandNavy,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (ventaRealizada != null) {
                                "Comprobante de venta #${ventaRealizada!!.numeroVenta}"
                            } else {
                                "$totalUnidades unidades · ${items.size} productos"
                            },
                            color = OnBrandSubtitle,
                            fontSize = 12.sp
                        )
                    }

                    if (items.isNotEmpty() && ventaRealizada == null) {
                        OutlinedButton(
                            onClick = onVaciarCarrito,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = OnBrandNavy
                            )
                        ) {
                            Text("Vaciar")
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (ventaRealizada != null) {
                    val resumen = ventaRealizada!!
                    VistaVentaRealizada(
                        resumen = resumen,
                        onSeguirComprando = {
                            ventaRealizada = null
                            onIrAProductos()
                        },
                        onVolverInicio = {
                            ventaRealizada = null
                            onVolver()
                        }
                    )
                } else if (items.isEmpty()) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = FoeCardBackground),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Tu carrito está vacío",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = FoeOnSurface
                            )
                            Text(
                                text = "Entra al catálogo de Productos, elige la cantidad con + y - y presiona Agregar.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = FoeOnSurfaceVariant
                            )
                            Button(
                                onClick = onIrAProductos,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ModuloAcero,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Ir al catálogo de productos")
                            }
                        }
                    }
                } else {
                    items.forEach { item ->
                        TarjetaItemCarrito(
                            item = item,
                            onSumar = {
                                onCambiarCantidad(item.producto.id, item.cantidad + 1)
                            },
                            onRestar = {
                                if (item.cantidad > 1) {
                                    onCambiarCantidad(item.producto.id, item.cantidad - 1)
                                } else {
                                    onQuitarProducto(item.producto.id)
                                }
                            },
                            onQuitar = {
                                onQuitarProducto(item.producto.id)
                            }
                        )
                    }

                    // RESUMEN DE SUBTOTAL, ISV Y TOTAL
                    Card(
                        shape = RoundedCornerShape(24.dp),
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
                                text = "RESUMEN DE VENTA",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = FoeOnSurfaceVariant
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Unidades totales:", color = FoeOnSurfaceVariant)
                                Text(
                                    text = "$totalUnidades uds.",
                                    fontWeight = FontWeight.SemiBold,
                                    color = FoeOnSurface
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Subtotal:", color = FoeOnSurfaceVariant)
                                Text(
                                    text = formatearLempiras(subtotalCentavos),
                                    fontWeight = FontWeight.Bold,
                                    color = FoeOnSurface
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("ISV:", color = FoeOnSurfaceVariant)
                                Text(
                                    text = formatearLempiras(isvCentavos),
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
                                    text = "Total a pagar",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FoeOnSurface
                                )
                                Text(
                                    text = formatearLempiras(totalCentavos),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = FoePrimary
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (!procesandoVenta) {
                                procesandoVenta = true
                                val snapshotItems = items.toList()
                                val resumenActual = VentaRealizadaResumen(
                                    numeroVenta = contadorVentas,
                                    items = snapshotItems,
                                    subtotalCentavos = subtotalCentavos,
                                    isvCentavos = isvCentavos,
                                    totalCentavos = totalCentavos
                                )
                                scope.launch {
                                    try {
                                        snapshotItems.forEach { item ->
                                            val nuevoStockCamion =
                                                (item.producto.stockCamion - item.cantidad).coerceAtLeast(0)
                                            productoCLFK.actualizar(
                                                item.producto.copy(stockCamion = nuevoStockCamion)
                                            )
                                        }
                                    } catch (_: Exception) {
                                    }
                                    contadorVentas += 1
                                    ventaRealizada = resumenActual
                                    onVentaCompletada()
                                    procesandoVenta = false
                                }
                            }
                        },
                        enabled = !procesandoVenta,
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ModuloVerde,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            text = if (procesandoVenta) {
                                "Procesando venta..."
                            } else {
                                "Hacer venta · ${formatearLempiras(totalCentavos)}"
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaItemCarrito(
    item: ItemCarrito,
    onSumar: () -> Unit,
    onRestar: () -> Unit,
    onQuitar: () -> Unit
) {
    val prod = item.producto
    val colorCategoria = when (prod.categoria) {
        "granos" -> ModuloAmbar
        "aceites" -> ModuloCoral
        "bebidas" -> ModuloAcero
        "limpieza" -> ModuloVerde
        "lacteos" -> ModuloVioleta
        else -> FoePrimary
    }
    val subtotalLineaCentavos = prod.precioCentavos * item.cantidad
    val isvLineaCentavos = ((prod.precioCentavos * prod.isvPorcentaje) / 100) * item.cantidad
    val totalLineaCentavos = subtotalLineaCentavos + isvLineaCentavos

    Card(
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
                            .width(42.dp)
                            .height(42.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "#${prod.id}",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = prod.nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                    Text(
                        text = "${prod.presentacion} · ${formatearLempiras(prod.precioCentavos)} c/u",
                        style = MaterialTheme.typography.bodySmall,
                        color = FoeOnSurfaceVariant
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = formatearLempiras(totalLineaCentavos),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoePrimary
                    )
                    Text(
                        text = if (prod.isvPorcentaje > 0) "Incluye ISV" else "Exento",
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onRestar) {
                        Text("-", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Text(
                        text = "${item.cantidad}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FoeOnSurface,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    OutlinedButton(onClick = onSumar) {
                        Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = onQuitar,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = FoeError
                    )
                ) {
                    Text("Quitar", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun VistaVentaRealizada(
    resumen: VentaRealizadaResumen,
    onSeguirComprando: () -> Unit,
    onVolverInicio: () -> Unit
) {
    val totalUnidades = resumen.items.sumOf { it.cantidad }

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
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FoeSurfaceContainerLow),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = null,
                        colors = CheckboxDefaults.colors(
                            checkedColor = ModuloVerde,
                            checkmarkColor = Color.White,
                            disabledCheckedColor = ModuloVerde,
                            disabledIndeterminateColor = ModuloVerde
                        )
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Venta realizada",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ModuloVerde
                        )
                        Text(
                            text = "Comprobante #${resumen.numeroVenta} · $totalUnidades unidades vendidas",
                            style = MaterialTheme.typography.bodySmall,
                            color = FoeOnSurfaceVariant
                        )
                    }
                }
            }

            Text(
                text = "DETALLE DE LO VENDIDO",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = FoeOnSurfaceVariant
            )

            resumen.items.forEach { item ->
                val subtotalItem = item.producto.precioCentavos * item.cantidad
                val isvItem = ((item.producto.precioCentavos * item.producto.isvPorcentaje) / 100) * item.cantidad
                val totalItem = subtotalItem + isvItem

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${item.cantidad} x ${item.producto.nombre}",
                            fontWeight = FontWeight.Bold,
                            color = FoeOnSurface
                        )
                        Text(
                            text = "${item.producto.presentacion} · ${formatearLempiras(item.producto.precioCentavos)} c/u",
                            style = MaterialTheme.typography.bodySmall,
                            color = FoeOnSurfaceVariant
                        )
                    }
                    Text(
                        text = formatearLempiras(totalItem),
                        fontWeight = FontWeight.Bold,
                        color = FoeOnSurface
                    )
                }
            }

            HorizontalDivider(color = FoeOutlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal:", color = FoeOnSurfaceVariant)
                Text(
                    text = formatearLempiras(resumen.subtotalCentavos),
                    fontWeight = FontWeight.SemiBold,
                    color = FoeOnSurface
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("ISV:", color = FoeOnSurfaceVariant)
                Text(
                    text = formatearLempiras(resumen.isvCentavos),
                    fontWeight = FontWeight.SemiBold,
                    color = FoeOnSurface
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total vendido",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = FoeOnSurface
                )
                Text(
                    text = formatearLempiras(resumen.totalCentavos),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ModuloVerde
                )
            }
        }
    }

    Button(
        onClick = onSeguirComprando,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ModuloAcero,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text("Seguir agregando productos", fontWeight = FontWeight.Bold)
    }

    OutlinedButton(
        onClick = onVolverInicio,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Volver")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarritoScreenPreview() {
    val productos = crearProductosPorDefecto()
    val fakeProductoCLFK = object : ProductoCLFK {
        override suspend fun insertar(producto: ProductoEntity) {}
        override suspend fun insertarTodos(productos: List<ProductoEntity>) {}
        override suspend fun actualizar(producto: ProductoEntity) {}
        override suspend fun obtenerTodos(): List<ProductoEntity> = productos
        override suspend fun obtenerPorCategoria(categoria: String): List<ProductoEntity> = productos
    }
    ProyectoFOETheme {
        CarritoScreen(
            items = listOf(
                ItemCarrito(productos[0], 3),
                ItemCarrito(productos[2], 2)
            ),
            productoCLFK = fakeProductoCLFK,
            onCambiarCantidad = { _, _ -> },
            onQuitarProducto = {},
            onVaciarCarrito = {},
            onVentaCompletada = {},
            onVolver = {},
            onIrAProductos = {}
        )
    }
}
