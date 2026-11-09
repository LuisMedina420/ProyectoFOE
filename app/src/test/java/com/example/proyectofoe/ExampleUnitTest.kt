package com.example.proyectofoe

import com.example.proyectofoe.ui.screens.CLIENTES_S.crearClientesPorDefecto
import com.example.proyectofoe.ui.screens.CLIENTES_S.crearProductosPorDefecto
import com.example.proyectofoe.ui.screens.CLIENTES_S.formatearLempiras
import com.example.proyectofoe.ui.screens.CLIENTES_S.obtenerIniciales
import com.example.proyectofoe.ui.screens.CLIENTES_S.parsearSugeridos
import com.example.proyectofoe.ui.screens.CLIENTES_S.validarPaso1NuevoCliente
import com.example.proyectofoe.ui.screens.CLIENTES_S.validarPaso2NuevoCliente
import com.example.proyectofoe.ui.screens.LOGIN_S.CONTRASENA_DEMO
import com.example.proyectofoe.ui.screens.LOGIN_S.USUARIO_DEMO
import com.example.proyectofoe.ui.screens.LOGIN_S.construirMensajeIntentoFallido
import com.example.proyectofoe.ui.screens.LOGIN_S.crearUsuarioPorDefecto
import com.example.proyectofoe.ui.screens.LOGIN_S.validarCredencialesDemo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun usuarioPorDefecto_contieneDatosDelDemoFOE360() {
        val usuario = crearUsuarioPorDefecto()
        assertEquals("mzelaya", usuario.usuario)
        assertEquals("ruta2026", usuario.contrasena)
        assertEquals("Mario Zelaya", usuario.nombre)
        assertEquals("MZ", usuario.iniciales)
        assertEquals("Vendedor", usuario.rol)
        assertEquals("R-07 Talanga - Guaimaca", usuario.ruta)
        assertEquals("Camión C-12", usuario.vehiculo)
        assertEquals("HDA 4821", usuario.placa)
        assertEquals("Distribuidora La Montaña", usuario.empresa)
    }

    @Test
    fun login_validaCredencialesCorrectasEIncorrectas() {
        assertTrue(validarCredencialesDemo(USUARIO_DEMO, CONTRASENA_DEMO))
        assertTrue(validarCredencialesDemo("MZELAYA", "ruta2026"))
        assertTrue(validarCredencialesDemo("  mzelaya  ", "ruta2026"))

        assertFalse(validarCredencialesDemo("mzelaya", "RUTA2026"))
        assertFalse(validarCredencialesDemo("mzelaya", "incorrecta"))
        assertFalse(validarCredencialesDemo("otro", "ruta2026"))
        assertFalse(validarCredencialesDemo("", ""))
    }

    @Test
    fun login_controlaIntentosYBloqueoComoEnElDemo() {
        assertEquals(
            "Usuario o contraseña incorrectos.",
            construirMensajeIntentoFallido(1)
        )
        assertEquals(
            "Usuario o contraseña incorrectos.",
            construirMensajeIntentoFallido(2)
        )
        assertEquals(
            "Usuario o contraseña incorrectos. Te quedan 2 intentos antes de que se bloquee 15 minutos.",
            construirMensajeIntentoFallido(3)
        )
        assertEquals(
            "Usuario o contraseña incorrectos. Te queda 1 intento antes de que se bloquee 15 minutos.",
            construirMensajeIntentoFallido(4)
        )
        assertEquals(
            "Demasiados intentos. Espera 15:00 o pide a tu supervisor que te desbloquee.",
            construirMensajeIntentoFallido(5)
        )
    }

    @Test
    fun clientesPorDefecto_contieneLas12ParadasDelDemo() {
        val clientes = crearClientesPorDefecto()
        assertEquals(12, clientes.size)
        val parada5 = clientes.first { it.parada == 5 }
        assertEquals("Abarrotes Mi Esperanza", parada5.nombreNegocio)
        assertEquals("Rosa Mejía", parada5.dueno)
        assertEquals(412000, parada5.saldoCentavos)
        assertEquals("L 4,120.00", formatearLempiras(parada5.saldoCentavos))
    }

    @Test
    fun productosPorDefecto_contieneLos14ProductosDelDemo() {
        val productos = crearProductosPorDefecto()
        assertEquals(14, productos.size)
        val arroz = productos.first { it.id == 1 }
        assertEquals("Arroz precocido 5 lb", arroz.nombre)
        assertEquals(0, arroz.isvPorcentaje)
        val harina = productos.first { it.id == 4 }
        assertEquals(0, harina.stockCamion)
    }

    @Test
    fun clientes_parseaSugeridosYValidaNuevoCliente() {
        val sugeridos = parsearSugeridos("1:2,7:2,5:1,2:1")
        assertEquals(4, sugeridos.size)
        assertEquals(2, sugeridos[1])
        assertEquals(1, sugeridos[5])

        assertEquals("RM", obtenerIniciales("Rosa Mejía"))
        assertTrue(validarPaso1NuevoCliente("Pulpería La Esperanza", "Rosa Mejía", "9999-9999"))
        assertFalse(validarPaso1NuevoCliente("AB", "Rosa Mejía", "9999-9999"))
        assertFalse(validarPaso1NuevoCliente("Pulpería La Esperanza", "Rosa Mejía", "1234"))

        assertTrue(validarPaso2NuevoCliente(true, true, "Bo. El Centro, Talanga"))
        assertFalse(validarPaso2NuevoCliente(false, true, "Bo. El Centro, Talanga"))
    }
}
