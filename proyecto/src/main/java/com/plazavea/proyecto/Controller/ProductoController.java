package com.plazavea.proyecto.Controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.plazavea.proyecto.Model.Empleado;
import com.plazavea.proyecto.Model.Producto;
import com.plazavea.proyecto.Service.EmpleadoService;
import com.plazavea.proyecto.Service.ProductoService;
import com.plazavea.proyecto.Service.ProveedorService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/producto")
@AllArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final EmpleadoService empleadoService;
    private final ProveedorService proveedorService;

   


    @GetMapping("/listarProductos")
    public String verPaginaInicio(Model model){
        model.addAttribute("listaProductos", productoService.listarProductos());
        return "productos/productos";
    }

    @GetMapping("/nuevoProducto")
    public String nuevoProducto(Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaEmpleados", empleadoService.listarEmpleado());
        model.addAttribute("listaProveedores", proveedorService.listarProveedores());
        return "productos/nuevoProducto";
    }

    @PostMapping("/guardarProducto")
    public String guardarProducto(@ModelAttribute("producto") Producto producto){
        productoService.guardarProducto(producto);
        return "redirect:/producto/listarProductos";
    }


    @GetMapping("/actualizarProducto/{id}")
    public String actualizarProducto(@PathVariable("id") Long id, Model model){
        model.addAttribute("producto", productoService.obtenerProductoPorId(id));
        model.addAttribute("listaEmpleados", empleadoService.listarEmpleado());
        model.addAttribute("listaProveedores", proveedorService.listarProveedores());
        return "productos/actualizarProducto";
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable("id") Long id){
        productoService.eliminarProducto(id);
        return "redirect:/producto/listarProductos";
    }


    @GetMapping("/empleado")
    public String listarProductosPorEmpleado(@RequestParam("empleadoId") Long empleadoId, Model model) {
        List<Producto> productos = productoService.listarProductosPorEmpleado(empleadoId);
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(empleadoId);

        model.addAttribute("productos", productos);
        model.addAttribute("empleadoNombre", empleado.getNombre() + " " + empleado.getApellido());
        return "productos/productosPorEmpleado"; 
    }

    @GetMapping("/listarPorEmpleado")
        public String listarPorEmpleado(Model model) {
        List<Empleado> empleados = empleadoService.listarEmpleado(); 
        model.addAttribute("empleados", empleados);
        return "productos/listarProductosPorEmpleado"; 
    }


    //EXPORTAR A EXCEL METODO


    @GetMapping("/exportarExcel")
    public void exportarAExcel(HttpServletResponse response) throws IOException {
            List<Producto> productos = productoService.listarProductos();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Productos");

            // Crear un estilo de celda para las fechas
            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nombre");
            headerRow.createCell(2).setCellValue("Precio");
            headerRow.createCell(3).setCellValue("Cantidad");
            headerRow.createCell(4).setCellValue("Categoria");
            headerRow.createCell(5).setCellValue("Fecha de Entrada");
            headerRow.createCell(6).setCellValue("Fecha de vencimiento");

            // Llenado de datos
            int rowNum = 1;
            for (Producto producto : productos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(producto.getId());
                row.createCell(1).setCellValue(producto.getNombre());
                row.createCell(2).setCellValue(producto.getPrecio().doubleValue());
                row.createCell(3).setCellValue(producto.getCantidad());
                row.createCell(4).setCellValue(producto.getCategoria());
                // Aplicar el estilo de celda a las fechas
                if (producto.getFechaentrada() != null) {
                    Cell fechaEntradaCell = row.createCell(5);
                    fechaEntradaCell.setCellValue(producto.getFechaentrada()); 
                    fechaEntradaCell.setCellStyle(dateCellStyle);
                }

                if (producto.getFechavencimiento() != null) {
                    Cell fechaVencimientoCell = row.createCell(6);
                    fechaVencimientoCell.setCellValue(producto.getFechavencimiento()); 
                    fechaVencimientoCell.setCellStyle(dateCellStyle);
                }
            }

            // Configurar la respuesta
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");

            // respuesta
            workbook.write(response.getOutputStream());
            workbook.close();
    }


    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstadoProducto(@PathVariable("id") Long id) {
        productoService.cambiarEstadoProducto(id);
        return "redirect:/producto/listarProductos";
    }

    
}
