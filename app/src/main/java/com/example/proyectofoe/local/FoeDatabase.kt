package com.example.proyectofoe.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        UsuarioEntity::class,
        ClienteEntity::class,
        ProductoEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class FoeDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    abstract fun clienteDao(): ClienteDao

    abstract fun productoDao(): ProductoDao
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE usuarios ADD COLUMN correo TEXT NOT NULL DEFAULT 'mzelaya@gmail.com'")
        db.execSQL("ALTER TABLE usuarios ADD COLUMN codigoBodega TEXT NOT NULL DEFAULT 'B-001'")
        db.execSQL("ALTER TABLE usuarios ADD COLUMN contrasenaBodega TEXT NOT NULL DEFAULT 'bodega2026'")
        db.execSQL("ALTER TABLE usuarios ADD COLUMN nombreBodega TEXT NOT NULL DEFAULT 'Bodega Central'")
    }
}
