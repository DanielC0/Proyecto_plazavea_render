package com.plazavea.proyecto.Service;

import java.util.List;

import com.plazavea.proyecto.Model.Empleado;

public interface EmpleadoService {


    List<Empleado> listarEmpleado();
    void guardarEmpleado(Empleado empleado);
    Empleado obtenerEmpleadoPorId(Long id);
    void eliminarEmpleado(Long id);

    
}
