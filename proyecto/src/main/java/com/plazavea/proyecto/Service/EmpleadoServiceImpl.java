package com.plazavea.proyecto.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plazavea.proyecto.Model.Empleado;
import com.plazavea.proyecto.Repository.EmpleadoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    
    private final EmpleadoRepository empleadoRepository;

    @Override
    public void eliminarEmpleado(Long id) {

       if (empleadoRepository.existsById(id)) {
            empleadoRepository.deleteById(id);
       }
       else{
        throw new RuntimeException("Empleado no encontrado por id: " +  id);
       }

        
    }

    @Override
    public void guardarEmpleado(Empleado empleado) {
        empleadoRepository.save(empleado);
        
    }

    @Override
    public List<Empleado> listarEmpleado() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado por el id: " + id));
    }

}
