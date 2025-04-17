package com.plazavea.proyecto.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/inicio")
    public String Menu(){
        return "Inicio";
    }
    
}
