package com.TrabajoPractico.Reportes.Controlers;

import com.TrabajoPractico.Reportes.Servicies.IncidentesService;
import com.TrabajoPractico.Reportes.Servicies.IncidentesXEmpleadoService;
import com.TrabajoPractico.Reportes.Servicies.PruebasXVehiculosSerivce;
import com.TrabajoPractico.Reportes.model.Empleados;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "reportes")
public class ReportesControler {

    private final PruebasXVehiculosSerivce pruebasXVehiculosSerivce;
    private final IncidentesService incidentesService;
    private final IncidentesXEmpleadoService incidentesXEmpleadoService;

    @Autowired
    public ReportesControler(PruebasXVehiculosSerivce pruebasXVehiculosSerivce, IncidentesService incidentesService, IncidentesXEmpleadoService incidentesXEmpleadoService) {
        this.pruebasXVehiculosSerivce = pruebasXVehiculosSerivce;
        this.incidentesService = incidentesService;
        this.incidentesXEmpleadoService = incidentesXEmpleadoService;

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

    @GetMapping(path = "incidentes")
    public ResponseEntity<String> incidentes() throws IOException {
        List<Pruebas> pruebasConIncidentes = incidentesService.getPruebasConInicidentes();

        FileWriter fw = new FileWriter("src\\main\\resources\\Reportes\\Incidentes.txt", false);
        StringBuilder sb = new StringBuilder("Archivo de pruebas con incidentes").append("\n");
        sb.append("---------------------------------------------------------------").append("\n");
        for (Pruebas prueba : pruebasConIncidentes) {
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

    @GetMapping(path = "incidentesXEmpleado")
    public ResponseEntity<String> incidentesXEmpleado(@RequestParam long legajoEmpleado) throws IOException, NoSuchElementException {
        try{


        List<Pruebas> pruebasConIncidentesXEmpleado = incidentesXEmpleadoService.getPruebasConIncidentesConEmpleado(legajoEmpleado);

        FileWriter fw = new FileWriter("src\\main\\resources\\Reportes\\Incidentes.txt", false);
        StringBuilder sb = new StringBuilder("Archivo de pruebas con incidentes de Empleado").append("\n");
        sb.append("---------------------------------------------------------------").append("\n");

        Empleados empleadoEnRevision = pruebasConIncidentesXEmpleado.get(0).getEmpleado();

        sb.append("Legajo Empleado: ").append(empleadoEnRevision.getLegajo()).append("\n");
        sb.append("Nombre Empleado: ").append(empleadoEnRevision.getNombre()).append("\n");
        sb.append("Apellido Empleado: ").append(empleadoEnRevision.getApellido()).append("\n");
        sb.append("Telefono Empleado: ").append(empleadoEnRevision.getTelefono_contacto()).append("\n");

        sb.append("---------------------------------------------------------------").append("\n");

        fw.append(sb.toString());
        sb.delete(0, sb.length());

        for (Pruebas prueba : pruebasConIncidentesXEmpleado) {
            sb.append("ID Prueba: ").append(prueba.getId()).append("\n");
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
        catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
