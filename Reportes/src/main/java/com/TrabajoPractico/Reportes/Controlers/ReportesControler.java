package com.TrabajoPractico.Reportes.Controlers;

import com.TrabajoPractico.Reportes.Servicies.PruebasXVehiculosSerivce;
import com.TrabajoPractico.Reportes.model.Pruebas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(path = "reportes")
public class ReportesControler {

    private final PruebasXVehiculosSerivce pruebasXVehiculosSerivce;

    @Autowired
    public ReportesControler(PruebasXVehiculosSerivce pruebasXVehiculosSerivce) {
        this.pruebasXVehiculosSerivce = pruebasXVehiculosSerivce;

    }

    @GetMapping(path = "/pruebasXVehiculo")
    public ResponseEntity<String> reporteDePruebasPorVehiculo(@RequestParam Long idVehiculo) throws IOException {
        List<Pruebas> pruebasConVehiculo = pruebasXVehiculosSerivce.encontrarPruebasConVehiculo(idVehiculo);

        FileWriter fw = new FileWriter("src\\main\\resources\\Reportes\\ArchivoPruebasXVehiculos.txt", false);
        StringBuilder sb = new StringBuilder("Archivo de información de pruebas para el vehiculo " + pruebasXVehiculosSerivce.findVehiculoById(idVehiculo).getPatente()).append("\n");
        sb.append("---------------------------------------------------------------").append("\n");
        fw.append(sb.toString());
        sb.delete(0, sb.length());

        Iterator<Pruebas> iterator = pruebasConVehiculo.iterator();
        while (iterator.hasNext()) {
            Pruebas prueba = iterator.next();
            sb.append("ID Prueba: ").append(prueba.getId()).append("\n");
            sb.append("Nombre de Empleado encargado de la prueba: ").append(prueba.getEmpleado().getNombre()).append("\n");
            sb.append("Nombre del interesado que solicitó la prueba: ").append(prueba.getInteresados().getNombre()).append("\n");
            sb.append("Fecha y hora de inicio de la prueba: ").append(prueba.getFecha_hora_inicio()).append("\n");
            sb.append("Fecha y hora de fin de la prueba: ");
            if (prueba.getFecha_hora_fin() != null) {
                sb.append(prueba.getFecha_hora_fin()).append("\n");
            }else{
                sb.append("La prueba se encuentra en curso").append("\n");
            }
            sb.append("Comentarios respecto a la prueba realizada: ").append(prueba.getComentarios()).append("\n");

            sb.append("------------------------------------------------------------------------------------------------------------------").append("\n");
            fw.append(sb.toString());
            sb.delete(0, sb.length());
        }

        fw.flush();

        return ResponseEntity.ok("El archivo fue creado correctamente en la carpeta del proyecto");
    }

}
