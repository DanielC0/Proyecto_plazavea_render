package com.plazavea.proyecto.Security;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.plazavea.proyecto.Model.Empleado;
import com.plazavea.proyecto.Repository.EmpleadoRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(EmpleadoRepository empleadoRepository, PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear administrador inicial si no existe
        if (empleadoRepository.findByEmail("admin@plazavea.com").isEmpty()) {
            Empleado admin = new Empleado();
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setEmail("admin@plazavea.com");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRol("ADMINISTRADOR");
            admin.setEnabled(true);
            admin.setFecha(new Date()); 
            // Establece otros campos necesarios
            empleadoRepository.save(admin);
        }
    }

    
}
