package mingeso.proyecto.autofix_reportes.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mingeso.proyecto.autofix_reportes.dtos.AvgRepairTimeByMarcaDTO;
import mingeso.proyecto.autofix_reportes.dtos.ReparacionMotorSummary;
import mingeso.proyecto.autofix_reportes.dtos.ReparacionTipoSummary;
import mingeso.proyecto.autofix_reportes.services.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController
{
	@Autowired
	private ReporteService reporteService;

	@GetMapping("/tipo-auto")
	public ResponseEntity<List<ReparacionTipoSummary>> getReparacionSummary() {
		List<ReparacionTipoSummary> summary = reporteService.getReparacionTipoSummary();
		return ResponseEntity.ok(summary);
	}

	@GetMapping("/tipo-motor")
	public ResponseEntity<List<ReparacionMotorSummary>> getReparacionMotorSummary() {
		List<ReparacionMotorSummary> summary = reporteService.getReparacionMotorSummary();
		return ResponseEntity.ok(summary);
	}

	@GetMapping("/tiempo-reparacion")
	public ResponseEntity<List<AvgRepairTimeByMarcaDTO>> getAvgRepairTimeByMarca() {
		List<AvgRepairTimeByMarcaDTO> avgRepairTimeByMarca = reporteService.getAvgRepairTimeByMarca();
		return ResponseEntity.ok(avgRepairTimeByMarca);
	}
}
