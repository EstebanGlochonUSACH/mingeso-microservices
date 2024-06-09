package mingeso.proyecto.autofix_reportes.services;

import java.util.List;

import mingeso.proyecto.autofix_reportes.repositories.OrdenRepository;
import mingeso.proyecto.autofix_reportes.repositories.ReparacionRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import mingeso.proyecto.autofix_reportes.dtos.AvgRepairTimeByMarcaDTO;
import mingeso.proyecto.autofix_reportes.dtos.ReparacionMotorSummary;
import mingeso.proyecto.autofix_reportes.dtos.ReparacionTipoSummary;

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
