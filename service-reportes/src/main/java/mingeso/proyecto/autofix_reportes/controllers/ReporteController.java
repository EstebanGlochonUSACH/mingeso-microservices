package mingeso.proyecto.autofix_reportes.controllers;

import java.util.List;

import mingeso.proyecto.autofix_reportes.dtos.ReparacionMesTipoSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mingeso.proyecto.autofix_reportes.dtos.ReparacionTipoSummary;
import mingeso.proyecto.autofix_reportes.services.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController
{
	@Autowired
	private ReporteService reporteService;

	@GetMapping("/tipo-auto")
	public ResponseEntity<List<ReparacionTipoSummary>> getReparacionTipoSummary(
		@RequestParam Integer year,
		@RequestParam Integer month
	) {
		List<ReparacionTipoSummary> summary = reporteService.getReparacionTipoSummary(year, month);
		return ResponseEntity.ok(summary);
	}

	@GetMapping("/tipo-mes")
	public ResponseEntity<List<ReparacionMesTipoSummary>> getReparacionMesTipoSummary(
		@RequestParam Integer year,
		@RequestParam Integer month
	) {
		List<ReparacionMesTipoSummary> summary = reporteService.getReparacionMesTipoSummary(year, month);
		return ResponseEntity.ok(summary);
	}
}
