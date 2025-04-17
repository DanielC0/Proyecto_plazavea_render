package com.plazavea.proyecto.Controller;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plazavea.proyecto.Model.Detalle_Pedido;
import com.plazavea.proyecto.Model.Pedido;
import com.plazavea.proyecto.Model.Producto;
import com.plazavea.proyecto.Model.Proveedor;
import com.plazavea.proyecto.Repository.ProductoRepository;
import com.plazavea.proyecto.Service.EmpleadoService;
import com.plazavea.proyecto.Service.PedidoService;

import com.plazavea.proyecto.Service.ProveedorService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final EmpleadoService empleadoService;
    private final ProveedorService proveedorService;
    private final ProductoRepository productoRepository;

    @GetMapping("/listarPedidos")
    public String mostrarPedidos(Model model) {
        List<Pedido> listaPedidos = pedidoService.obtenerTodosLosPedidos();
        model.addAttribute("listaPedidos", listaPedidos);
        return "pedidos/pedidos";
    }

    @GetMapping("/nuevoPedido")
    public String mostrarFormularioNuevoPedido(Model model) {
        Pedido nuevoPedido = new Pedido();

        List<Detalle_Pedido> detalles = new ArrayList<>();
        detalles.add(new Detalle_Pedido());

        model.addAttribute("pedido", nuevoPedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("listaEmpleados", empleadoService.listarEmpleado());
        model.addAttribute("listaProveedores", proveedorService.listarProveedores());
        model.addAttribute("listaProductos", productoRepository.findAll());

        return "pedidos/nuevoPedido";
    }

    @GetMapping("/obtenerProductosPorProveedor/{proveedorId}")
    @ResponseBody
    public List<Producto> obtenerProductosPorProveedor(@PathVariable Long proveedorId) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorId(proveedorId);
        return new ArrayList<>(proveedor.getProductos());
    }

    @PostMapping("/crearPedido")
    public String crearPedido(@ModelAttribute Pedido pedido) {
        // Procesar cada detalle
        pedido.getDetalles().forEach(detalle -> {
            if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
                Producto productoExistente = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                detalle.setProducto(productoExistente);
                detalle.setProductoNombre(productoExistente.getNombre());
                detalle.setPrecioUnitario(productoExistente.getPrecio());
            }
            detalle.setPedido(pedido);
            detalle.calcularSubtotal();
        });

        // Calcular total y guardar
        BigDecimal total = pedido.getDetalles().stream()
                .map(Detalle_Pedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotalPedido(total);

        pedidoService.crearPedido(pedido, pedido.getDetalles());

        return "redirect:/pedido/listarPedidos";
    }

    @GetMapping("/actualizarPedido/{id}")
    public String actualizarPedido(@PathVariable Long id, @ModelAttribute Pedido pedido, Model model) {
        try {
            // Obtener el pedido existente con todos sus detalles
            Pedido pedidoExistente = pedidoService.obtenerPedidoPorId(id);


            // Actualizar solo los campos básicos
            pedidoExistente.setEmpleado(pedido.getEmpleado());
            pedidoExistente.setProveedor(pedido.getProveedor());
            pedidoExistente.setFechaPedido(pedido.getFechaPedido());

            // Para cada detalle, asegurarse de cargar el producto completo
            pedido.getDetalles().forEach(detalle -> {
                if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
                    Producto productoCompleto = productoRepository.findById(detalle.getProducto().getId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                    detalle.setProducto(productoCompleto);
                }
                detalle.setPedido(pedidoExistente);
                detalle.calcularSubtotal();
            });

            // Actualizar los detalles
            pedidoExistente.setDetalles(pedido.getDetalles());

            pedidoService.actualizarPedido(id, pedidoExistente);
            return "redirect:/pedido/listarPedidos";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el pedido: " + e.getMessage());
            model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(id));
            model.addAttribute("listaEmpleados", empleadoService.listarEmpleado());
            model.addAttribute("listaProveedores", proveedorService.listarProveedores());
            model.addAttribute("listaProductos", productoRepository.findAll());
            return "pedidos/actualizarPedido";
        }
    }

    @PostMapping("/actualizarPedido/{id}")
    public String actualizarPedido(@PathVariable Long id, @ModelAttribute Pedido pedido) {
        pedido.getDetalles().forEach(detalle -> {
            detalle.setPedido(pedido);
            detalle.calcularSubtotal();
        });

        pedidoService.actualizarPedido(id, pedido);
        return "redirect:/pedido/listarPedidos";
    }

    @GetMapping("/eliminarPedido/{id}")
    public String eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return "redirect:/pedido/listarPedidos";
    }

}