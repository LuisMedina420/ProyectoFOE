package com.example.proyectofoe.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val parada: Int,
    val nombreNegocio: String,
    val dueno: String,
    val iniciales: String,
    val direccion: String,
    val telefono: String,
    val rtnODni: String,
    val estadoCredito: String, // "ok", "warn", "bad"
    val textoCredito: String,  // "Al día", "Vence en 2 días", "Mora 12 días", etc.
    val saldoCentavos: Int,    // En centavos de Lempira (ej. 412000 = L 4,120.00)
    val estadoVisita: String,  // "venta", "noventa", "pend"
    val detalleVisita: String, // Ej. "Vendida · L 3,480.50" o "Por visitar"
    val pideCredito: Boolean,
    val tieneFotoFachada: Boolean,
    val tieneUbicacionGps: Boolean,
    val sugeridos: String,     // Ej. "1:2,7:2,5:1,2:1" (idProducto:cantidad)
    val fotoUri: String? = null
)
