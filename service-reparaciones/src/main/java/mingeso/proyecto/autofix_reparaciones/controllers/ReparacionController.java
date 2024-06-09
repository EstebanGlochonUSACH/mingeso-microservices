package mingeso.proyecto.autofix_reparaciones.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mingeso.proyecto.autofix_reparaciones.entities.Reparacion;
import mingeso.proyecto.autofix_reparaciones.services.ReparacionService;

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
	public ResponseEntity<Reparacion> getReparacion(@PathVariable Long id) {
		Reparacion reparacion = reparacionService.getReparacionById(id);
		if(reparacion == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reparacion);
	}

	@PostMapping("/create")
	public ResponseEntity<Reparacion> createReparacion(@RequestBody Reparacion reparacion) {
		try{
			reparacion = reparacionService.createReparacion(reparacion);
			return ResponseEntity.status(HttpStatus.CREATED).body(reparacion);
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
	public ResponseEntity<String> deleteReparacion(@PathVariable Long id) {
		Reparacion rep = reparacionService.deleteReparacion(id);
		if(rep == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok("OK");
	}
}
