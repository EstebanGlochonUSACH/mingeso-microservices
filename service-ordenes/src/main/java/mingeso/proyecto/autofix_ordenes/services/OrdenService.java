package mingeso.proyecto.autofix_ordenes.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import mingeso.proyecto.autofix_ordenes.clients.AutosFeignClient;
import mingeso.proyecto.autofix_ordenes.dtos.AutoDTO;
import mingeso.proyecto.autofix_ordenes.dtos.OrdenDTO;
import mingeso.proyecto.autofix_ordenes.repositories.BonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mingeso.proyecto.autofix_ordenes.config.DescuentoReparacionesConfig;
import mingeso.proyecto.autofix_ordenes.config.RecargoAntiguedadConfig;
import mingeso.proyecto.autofix_ordenes.config.RecargoAtrasoConfig;
import mingeso.proyecto.autofix_ordenes.config.RecargoKilometrajeConfig;
import mingeso.proyecto.autofix_ordenes.entities.Bono;
import mingeso.proyecto.autofix_ordenes.entities.Orden;
import mingeso.proyecto.autofix_ordenes.repositories.OrdenRepository;

@Service
public class OrdenService
{
	private static Double IVA = 0.19;
	
	private final OrdenRepository ordenRepository;
	private final BonoRepository bonoRepository;
	private final AutosFeignClient autosClient;

	@Autowired
	public OrdenService(
		OrdenRepository ordenRepository,
		BonoRepository bonoRepository,
		AutosFeignClient autosClient
	) {
		this.ordenRepository = ordenRepository;
		this.bonoRepository = bonoRepository;
		this.autosClient = autosClient;
	}

	public Page<Orden> getPagedOrdenes(Pageable pageable) {
		return ordenRepository.findAllSorted(pageable);
	}

	public List<Orden> getOrdenesByAuto(Long id_auto) {
		return ordenRepository.findAllById_auto(id_auto);
	}

	public List<Orden> getAllOrdenes() {
		return ordenRepository.findAllSorted();
	}

	public Orden getOrdenById(Long id) {
		return ordenRepository.findById(id).orElse(null);
	}

	private Boolean isSpecialDay(LocalDateTime dateTime){
		DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
		boolean isMondayOrThursday = (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.THURSDAY);

		LocalTime time = dateTime.toLocalTime();
		boolean isBetween9And12 = time.isAfter(LocalTime.of(9, 0)) && time.isBefore(LocalTime.of(12, 0));

		return(isMondayOrThursday && isBetween9And12);
	}

	private AutoDTO getAuto(Long id_auto) throws Exception {
		if(id_auto == null){
			throw new Exception("La orden no tiene auto!");
		}
		AutoDTO auto = autosClient.getAutoById(id_auto);
		if(auto == null){
			throw new Exception("El auto no existe!");
		}
		return auto;
	}

	@Transactional
	public Orden createOrden(OrdenDTO orden) throws Exception {
		// Registrar Bono
		AutoDTO auto = getAuto(orden.getAuto().getId());
		Orden newOrden = new Orden();
		newOrden.setId_auto(auto.getId());

		Bono bono = orden.getBono();
		if(bono != null){
			Long marca = bono.getMarca();
			if(marca != null && !marca.equals(auto.getMarca().getId())){
				throw new Exception("No se puede registrar un bono de una Marca distinta a la del auto!");
			}
			else if(bono.getUsado()){
				throw new Exception("No se puede registrar un bono que ya fue canjeado!");
			}
			else{
				bono.setUsado(true);
				bonoRepository.save(bono);
				newOrden.setBono(bono);
			}
		}

		return ordenRepository.save(newOrden);
	}

