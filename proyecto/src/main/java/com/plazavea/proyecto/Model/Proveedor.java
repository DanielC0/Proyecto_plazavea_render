package com.plazavea.proyecto.Model;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "categoria_producto", nullable = false)
    private String categoriaProducto;



    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private Set<Producto> productos;


    public void agregarProducto(Producto producto) {
        productos.add(producto);
        producto.setProveedor(this);
    }


    @Column(name = "fecha", nullable = false)
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;


    public Proveedor() {
    }


    public Proveedor(Long id, String nombre, String telefono, String categoriaProducto,
            Set<Producto> productos, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.categoriaProducto = categoriaProducto;
        this.productos = productos;
        this.fecha = fecha;
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


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getCategoriaProducto() {
        return categoriaProducto;
    }


    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }




    public Set<Producto> getProductos() {
        return productos;
    }


    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }


    public Date getFecha() {
        return fecha;
    }


    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
