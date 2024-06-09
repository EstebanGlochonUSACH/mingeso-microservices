package mingeso.proyecto.autofix_ordenes.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mingeso.proyecto.autofix_ordenes.repositories.BonoRepository;
import mingeso.proyecto.autofix_ordenes.dtos.BonoGroupedByFechaInicioDTO;
import mingeso.proyecto.autofix_ordenes.entities.Bono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonoService
{
	private final BonoRepository bonoRepository;

	@Autowired
	public BonoService(BonoRepository bonoRepository) {
		this.bonoRepository = bonoRepository;
	}

	public List<Bono> getAllBonos() {
		return bonoRepository.findAll();
	}

	public Bono getBono(Long id) {
		return bonoRepository.findById(id).orElse(null);
	}

	public List<Bono> getFilteredBono(Long marca, LocalDateTime fecha) {
		if (marca != null) {
			return bonoRepository.findAllByMarcaAndFecha(marca, fecha);
		}
		else {
			return bonoRepository.findAll();
		}
	}

	public List<BonoGroupedByFechaInicioDTO> getAllBonosByGroup() {
		return bonoRepository.groupByFechaInicio();
	}

	public Bono createBono(Long marca, Integer monto, LocalDateTime day) {
		LocalDateTime workingDate = day;
		if(workingDate == null){
			workingDate = LocalDateTime.now();
		}
		LocalDateTime fechaInicio = LocalDateTime.of(workingDate.getYear(), workingDate.getMonth(), 1, 0, 0, 0);
		LocalDateTime fechaTermino = fechaInicio.plusMonths(1);
		Bono bono = new Bono(marca, monto, fechaInicio, fechaTermino);
		return bonoRepository.save(bono);
	}

	public List<Bono> createBonos(Long marca, Integer monto, Integer cantidad, LocalDateTime day) {
		List<Bono> bonos = new ArrayList<>();
		Bono bono;
		for(int i = 0; i < cantidad; ++i){
			bono = createBono(marca, monto, day);
			bonos.add(bono);
		}
		return bonos;
	}

	public Bono updateBono(Long id, Boolean usado) {
		Bono bono = bonoRepository.findById(id).orElse(null);
		if (bono != null) {
			bono.setUsado(usado);
			return bonoRepository.save(bono);
		}
		return null;
	}
}
