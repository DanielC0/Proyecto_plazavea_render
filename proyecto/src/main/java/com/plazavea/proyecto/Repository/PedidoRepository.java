package com.plazavea.proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plazavea.proyecto.Model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
