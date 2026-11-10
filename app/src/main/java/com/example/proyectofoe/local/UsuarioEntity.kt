package com.example.proyectofoe.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val usuario: String,

    @ColumnInfo(defaultValue = "mzelaya@gmail.com")
    val correo: String = "mzelaya@gmail.com",

    val contrasena: String,

    @ColumnInfo(defaultValue = "B-001")
    val codigoBodega: String = "B-001",

    @ColumnInfo(defaultValue = "bodega2026")
    val contrasenaBodega: String = "bodega2026",

    @ColumnInfo(defaultValue = "Bodega Central")
    val nombreBodega: String = "Bodega Central",

    val nombre: String,
    @ColumnInfo(defaultValue = "CLFK")
    val iniciales: String = "CLFK",
    val rol: String,
    val subtituloRol: String,
    val ruta: String,
    val vehiculo: String,
    val placa: String,
    val empresa: String
)
