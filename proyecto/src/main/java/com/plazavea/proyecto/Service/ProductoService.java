package com.plazavea.proyecto.Service;

import java.util.List;

import com.plazavea.proyecto.Model.Producto;

public interface ProductoService {

    List<Producto> listarProductos();
    void guardarProducto(Producto producto);
    Producto obtenerProductoPorId(Long id);
    void eliminarProducto(Long id);
    List<Producto> listarProductosPorEmpleado(Long empleadoId);
    void cambiarEstadoProducto(Long id);
    
}
