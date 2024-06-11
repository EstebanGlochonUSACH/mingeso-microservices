package mingeso.proyecto.autofix_ordenes.controllers;

import mingeso.proyecto.autofix_ordenes.clients.AutosFeignClient;
import mingeso.proyecto.autofix_ordenes.dtos.AutoDTO;
import mingeso.proyecto.autofix_ordenes.dtos.OrdenDTO;
import mingeso.proyecto.autofix_ordenes.dtos.ReparacionDTO;
import mingeso.proyecto.autofix_ordenes.entities.Orden;
import mingeso.proyecto.autofix_ordenes.entities.Reparacion;
import mingeso.proyecto.autofix_ordenes.models.ResponseObject;
import mingeso.proyecto.autofix_ordenes.services.OrdenService;
import mingeso.proyecto.autofix_ordenes.services.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenController
{
	private final OrdenService ordenService;
	private final ReparacionService reparacionService;
	private final AutosFeignClient autosClient;

	@Autowired
	public OrdenController(
		OrdenService ordenService,
		ReparacionService reparacionService,
		AutosFeignClient autosClient
	) {
		this.ordenService = ordenService;
		this.reparacionService = reparacionService;
		this.autosClient = autosClient;
	}

	private OrdenDTO convertOrdenToDTO(Orden orden) throws Exception {
		AutoDTO auto = autosClient.getAutoById(orden.getId_auto());
		if(auto == null){
			throw new Exception("El Orden.auto no existe!");
		}
		OrdenDTO dto = new OrdenDTO();
		dto.setId(orden.getId());
		dto.setAuto(auto);
		dto.setBono(orden.getBono());
		dto.setReparaciones(orden.getReparaciones());
		dto.setDescuentoDiaAtencion(orden.getDescuentoDiaAtencion());
		dto.setDescuentoReparaciones(orden.getDescuentoReparaciones());
		dto.setRecargaAntiguedad(orden.getRecargaAntiguedad());
		dto.setRecargaAtraso(orden.getRecargaAtraso());
		dto.setRecargaKilometraje(orden.getRecargaKilometraje());
		dto.setMontoReparaciones(orden.getMontoReparaciones());
		dto.setMontoTotal(orden.getMontoTotal());
		dto.setValorIva(orden.getValorIva());
		dto.setFechaIngreso(orden.getFechaIngreso());
		dto.setFechaSalida(orden.getFechaSalida());
		dto.setFechaEntrega(orden.getFechaEntrega());
		return dto;
	}

	@GetMapping
	public ResponseEntity<Page<OrdenDTO>> getPagedOrdenes(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "100") int limit,
		@RequestParam(required = false) Long auto
	) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Orden> ordenes = ordenService.getPagedOrdenes(pageable);
		Page<OrdenDTO> ordenesDTO = ordenes.map(orden -> {
			try { return convertOrdenToDTO(orden); }
			catch (Exception err){ return null; }
		});
		return ResponseEntity.ok(ordenesDTO);

		// if(auto != null){
		// 	Page<Orden> ordenes = ordenService.getOrdenesByAuto(auto);
		// 	return ResponseEntity.ok(ordenes);
		// }
	}

	@GetMapping("/all")
	public ResponseEntity<List<OrdenDTO>> getAllOrdenes() throws Exception {
		List<Orden> ordenes = ordenService.getAllOrdenes();
		List<OrdenDTO> ordenes2 = new ArrayList<>();

		OrdenDTO dto;
		for(Orden orden : ordenes){
			dto = convertOrdenToDTO(orden);
			ordenes2.add(dto);
		}

		return ResponseEntity.ok(ordenes2);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrdenDTO> getOrdenById(@PathVariable Long id) throws Exception {
		Orden orden = ordenService.getOrdenById(id);
		if (orden != null) {
			OrdenDTO dto = convertOrdenToDTO(orden);
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseObject<OrdenDTO>> createOrden(@RequestBody OrdenDTO orden) {
		try{
			Orden createdOrden = ordenService.createOrden(orden);
			OrdenDTO dto = convertOrdenToDTO(createdOrden);
			ResponseObject<OrdenDTO> response = new ResponseObject<OrdenDTO>(null, dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		catch(Exception err){
			ResponseObject<OrdenDTO> response = new ResponseObject<OrdenDTO>(err.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject<OrdenDTO>> updateOrden(@PathVariable Long id, @RequestBody Orden updatedOrden) {
		try{
			Integer totalReparaciones = updatedOrden.getReparaciones().size();
			Orden updatedEntity = ordenService.updateOrden(updatedOrden, totalReparaciones);
			if (updatedEntity != null) {
				OrdenDTO dto = convertOrdenToDTO(updatedEntity);
				ResponseObject<OrdenDTO> response = new ResponseObject<OrdenDTO>(null, dto);
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		catch(Exception err){
			err.printStackTrace();
			ResponseObject<OrdenDTO> response = new ResponseObject<OrdenDTO>(err.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
		ordenService.deleteOrden(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id_orden}/reparaciones/{id_reparacion}")
	public ResponseEntity<Reparacion> getReparacionById(
		@PathVariable Long id_orden,
		@PathVariable Long id_reparacion
	) {
		Reparacion reparacion = reparacionService.getReparacionById(id_reparacion);
		if (reparacion != null) {
			return ResponseEntity.ok(reparacion);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{id_orden}/reparaciones")
	@Transactional
	public ResponseEntity<OrdenDTO> createReparacion(
		@PathVariable Long id_orden,
		@RequestBody ReparacionDTO reparacion
	) throws Exception {
		Orden orden = reparacionService.createReparacion(reparacion);
		if(!orden.getId().equals(id_orden)) {
			throw new Exception("El \"Orden.id\" no coincide!");
		}
		OrdenDTO dto = convertOrdenToDTO(orden);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@DeleteMapping("/{id_orden}/reparaciones/{id_reparacion}")
	@Transactional
	public ResponseEntity<OrdenDTO> deleteReparacion(
		@PathVariable Long id_orden,
		@PathVariable Long id_reparacion
	) throws Exception {
		Orden orden = reparacionService.deleteReparacion(id_reparacion);
		OrdenDTO dto = convertOrdenToDTO(orden);
		return ResponseEntity.ok(dto);
	}
}