	@Transactional
	public Orden updateOrden(Orden updatedOrden, Integer totalReparaciones) throws Exception {
		Orden existingOrden = ordenRepository.findById(updatedOrden.getId()).orElse(null);
		if (existingOrden == null) return null;

		// Validar que el bono no este usado y que la orden no tenga bono
		AutoDTO auto = getAuto(existingOrden.getId_auto());

		Bono updatedBono = updatedOrden.getBono();
		Bono bono = null;
		if(updatedBono != null){
			bono = bonoRepository.findById(updatedBono.getId()).orElse(null);
		}

		// Para reparaciones, descuentos y recargas
		String autoTipo = auto.getTipo();
		Double descuento;
		Long montoTmp;
		Long montoReparaciones = updatedOrden.getMontoReparaciones();
		Long montoTotal = montoReparaciones;
		existingOrden.setMontoReparaciones(montoReparaciones);

		// Bono
		if(bono != null){
			// Checks
			Long marca = bono.getMarca();
			if(marca != null && !marca.equals(auto.getMarca().getId())){
				throw new Exception("No se puede registrar un bono de una Marca distinta a la del auto!");
			}

			// Clear existing bono (if any)
			Bono existingBono = existingOrden.getBono();
			if(existingBono != null && !existingBono.equals(bono)){
				existingBono.setUsado(false);
				bonoRepository.save(existingBono);
				existingOrden.setBono(null);
				existingBono = null;
			}

			// Add new bono
			if(existingBono == null){
				if(bono.getUsado()){
					throw new Exception("No se puede registrar un bono que ya fue canjeado!");
				}
				bono.setUsado(true);
				bonoRepository.save(bono);
				existingOrden.setBono(bono);
				if(montoReparaciones != null){
					montoTotal -= bono.getMonto();
				}
			}
		}
		else{
			Bono existingBono = existingOrden.getBono();
			if(existingBono != null){
				existingBono.setUsado(false);
				bonoRepository.save(existingBono);
				existingOrden.setBono(null);
			}
		}

		// Fechas
		LocalDateTime fechaIngreso = updatedOrden.getFechaIngreso();
		LocalDateTime fechaSalida = updatedOrden.getFechaSalida();
		LocalDateTime fechaEntrega = updatedOrden.getFechaEntrega();

		existingOrden.setFechaIngreso(fechaIngreso);
		existingOrden.setFechaSalida(fechaSalida);
		existingOrden.setFechaEntrega(fechaEntrega);

		if(fechaIngreso == null){
			fechaSalida = null;
			fechaEntrega = null;
			existingOrden.setFechaIngreso(null);
			existingOrden.setFechaSalida(null);
			existingOrden.setFechaEntrega(null);
		}
		else if(fechaSalida == null){
			fechaEntrega = null;
			existingOrden.setFechaIngreso(fechaIngreso);
		}

		// Descuento por dia de Atencion
		if(montoReparaciones != null && fechaIngreso != null){
			if(isSpecialDay(fechaIngreso)){
				montoTmp = Math.round(montoReparaciones * 0.1);
				existingOrden.setDescuentoDiaAtencion(montoTmp);
				montoTotal -= montoTmp;
			}
			else{
				existingOrden.setDescuentoDiaAtencion(null);
			}
		}
		else{
			existingOrden.setDescuentoDiaAtencion(null);
		}

		// Descuento por reparaciones
		if(montoReparaciones != null){
			if(totalReparaciones > 0){
				descuento = DescuentoReparacionesConfig.getDescuento(auto.getMotor(), totalReparaciones);
				montoTmp = Math.round(montoReparaciones * descuento);
				existingOrden.setDescuentoReparaciones(montoTmp);
				montoTotal -= montoTmp;
			}
			else{
				existingOrden.setDescuentoReparaciones(0L);
			}
		}
		else{
			existingOrden.setDescuentoReparaciones(null);
		}

		// Recarga por Antiguedad del auto
		if(montoReparaciones != null && fechaIngreso != null){
			Integer diff = fechaIngreso.getYear() - auto.getAnio();
			descuento = RecargoAntiguedadConfig.getRecargo(autoTipo, diff);
			montoTmp = Math.round(montoReparaciones * descuento);
			existingOrden.setRecargaAntiguedad(montoTmp);
			montoTotal += montoTmp;
		}
		else{
			existingOrden.setRecargaAntiguedad(null);
		}

		// Recarga por kilometraje
		if(montoReparaciones != null){
			descuento = RecargoKilometrajeConfig.getRecargo(autoTipo, auto.getKilometraje());
			montoTmp = Math.round(montoReparaciones * descuento);
			existingOrden.setRecargaKilometraje(montoTmp);
			montoTotal += montoTmp;
		}
		else{
			existingOrden.setRecargaKilometraje(null);
		}

		// Recarga Atraso
		if(montoReparaciones != null && fechaEntrega != null && fechaEntrega.isAfter(fechaSalida)){
			Long days = Math.abs(ChronoUnit.DAYS.between(fechaEntrega, fechaSalida));
			montoTmp = Math.round(RecargoAtrasoConfig.getRecargo(montoReparaciones, days));
			existingOrden.setRecargaAtraso(montoTmp);
			montoTotal += montoTmp;
		}
		else{
			existingOrden.setRecargaAtraso(null);
		}

		// Calculo del IVA y total
		if(montoReparaciones != null && fechaEntrega != null){
			if(montoTotal < 0){
				montoTotal = 0L;
			}
			existingOrden.setMontoTotal(montoTotal);
			montoTmp = Math.round(montoTotal * IVA);
			existingOrden.setValorIva(montoTmp);
		}
		
		return ordenRepository.save(existingOrden);
	}

	public void deleteOrden(Long id) {
		ordenRepository.deleteById(id);
	}
}
