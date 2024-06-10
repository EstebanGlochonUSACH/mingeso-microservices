package mingeso.proyecto.autofix_reportes.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mingeso.proyecto.autofix_reportes.clients.OrdenesFeignClient;
import mingeso.proyecto.autofix_reportes.clients.ReparacionesFeignClient;
import mingeso.proyecto.autofix_reportes.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class ReporteService
{
	private final ReparacionesFeignClient reparacionesClient;
    private final OrdenesFeignClient ordenesClient;

	@Autowired
	public ReporteService(
		ReparacionesFeignClient reparacionesClient,
		OrdenesFeignClient ordenesClient
	) {
		this.reparacionesClient = reparacionesClient;
		this.ordenesClient = ordenesClient;
	}

	public List<ReparacionTipoSummary> getReparacionTipoSummary(Integer year, Integer month) {
		List<OrdenDTO> ordenes = ordenesClient.getAllOrdenes();
		Map<Long, ReparacionTipoSummary> map = new HashMap<>();
		ReparacionTipoSummary aux;

		for(OrdenDTO orden : ordenes){
			if(orden.getFechaIngreso().getYear() != year || orden.getFechaIngreso().getMonthValue() != month){
				continue;
			}

			AutoDTO auto = orden.getAuto();
			String autoTipo = auto.getTipo();

			for(ReparacionDTO reparacion : orden.getReparaciones()){
				Long id = reparacion.getId();
				if(!map.containsKey(id)){
					map.put(id, new ReparacionTipoSummary(reparacion));
				}
				aux = map.get(id);
				if(autoTipo.equals("SEDAN")){
					aux.addSedan(reparacion.getMonto());
				}
				else if(autoTipo.equals("HATCHBACK")){
					aux.addHatchback(reparacion.getMonto());
				}
				else if(autoTipo.equals("SUV")){
					aux.addSuv(reparacion.getMonto());
				}
				else if(autoTipo.equals("PICKUP")){
					aux.addPickup(reparacion.getMonto());
				}
				else if(autoTipo.equals("FURGONETA")){
					aux.addFurgoneta(reparacion.getMonto());
				}
			}
		}

		List<ReparacionTipoDTO> repTipos = reparacionesClient.getAllReparaciones();
		for(ReparacionTipoDTO repTipo : repTipos){
			Long id = repTipo.getId();
			if(!map.containsKey(id)){
				ReparacionDTO reparacion = new ReparacionDTO();
				reparacion.setId(id);
				reparacion.setNombre(repTipo.getNombre());
				reparacion.setMonto(0L);
				reparacion.setDescripcion(repTipo.getDescripcion());
				map.put(id, new ReparacionTipoSummary(reparacion));
			}
			else{
				aux = map.get(id);
				ReparacionDTO reparacion = aux.getTipo();
				reparacion.setId(id);
				reparacion.setNombre(repTipo.getNombre());
				reparacion.setMonto(0L);
				reparacion.setDescripcion(repTipo.getDescripcion());
			}
		}

		return new ArrayList<>(map.values());
	}

	private Boolean inMonth(LocalDateTime dateTime, LocalDate date){
		return(dateTime.getYear() == date.getYear() &&
				dateTime.getMonthValue() == date.getMonthValue());
	}

	public List<ReparacionMesTipoSummary> getReparacionMesTipoSummary(Integer year, Integer month) {
		List<OrdenDTO> ordenes = ordenesClient.getAllOrdenes();
		Map<Long, ReparacionMesTipoSummary> map = new HashMap<>();
		ReparacionMesTipoSummary aux;

		LocalDate mes0 = LocalDate.of(year, month, 15);
		LocalDate mes1 = mes0.minusMonths(1);
		LocalDate mes2 = mes0.minusMonths(2);
		LocalDate mes3 = mes0.minusMonths(3);

		for(OrdenDTO orden : ordenes){
			for(ReparacionDTO reparacion : orden.getReparaciones()){
				Long id = reparacion.getId();
				if(!map.containsKey(id)){
					map.put(id, new ReparacionMesTipoSummary(reparacion, mes0, mes1, mes2, mes3));
				}
				aux = map.get(id);
				if(inMonth(orden.getFechaIngreso(), mes0)){
					aux.addMes0(reparacion.getMonto());
				}
				else if(inMonth(orden.getFechaIngreso(), mes1)){
					aux.addMes1(reparacion.getMonto());
				}
				else if(inMonth(orden.getFechaIngreso(), mes2)){
					aux.addMes2(reparacion.getMonto());
				}
				else if(inMonth(orden.getFechaIngreso(), mes3)){
					aux.addMes3(reparacion.getMonto());
				}
			}
		}

		List<ReparacionTipoDTO> repTipos = reparacionesClient.getAllReparaciones();
		for(ReparacionTipoDTO repTipo : repTipos){
			Long id = repTipo.getId();
			if(!map.containsKey(id)){
				ReparacionDTO reparacion = new ReparacionDTO();
				reparacion.setId(id);
				reparacion.setNombre(repTipo.getNombre());
				reparacion.setMonto(0L);
				reparacion.setDescripcion(repTipo.getDescripcion());
				map.put(id, new ReparacionMesTipoSummary(reparacion, mes0, mes1, mes2, mes3));
			}
			else{
				aux = map.get(id);
				ReparacionDTO reparacion = aux.getTipo();
				reparacion.setId(id);
				reparacion.setNombre(repTipo.getNombre());
				reparacion.setMonto(0L);
				reparacion.setDescripcion(repTipo.getDescripcion());
			}
		}

		return new ArrayList<>(map.values());
	}
}
