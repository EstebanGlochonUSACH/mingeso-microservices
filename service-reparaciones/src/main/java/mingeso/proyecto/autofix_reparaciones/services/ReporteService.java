package mingeso.proyecto.autofix_reparaciones.services;

import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import mingeso.proyecto.autofix_reparaciones.dtos.AvgRepairTimeByMarcaDTO;
import mingeso.proyecto.autofix_reparaciones.dtos.ReparacionMotorSummary;
import mingeso.proyecto.autofix_reparaciones.dtos.ReparacionTipoSummary;
import mingeso.proyecto.autofix_reparaciones.repositories.OrdenRepository;
import mingeso.proyecto.autofix_reparaciones.repositories.ReparacionRepository;

@Service
public class ReporteService
{
	private final ReparacionRepository reparacionRepository;
    private final OrdenRepository ordenRepository;

	@Autowired
	public ReporteService(ReparacionRepository reparacionRepository, OrdenRepository ordenRepository) {
		this.reparacionRepository = reparacionRepository;
		this.ordenRepository = ordenRepository;
	}

	public List<ReparacionTipoSummary> getReparacionTipoSummary() {
		return reparacionRepository.findReparacionTipoSummary();
	}

	public List<ReparacionMotorSummary> getReparacionMotorSummary() {
		return reparacionRepository.findReparacionMotorSummary();
	}

    public List<AvgRepairTimeByMarcaDTO> getAvgRepairTimeByMarca() {
        return ordenRepository.findAvgRepairTimeByMarca();
    }
}
