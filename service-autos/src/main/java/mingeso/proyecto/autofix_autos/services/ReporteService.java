package mingeso.proyecto.autofix_autos.services;

import java.util.List;

import mingeso.proyecto.autofix_autos.repositories.OrdenRepository;
import mingeso.proyecto.autofix_autos.repositories.ReparacionRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import mingeso.proyecto.autofix_autos.dtos.AvgRepairTimeByMarcaDTO;
import mingeso.proyecto.autofix_autos.dtos.ReparacionMotorSummary;
import mingeso.proyecto.autofix_autos.dtos.ReparacionTipoSummary;

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
