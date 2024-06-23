package com.braxly.lapancaproject;

import org.junit.Test;

import static org.junit.Assert.*;

import com.braxly.lapancaproject.models.DetallePedido;
import com.braxly.lapancaproject.models.Mesa;
import com.braxly.lapancaproject.models.Pedido;
import com.braxly.lapancaproject.models.Producto;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModeloTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testClienteConstructorAndGetters() {
        // Arrange
        String depeId = "1";
        String dedePediId = "p1";
        String dedeProdId = "prod123";
        String dedeCantidad = "2";
        String dedeSubtotal = "150.00";
        String depeProdNombre = "Pan Integral";
        String dedeProdPrecio = "75.00";

        // Act
        DetallePedido detalle = new DetallePedido(depeId, dedePediId, dedeProdId, dedeCantidad, dedeSubtotal, depeProdNombre, dedeProdPrecio);

        // Assert
        assertEquals(depeId, detalle.getDepeId());
        assertEquals(dedePediId, detalle.getDedePediId());
        assertEquals(dedeProdId, detalle.getDedeProdId());
        assertEquals(dedeCantidad, detalle.getDedeCantidad());
        assertEquals(dedeSubtotal, detalle.getDedeSubtotal());
        assertEquals(depeProdNombre, detalle.getDepeProdNombre());
        assertEquals(dedeProdPrecio, detalle.getDedeProdPrecio());
    }

    @Test
    public void testDetallePedidoConstructorAndGetters() {
        // Arrange
        String depeId = "1";
        String dedePediId = "u1";
        String dedeProdId = "Juan";
        String dedeCantidad = "Perez";
        String dedeSubtotal = "12345678";
        String depeProdNombre = "987654321";
        String dedeProdPrecio = "juan.perez@example.com";

        // Act
        DetallePedido detalle = new DetallePedido(depeId, dedePediId, dedeProdId, dedeCantidad, dedeSubtotal, depeProdNombre, dedeProdPrecio);

        // Assert
        assertEquals(depeId, detalle.getDepeId());
        assertEquals(dedePediId, detalle.getDedePediId());
        assertEquals(dedeProdId, detalle.getDedeProdId());
        assertEquals(dedeCantidad, detalle.getDedeCantidad());
        assertEquals(dedeSubtotal, detalle.getDedeSubtotal());
        assertEquals(depeProdNombre, detalle.getDepeProdNombre());
        assertEquals(dedeProdPrecio, detalle.getDedeProdPrecio());
    }

    @Test
    public void testMesaConstructorAndGetters() {
        // Arrange
        String mesaId = "M001";
        String mesaRestId = "R100";
        String mesaNumero = "10";
        String mesaCantidadPersonas = "4";
        String mesaReferenciaUbicacion = "Cerca de la ventana";
        String mesaActivo = "true";
        String mesaEstado = "disponible";

        // Act
        Mesa mesa = new Mesa(mesaId, mesaRestId, mesaNumero, mesaCantidadPersonas, mesaReferenciaUbicacion, mesaActivo, mesaEstado);

        // Assert
        assertEquals(mesaId, mesa.getMesaId());
        assertEquals(mesaRestId, mesa.getMesaRestId());
        assertEquals(mesaNumero, mesa.getMesaNumero());
        assertEquals(mesaCantidadPersonas, mesa.getMesaCantidadPersonas());
        assertEquals(mesaReferenciaUbicacion, mesa.getMesaReferenciaUbicacion());
        assertEquals(mesaActivo, mesa.getMesaActivo());
        assertEquals(mesaEstado, mesa.getMesaEstado());
    }
    @Test
    public void testPedidoConstructorAndGetters() {
        // Arrange
        String pediId = "P001";
        String pediMesaId = "M010";
        String pediClieId = "C100";
        String pediTipoPedido = "Dine-In";
        String pediFecha = "2024-06-22";
        String pediTotal = "120.50";

        // Act
        Pedido pedido = new Pedido(pediId, pediMesaId, pediClieId, pediTipoPedido, pediFecha, pediTotal);

        // Assert
        assertEquals(pediId, pedido.getPediId());
        assertEquals(pediMesaId, pedido.getPediMesaId());
        assertEquals(pediClieId, pedido.getPediClieId());
        assertEquals(pediTipoPedido, pedido.getPediTipoPedido());
        assertEquals(pediFecha, pedido.getPediFecha());
        assertEquals(pediTotal, pedido.getPediTotal());
    }
    @Test
    public void testProductoConstructorAndGetters() {
        // Arrange
        String prodId = "101";
        String prodTiprId = "500";
        String tiprNombre = "Bebida";
        String prodNombre = "Coca Cola";
        String prodDescripcion = "Refresco de cola";
        String prodStock = "150";
        Double prodPrecio = 1.50;
        String prodFoto = "imagen.jpg";

        // Act
        Producto producto = new Producto(prodId, prodTiprId, tiprNombre, prodNombre, prodDescripcion, prodStock, prodPrecio, prodFoto);

        // Assert
        assertEquals(prodId, producto.getProdId());
        assertEquals(prodTiprId, producto.getProdTiprId());
        assertEquals(tiprNombre, producto.getTiprNombre());
        assertEquals(prodNombre, producto.getProdNombre());
        assertEquals(prodDescripcion, producto.getProdDescripcion());
        assertEquals(prodStock, producto.getProdStock());
        assertEquals(prodPrecio, producto.getProdPrecio());
        assertEquals(prodFoto, producto.getProdFoto());
    }

    
}