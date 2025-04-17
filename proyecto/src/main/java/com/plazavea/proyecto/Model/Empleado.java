package com.plazavea.proyecto.Model;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleado_id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private String rol; 

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    
    @ManyToMany
    @JoinTable(name = "tb_emp_product",
    joinColumns = @JoinColumn(name = "empleado_id"),
    inverseJoinColumns = @JoinColumn(name="producto_id"))
    private Set<Producto> productos;

    @Column(name = "fecha", nullable = false)
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    public Empleado() {
    }

    

    



    public Empleado(Long id, String nombre, String apellido, String email, String password, String rol, boolean enabled,
            Set<Producto> productos, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.enabled = enabled;
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







    public String getApellido() {
        return apellido;
    }







    public void setApellido(String apellido) {
        this.apellido = apellido;
    }







    public String getEmail() {
        return email;
    }







    public void setEmail(String email) {
        this.email = email;
    }







    public String getPassword() {
        return password;
    }







    public void setPassword(String password) {
        this.password = password;
    }







    public String getRol() {
        return rol;
    }







    public void setRol(String rol) {
        this.rol = rol;
    }







    public boolean isEnabled() {
        return enabled;
    }







    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
