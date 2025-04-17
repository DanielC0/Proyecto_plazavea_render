package com.plazavea.proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plazavea.proyecto.Model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Long> {
    
}
