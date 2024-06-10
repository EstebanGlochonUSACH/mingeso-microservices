package mingeso.proyecto.autofix_ordenes.services;

import java.time.LocalDateTime;
import java.util.List;
import mingeso.proyecto.autofix_ordenes.clients.AutosFeignClient;
import mingeso.proyecto.autofix_ordenes.clients.ReparacionesFeignClient;
import mingeso.proyecto.autofix_ordenes.dtos.AutoDTO;
import mingeso.proyecto.autofix_ordenes.dtos.ReparacionTipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mingeso.proyecto.autofix_ordenes.entities.Orden;
import mingeso.proyecto.autofix_ordenes.entities.Reparacion;
import mingeso.proyecto.autofix_ordenes.repositories.ReparacionRepository;

@Service
public class ReparacionService
{
	private final ReparacionRepository reparacionRepository;
	private final OrdenService ordenService;
	private final AutosFeignClient autosClient;
	private final ReparacionesFeignClient reparacionesClient;

	@Autowired
	public ReparacionService(
		ReparacionRepository reparacionRepository,
		OrdenService ordenService,
		AutosFeignClient autosClient,
		ReparacionesFeignClient reparacionesClient
	) {
		this.reparacionRepository = reparacionRepository;
		this.ordenService = ordenService;
		this.autosClient = autosClient;
		this.reparacionesClient = reparacionesClient;
	}

	public List<Reparacion> getAllReparaciones() {
		return reparacionRepository.findAll();
	}

	public Reparacion getReparacionById(Long id) {
		return reparacionRepository.findById(id).orElse(null);
	}

	private Orden actualizarOrden(Long ordenId) throws Exception {
		Orden orden = ordenService.getOrdenById(ordenId);
		if(orden == null) return null;
		Long montoReparaciones = 0L;
		int totalReparaciones = 0;
		for(Reparacion rep : orden.getReparaciones()){
			totalReparaciones += 1;
			montoReparaciones += rep.getMonto();
		}
		orden.setMontoReparaciones(montoReparaciones);
		return ordenService.updateOrden(orden, totalReparaciones);
	}

	public Orden createReparacion(Reparacion reparacion) throws Exception {
		// Definir bien el monto
		Orden orden = reparacion.getOrden();
		if(orden == null){
			throw new Exception("La reparacion no tiene \"orden\".");
		}

		Long id_auto = orden.getId_auto();
		if(id_auto == null) {
			throw new Exception("La orden no tiene auto!");
		}
		AutoDTO auto = autosClient.getAutoById(id_auto);
		if(auto == null) {
			throw new Exception("El auto de la orden no existe!");
		}

		ReparacionTipoDTO repTipo = reparacionesClient.getReparacion(reparacion.getTipo());
		if(repTipo == null){
			throw new Exception("El tipo de reparacion no existe!");
		}

		Integer monto = 0;
		String tipoMotor = auto.getMotor();
		monto = switch (tipoMotor) {
			case "GASOLINA" -> repTipo.getMontoGasolina();
			case "DIESEL" -> repTipo.getMontoDiesel();
			case "HIBRIDO" -> repTipo.getMontoHibrido();
			case "ELECTRICO" -> repTipo.getMontoElectrico();
			default -> throw new Exception("El tipo motor \"" + tipoMotor + "\" no tiene monto asociado!");
		};

		reparacion.setMonto(monto);
		reparacion.setFechaRegistro(LocalDateTime.now());
		reparacionRepository.save(reparacion);

		return actualizarOrden(orden.getId());
	}

	public Reparacion updateReparacion(Reparacion updatedReparacion) {
		Reparacion existingReparacion = reparacionRepository.findById(updatedReparacion.getId()).orElse(null);
		if (existingReparacion != null) {
			existingReparacion.setTipo(updatedReparacion.getTipo());
			existingReparacion.setMonto(updatedReparacion.getMonto());
			return reparacionRepository.save(existingReparacion);
		}
		return null;
	}

	public Orden deleteReparacion(Long id) throws Exception {
		Reparacion reparacion = reparacionRepository.findById(id).orElse(null);
		if(reparacion == null) return null;

		// Definir bien el monto
		Orden orden = reparacion.getOrden();
		if(orden == null){
			throw new Exception("La reparacion no tiene \"orden\".");
		}

		// Eliminar reparacion
		reparacionRepository.deleteById(id);

		return actualizarOrden(orden.getId());
	}
}
