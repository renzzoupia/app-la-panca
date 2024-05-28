package com.braxly.lapancaproject;

import org.junit.Test;

import static org.junit.Assert.*;

import com.braxly.lapancaproject.models.Cliente;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testConstructorAndGetters() {
        // Arrange
        String clieId = "1";
        String clieUsuaId = "u1";
        String clieNombres = "Juan";
        String clieApellidos = "Perez";
        String clieDni = "12345678";
        String clieCelular = "987654321";
        String clieCorreo = "juan.perez@example.com";
        String clieDireccion = "123 Main St";

        // Act
        Cliente cliente = new Cliente(clieId, clieUsuaId, clieNombres, clieApellidos, clieDni, clieCelular, clieCorreo, clieDireccion);

        // Assert
        assertEquals(clieId, cliente.getClieId());
        assertEquals(clieUsuaId, cliente.getClieUsuaId());
        assertEquals(clieNombres, cliente.getClieNombres());
        assertEquals(clieApellidos, cliente.getClieApellidos());
        assertEquals(clieDni, cliente.getClieDni());
        assertEquals(clieCelular, cliente.getClieCelular());
        assertEquals(clieCorreo, cliente.getClieCorreo());
        assertEquals(clieDireccion, cliente.getClieDireccion());
    }
}