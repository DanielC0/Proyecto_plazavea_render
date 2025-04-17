package com.plazavea.proyecto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plazavea.proyecto.Model.Producto;
import com.plazavea.proyecto.Repository.ProductoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {


    private final ProductoRepository productoRepository;

    @Autowired
    private EmailService emailService;

    private static final int CANTIDAD_UMBRAL = 100;

    @Override
    @Transactional
    public void eliminarProducto(Long id) {

        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
        checkProductQuantity(producto);
        
    }


    private void checkProductQuantity(Producto producto) {
        if (producto.getCantidad() < CANTIDAD_UMBRAL) {
            emailService.sendAlert(
                "josuecarhuallanqui93@gmail.com", 
                "Alerta de cantidad baja",
                "La cantidad de " + producto.getNombre() + " es menor a " + CANTIDAD_UMBRAL + " hay que reabastecer." 
            );
        }
    }



    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado por el id: " + id));
    }

    @Override
    public List<Producto> listarProductosPorEmpleado(Long empleadoId) {
        return productoRepository.findByEmpleadoId(empleadoId);
    }


    @Override
    @Transactional
    public void cambiarEstadoProducto(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(!producto.isActivo()); 
        productoRepository.save(producto);
    }
    
}
