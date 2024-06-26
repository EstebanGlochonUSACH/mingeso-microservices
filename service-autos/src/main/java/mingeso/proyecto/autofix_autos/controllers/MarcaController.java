package mingeso.proyecto.autofix_autos.controllers;

import java.util.List;

import mingeso.proyecto.autofix_autos.services.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mingeso.proyecto.autofix_autos.dtos.MarcaDTO;
import mingeso.proyecto.autofix_autos.entities.Marca;

@RestController
@RequestMapping("/marcas")
public class MarcaController
{
	private final MarcaService marcaService;

	@Autowired
	public MarcaController(MarcaService marcaService) {
		this.marcaService = marcaService;
	}

	@GetMapping
	public ResponseEntity<List<MarcaDTO>> getAllMarcas() {
		List<MarcaDTO> marcas = marcaService.getAllMarcas();
		return ResponseEntity.ok(marcas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MarcaDTO> getMarcaById(@PathVariable Long id) {
		Marca marca = marcaService.getMarcaById(id);
		if (marca != null) {
			return ResponseEntity.ok(new MarcaDTO(marca.getId(), marca.getNombre(), 0L));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/create")
	public ResponseEntity<MarcaDTO> createMarca(@RequestBody MarcaDTO marcaDTO) {
		String nombre = marcaDTO.getNombre(); 
		Marca marca = marcaService.createMarca(nombre);
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new MarcaDTO(marca.getId(), marca.getNombre(), 0L)
		);
	}

	@PutMapping("/{id}/update")
	public ResponseEntity<MarcaDTO> updateMarca(@PathVariable Long id, @RequestParam String nombre) {
		Marca updatedMarca = marcaService.updateMarca(id, nombre);
		if (updatedMarca != null) {
			return ResponseEntity.ok(
				new MarcaDTO(updatedMarca.getId(), updatedMarca.getNombre(), 0L)
			);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
		marcaService.deleteMarca(id);
		return ResponseEntity.noContent().build();
	}
}
