package mingeso.proyecto.autofix_ordenes.controllers;

import mingeso.proyecto.autofix_ordenes.clients.AutosFeignClient;
import mingeso.proyecto.autofix_ordenes.dtos.AutoDTO;
import mingeso.proyecto.autofix_ordenes.dtos.OrdenDTO;
import mingeso.proyecto.autofix_ordenes.entities.Orden;
import mingeso.proyecto.autofix_ordenes.models.ResponseObject;
import mingeso.proyecto.autofix_ordenes.services.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenController
{
	private final OrdenService ordenService;
	private final AutosFeignClient autosClient;

	@Autowired
	public OrdenController(OrdenService ordenService, AutosFeignClient autosClient) {
		this.ordenService = ordenService;
		this.autosClient = autosClient;
	}

	@GetMapping
	public ResponseEntity<Page<Orden>> getPagedOrdenes(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "100") int limit,
		@RequestParam(required = false) Long auto
	) {
		Pageable pageable = PageRequest.of(page, limit);
		if(auto != null){
			Page<Orden> ordenes = ordenService.getPagedOrdenes(pageable, auto);
			return ResponseEntity.ok(ordenes);
		}
		else{
			Page<Orden> ordenes = ordenService.getPagedOrdenes(pageable, null);
			return ResponseEntity.ok(ordenes);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<OrdenDTO>> getAllOrdenes() {
		List<Orden> ordenes = ordenService.getAllOrdenes();
		List<OrdenDTO> ordenes2 = new ArrayList<>();

		for(Orden orden : ordenes){
			AutoDTO auto = autosClient.getAutoById(orden.getAuto());
			if(auto == null) continue;
			OrdenDTO dto = new OrdenDTO();
			dto.setId(orden.getId());
			dto.setAuto(auto);
			dto.setReparaciones(orden.getReparaciones());
			dto.setValorIva(orden.getValorIva());
			dto.setMontoTotal(orden.getMontoTotal());
			dto.setFechaIngreso(orden.getFechaIngreso());
			dto.setFechaSalida(orden.getFechaSalida());
			dto.setFechaEntrega(orden.getFechaEntrega());
			ordenes2.add(dto);
		}

		return ResponseEntity.ok(ordenes2);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Orden> getOrdenById(@PathVariable Long id) {
		Orden orden = ordenService.getOrdenById(id);
		if (orden != null) {
			return ResponseEntity.ok(orden);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseObject<Orden>> createOrden(@RequestBody Orden orden) {
		try{
			Orden createdOrden = ordenService.createOrden(orden);
			ResponseObject<Orden> response = new ResponseObject<Orden>(null, createdOrden);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		catch(Exception err){
			ResponseObject<Orden> response = new ResponseObject<Orden>(err.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject<Orden>> updateOrden(@PathVariable Long id, @RequestBody Orden updatedOrden) {
		try{
			Integer totalReparaciones = updatedOrden.getReparaciones().size();
			Orden updatedEntity = ordenService.updateOrden(updatedOrden, totalReparaciones);
			if (updatedEntity != null) {
				ResponseObject<Orden> response = new ResponseObject<Orden>(null, updatedEntity);
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		catch(Exception err){
			err.printStackTrace();
			ResponseObject<Orden> response = new ResponseObject<Orden>(err.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
		ordenService.deleteOrden(id);
		return ResponseEntity.noContent().build();
	}
}
