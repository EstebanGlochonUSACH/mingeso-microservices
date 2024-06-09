package mingeso.proyecto.autofix_ordenes.controllers;

import java.time.LocalDateTime;
import java.util.List;

import mingeso.proyecto.autofix_ordenes.clients.MarcasFeignClient;
import mingeso.proyecto.autofix_ordenes.dtos.BonoGroupedByFechaInicioDTO;
import mingeso.proyecto.autofix_ordenes.dtos.MarcaDTO;
import mingeso.proyecto.autofix_ordenes.entities.Bono;
import mingeso.proyecto.autofix_ordenes.services.BonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bonos")
public class BonoController
{
	private final BonoService bonoService;
	private final MarcasFeignClient marcasClient;

	@Autowired
	public BonoController(
		BonoService bonoService,
		MarcasFeignClient marcasClient
	) {
		this.bonoService = bonoService;
		this.marcasClient = marcasClient;
	}

	@GetMapping
	public ResponseEntity<List<Bono>> getAllBonos(
        @RequestParam(required = false) Long marcaId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha
	) {
		if(marcaId != null){
			List<Bono> bonos = bonoService.getFilteredBono(marcaId, fecha);
			return ResponseEntity.ok(bonos);
		}
		else{
			List<Bono> bonos = bonoService.getAllBonos();
			return ResponseEntity.ok(bonos);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Bono> getBono(@PathVariable Long id) {
		Bono updatedBono = bonoService.getBono(id);
		if (updatedBono != null) {
			return ResponseEntity.ok(updatedBono);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/grouped-by-fecha-inicio")
    public List<BonoGroupedByFechaInicioDTO> getBonosGroupedByFechaInicio() {
        return bonoService.getAllBonosByGroup();
    }

	@PostMapping("/create")
	@Transactional
	public ResponseEntity<List<Bono>> createBono(@RequestParam Long marcaId, @RequestParam Integer monto, @RequestParam Integer cantidad) {
		MarcaDTO marca = marcasClient.getMarcaById(marcaId);
		if (marca != null) {
			List<Bono> bonos = bonoService.createBonos(marcaId, monto, cantidad, null);
			return ResponseEntity.status(HttpStatus.CREATED).body(bonos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}/update")
	public ResponseEntity<Bono> updateBono(@PathVariable Long id, @RequestParam Boolean usado) {
		Bono updatedBono = bonoService.updateBono(id, usado);
		if (updatedBono != null) {
			return ResponseEntity.ok(updatedBono);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
