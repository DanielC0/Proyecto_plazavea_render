package com.plazavea.proyecto.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.plazavea.proyecto.Model.Proveedor;
import com.plazavea.proyecto.Service.ProveedorService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/proveedor")
@AllArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;


    @GetMapping("/listarProveedores")
    public String verPaginaInicio(Model model){
        model.addAttribute("listaProveedores", proveedorService.listarProveedores());
        return "proveedores/proveedores";
    }

    @GetMapping("/nuevoProveedor")
    public String nuevoProveedor(Model model){
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/nuevoProveedor";
    }


    @PostMapping("/guardarProveedor")
    public String guardarProveedor(@ModelAttribute("proveedor")Proveedor proveedor){
        proveedorService.guardarProveedor(proveedor);
        return "redirect:/proveedor/listarProveedores";
    }

    @GetMapping("/actualizarProveedor/{id}")
    public String actualizarProveedor(@PathVariable("id") Long id, Model model){
        model.addAttribute("proveedor", proveedorService.obtenerProveedorPorId(id));
        return "proveedores/actualizarProveedor";
    }

    @GetMapping("/eliminarProveedor/{id}")
    public String eliminarProveedor(@PathVariable("id")Long id){
        proveedorService.eliminarProveedor(id);
        return "redirect:/proveedor/listarProveedores";
    }


    
    
}
