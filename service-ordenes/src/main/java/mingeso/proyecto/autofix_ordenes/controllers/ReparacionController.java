package mingeso.proyecto.autofix_ordenes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mingeso.proyecto.autofix_ordenes.dtos.ReparacionDTO;
import mingeso.proyecto.autofix_ordenes.entities.Orden;
import mingeso.proyecto.autofix_ordenes.entities.Reparacion;
import mingeso.proyecto.autofix_ordenes.services.ReparacionService;

@RestController
@RequestMapping("/reparaciones")
public class ReparacionController
{
	private final ReparacionService reparacionService;

	@Autowired
	public ReparacionController(ReparacionService reparacionService) {
		this.reparacionService = reparacionService;
	}

	@GetMapping
	public ResponseEntity<List<Reparacion>> getAllReparaciones() {
		List<Reparacion> reparaciones = reparacionService.getAllReparaciones();
		return ResponseEntity.ok(reparaciones);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reparacion> getReparacionById(@PathVariable Long id) {
		Reparacion reparacion = reparacionService.getReparacionById(id);
		if (reparacion != null) {
			return ResponseEntity.ok(reparacion);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/create")
	public ResponseEntity<Orden> createReparacion(@RequestBody ReparacionDTO reparacion) {
		try{
			Orden orden = reparacionService.createReparacion(reparacion);
			return ResponseEntity.status(HttpStatus.CREATED).body(orden);
		}
		catch(Exception err){
			err.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Reparacion> updateReparacion(@PathVariable Long id, @RequestBody Reparacion updatedReparacion) {
		Reparacion updatedEntity = reparacionService.updateReparacion(updatedReparacion);
		if (updatedEntity != null) {
			return ResponseEntity.ok(updatedEntity);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Orden> deleteReparacion(@PathVariable Long id) throws Exception {
		Orden orden = reparacionService.deleteReparacion(id);
		return ResponseEntity.ok(orden);
	}
}
