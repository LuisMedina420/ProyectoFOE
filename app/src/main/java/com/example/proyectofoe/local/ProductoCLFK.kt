package com.example.proyectofoe.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductoCLFK {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: ProductoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(productos: List<ProductoEntity>)

    @Query("SELECT * FROM productos ORDER BY id ASC")
    suspend fun obtenerTodos(): List<ProductoEntity>

    @Query("SELECT * FROM productos WHERE categoria = :categoria ORDER BY id ASC")
    suspend fun obtenerPorCategoria(categoria: String): List<ProductoEntity>
}
