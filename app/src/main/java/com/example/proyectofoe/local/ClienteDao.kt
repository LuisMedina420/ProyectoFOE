package com.example.proyectofoe.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cliente: ClienteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(clientes: List<ClienteEntity>)

    @Update
    suspend fun actualizar(cliente: ClienteEntity)

    @Query("SELECT * FROM clientes ORDER BY nombreNegocio ASC")
    suspend fun obtenerTodosOrdenadosPorNombre(): List<ClienteEntity>

    @Query("SELECT * FROM clientes ORDER BY parada ASC")
    suspend fun obtenerTodosPorParada(): List<ClienteEntity>

    @Query("SELECT * FROM clientes WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): ClienteEntity?
}
