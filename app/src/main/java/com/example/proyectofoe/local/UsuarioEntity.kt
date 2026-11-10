package com.example.proyectofoe.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuario: String,
    val correo: String = "mzelaya@gmail.com",
    val contrasena: String,
    val codigoBodega: String = "B-001",
    val contrasenaBodega: String = "bodega2026",
    val nombreBodega: String = "Bodega Central",
    val nombre: String,
    val iniciales: String = "CLFK",
    val rol: String,
    val subtituloRol: String,
    val ruta: String,
    val vehiculo: String,
    val placa: String,
    val empresa: String
)
