package com.plazavea.proyecto.Model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "productos")
public class Producto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "cantidad",nullable = false)
    private int cantidad;

    @Column(name = "categoria", nullable = false)
    private String categoria;


    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;


    @ManyToOne
    @JoinColumn(name = "proveedor_id",nullable = false)
    private Proveedor proveedor;


    @Column(name = "fecha_entrada", nullable = false)
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaentrada;


    @Column(name = "fecha_vencimiento", nullable = false)
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechavencimiento;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;


    public Producto() {
    }


    public Producto(Long id, String nombre, BigDecimal precio, int cantidad, String categoria, Empleado empleado,
            Proveedor proveedor, Date fechaentrada, Date fechavencimiento) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.empleado = empleado;
        this.proveedor = proveedor;
        this.fechaentrada = fechaentrada;
        this.fechavencimiento = fechavencimiento;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public BigDecimal getPrecio() {
        return precio;
    }


    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }


    public int getCantidad() {
        return cantidad;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public String getCategoria() {
        return categoria;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
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


    public Date getFechaentrada() {
        return fechaentrada;
    }


    public void setFechaentrada(Date fechaentrada) {
        this.fechaentrada = fechaentrada;
    }


    public Date getFechavencimiento() {
        return fechavencimiento;
    }


    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }


    public boolean isActivo() {
        return activo;
    }


    public void setActivo(boolean activo) {
        this.activo = activo;
    }    

    
}
