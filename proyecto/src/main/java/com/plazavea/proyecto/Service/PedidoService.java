package com.plazavea.proyecto.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plazavea.proyecto.Model.Detalle_Pedido;
import com.plazavea.proyecto.Model.Pedido;
import com.plazavea.proyecto.Model.Producto;
import com.plazavea.proyecto.Repository.PedidoRepository;
import com.plazavea.proyecto.Repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;



    public Pedido crearPedido(Pedido pedido, List<Detalle_Pedido> detalles) {

        detalles.forEach(detalle -> {
            if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
                Producto producto = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                detalle.setProducto(producto);
                detalle.setProductoNombre(producto.getNombre());

                producto.setCantidad(producto.getCantidad() + detalle.getCantidad());
                productoRepository.save(producto);

            }
            detalle.setPedido(pedido);
            detalle.calcularSubtotal();
        });

        // Calcular total
        BigDecimal total = detalles.stream()
                .map(Detalle_Pedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotalPedido(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    ////////////////////////////////////////////

    public void actualizarPedido(Long id, Pedido pedidoActualizado) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Actualizar propiedades básicas
        pedidoExistente.setEmpleado(pedidoActualizado.getEmpleado());
        pedidoExistente.setProveedor(pedidoActualizado.getProveedor());
        pedidoExistente.setFechaPedido(pedidoActualizado.getFechaPedido());

        // Manejar detalles
        manejarDetallesPedido(pedidoExistente, pedidoActualizado.getDetalles());

        // Calcular total
        calcularTotalPedido(pedidoExistente);

        pedidoRepository.save(pedidoExistente);
    }

    private void manejarDetallesPedido(Pedido pedido, List<Detalle_Pedido> detallesActualizados) {
        // Primero, revertir las cantidades de los detalles originales
        pedido.getDetalles().forEach(detalle -> {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            producto.setCantidad(producto.getCantidad() - detalle.getCantidad());
            productoRepository.save(producto);
        });
    
        // Limpiar los detalles existentes
        pedido.getDetalles().clear();
    
        // Procesar cada detalle actualizado
        for (Detalle_Pedido detalle : detallesActualizados) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            // Actualizar la cantidad del producto
            producto.setCantidad(producto.getCantidad() + detalle.getCantidad());
            productoRepository.save(producto);
    
            // Configurar el detalle
            detalle.setProducto(producto);
            detalle.setProductoNombre(producto.getNombre());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setPedido(pedido);
            detalle.calcularSubtotal();
    
            // Añadir el detalle al pedido
            pedido.getDetalles().add(detalle);
        }
    }

    private void calcularTotalPedido(Pedido pedido) {
        BigDecimal total = pedido.getDetalles().stream()
                .map(Detalle_Pedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotalPedido(total);
    }

    /////////////////////////////////////////////

    @Transactional
    public void eliminarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.getDetalles().forEach(detalle -> {
            Producto producto = detalle.getProducto();
            producto.setCantidad(producto.getCantidad() - detalle.getCantidad());
            productoRepository.save(producto);
        });

        pedidoRepository.delete(pedido);
    }

}
