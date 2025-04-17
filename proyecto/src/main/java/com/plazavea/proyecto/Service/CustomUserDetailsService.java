package com.plazavea.proyecto.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.plazavea.proyecto.Model.Empleado;
import com.plazavea.proyecto.Repository.EmpleadoRepository;
import com.plazavea.proyecto.Security.EmpleadoUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   private final EmpleadoRepository empleadoRepository;

    public CustomUserDetailsService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado con email: " + email));
        
        return new EmpleadoUserDetails(empleado);
    }
    
}
