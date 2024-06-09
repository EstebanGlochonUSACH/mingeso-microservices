package mingeso.proyecto.autofix_reparaciones.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mingeso.proyecto.autofix_reparaciones.entities.Reparacion;
import mingeso.proyecto.autofix_reparaciones.repositories.ReparacionRepository;

@Service
public class ReparacionService
{
	private final ReparacionRepository reparacionRepository;

	@Autowired
	public ReparacionService(ReparacionRepository reparacionRepository) {
		this.reparacionRepository = reparacionRepository;
	}

	public List<Reparacion> getAllReparaciones() {
		return reparacionRepository.findAll();
	}

	public Reparacion getReparacionById(Long id) {
		return reparacionRepository.findById(id).orElse(null);
	}

	public Reparacion createReparacion(Reparacion reparacion) {
		return reparacionRepository.save(reparacion);
	}

	public Reparacion updateReparacion(Reparacion updatedReparacion) {
		Reparacion existingReparacion = reparacionRepository.findById(updatedReparacion.getId()).orElse(null);
		if (existingReparacion != null) {
			existingReparacion.setMontoGasolina(updatedReparacion.getMontoGasolina());
			existingReparacion.setMontoDiesel(updatedReparacion.getMontoDiesel());
			existingReparacion.setMontoHibrido(updatedReparacion.getMontoHibrido());
			existingReparacion.setMontoElectrico(updatedReparacion.getMontoElectrico());
			existingReparacion.setDescripcion(updatedReparacion.getDescripcion());
			return reparacionRepository.save(existingReparacion);
		}
		return null;
	}

	public Reparacion deleteReparacion(Long id) {
		Reparacion reparacion = reparacionRepository.findById(id).orElse(null);
		if(reparacion == null){
			return null;
		}
		reparacionRepository.deleteById(id);
		return reparacion;
	}
}
