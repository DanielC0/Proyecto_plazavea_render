package com.plazavea.proyecto.Service;

import java.util.List;

import com.plazavea.proyecto.Model.Proveedor;



public interface ProveedorService{

    List<Proveedor> listarProveedores();
    void guardarProveedor(Proveedor proveedor);
    Proveedor obtenerProveedorPorId(Long id);
    void eliminarProveedor(Long id);
    
}
