package com.plazavea.proyecto.Controller;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.plazavea.proyecto.Model.Empleado;
import com.plazavea.proyecto.Service.EmpleadoService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/empleado")
@AllArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/listarEmpleados")
    public String verPaginaInicio(Model model){
        model.addAttribute("listaEmpleados", empleadoService.listarEmpleado());
        return "empleados/empleados";
    }

    @GetMapping("/nuevoEmpleado")
    public String nuevoEmpleado(Model model){
        Empleado empleado = new Empleado();
        empleado.setEnabled(true); // Por defecto activo
        empleado.setRol("ALMACEN");
        empleado.setFecha(new Date()); // Rol por defecto
        model.addAttribute("empleado", empleado);
        return "empleados/nuevoEmpleado";
    }


    @PostMapping("/guardarEmpleado")
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado){
        // Si es nuevo empleado o se cambió la contraseña
        if(empleado.getId() == null || !empleado.getPassword().isEmpty()) {
            empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
        } else if(empleado.getId() != null) {
            // Mantener la contraseña existente si no se cambió
            Empleado empleadoExistente = empleadoService.obtenerEmpleadoPorId(empleado.getId());
            empleado.setPassword(empleadoExistente.getPassword());
        }
        
        empleadoService.guardarEmpleado(empleado);
        return "redirect:/empleado/listarEmpleados";
    }


    @GetMapping("/actualizarEmpleado/{id}")
    public String actualizarEmpleado(@PathVariable("id") Long id, Model model){
        model.addAttribute("empleado", empleadoService.obtenerEmpleadoPorId(id));
        return "empleados/actualizarEmpleado";

    }

    @GetMapping("/eliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable("id")Long id){
        empleadoService.eliminarEmpleado(id);
        return "redirect:/empleado/listarEmpleados";
    }

    



    
}
