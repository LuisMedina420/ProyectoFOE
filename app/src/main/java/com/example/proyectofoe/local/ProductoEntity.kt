package com.example.proyectofoe.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey val id: Int,
    val categoria: String,      // "granos", "aceites", "bebidas", "limpieza", "lacteos"
    val nombre: String,
    val presentacion: String,
    val precioCentavos: Int,    // En centavos de Lempira (ej. 42000 = L 420.00)
    val isvPorcentaje: Int,     // 0 (Exento) o 15 (+ ISV 15 %)
    val stockCamion: Int,
    val stockDeposito: Int,
    val tipoEmpaque: String     // "bolsa", "botella", "fardo", "caja", "barra", "sobre", "galon", "lata", "tarro"
)
