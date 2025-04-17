package com.plazavea.proyecto.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plazavea.proyecto.Model.Proveedor;
import com.plazavea.proyecto.Repository.ProveedorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;


    @Override
    public void eliminarProveedor(Long id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Proveedor no encontrado por el id: " + id);
        }
    }

    @Override
    public void guardarProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
        
    }

    @Override
    public List<Proveedor> listarProveedores() {
        
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor obtenerProveedorPorId(Long id) {
       
        return proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado por el id: " + id));
    }

    
    
}
