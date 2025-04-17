package com.plazavea.proyecto.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoId;

    @Column
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id")
    private Proveedor proveedor;

    @Column
    private BigDecimal totalPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Detalle_Pedido> detalles = new ArrayList<>();

    public void agregarDetalle(Detalle_Pedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    public Pedido(){}

    public Pedido(Long pedidoId, LocalDate fechaPedido, Empleado empleado, Proveedor proveedor,
            BigDecimal totalPedido) {
        this.pedidoId = pedidoId;
        this.fechaPedido = fechaPedido;
        this.empleado = empleado;
        this.proveedor = proveedor;
        this.totalPedido = totalPedido;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<Detalle_Pedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle_Pedido> detalles) {
        this.detalles = detalles;
    }

    

    
    
}
