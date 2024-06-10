package mingeso.proyecto.autofix_ordenes.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mingeso.proyecto.autofix_ordenes.clients.MarcasFeignClient;
import mingeso.proyecto.autofix_ordenes.dtos.BonoDTO;
import mingeso.proyecto.autofix_ordenes.dtos.MarcaDTO;
import mingeso.proyecto.autofix_ordenes.repositories.BonoRepository;
import mingeso.proyecto.autofix_ordenes.dtos.BonoGroupedByFechaInicioDTO;
import mingeso.proyecto.autofix_ordenes.entities.Bono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonoService
{
	private final BonoRepository bonoRepository;
	private final MarcasFeignClient marcasClient;

	@Autowired
	public BonoService(
		BonoRepository bonoRepository,
		MarcasFeignClient marcasClient
	) {
		this.bonoRepository = bonoRepository;
		this.marcasClient = marcasClient;
	}

	private BonoDTO convertBonoToDTO(Bono bono){
		MarcaDTO marca = marcasClient.getMarcaById(bono.getMarca());
		BonoDTO dto = new BonoDTO();
		dto.setId(bono.getId());
		dto.setMonto(bono.getMonto());
		dto.setMarca(marca);
		dto.setFechaInicio(bono.getFechaInicio());
		dto.setFechaTermino(bono.getFechaTermino());
		dto.setUsado(bono.getUsado());
		return dto;
	}

	private List<BonoDTO> convertBonosToDTOs(List<Bono> bonos){
		List<BonoDTO> dtos = new ArrayList<>();
		BonoDTO tmpDto;
		for(Bono bono : bonos) {
			tmpDto = convertBonoToDTO(bono);
			dtos.add(tmpDto);
		}
		return dtos;
	}

	public List<BonoDTO> getAllBonos() {
		List<Bono> bonos = bonoRepository.findAll();
		return convertBonosToDTOs(bonos);
	}

	public BonoDTO getBono(Long id){
		Bono bono = bonoRepository.findById(id).orElse(null);
		if(bono != null){
			return convertBonoToDTO(bono);
		}
		return null;
	}

	public List<BonoDTO> getFilteredBono(Long marcaId, LocalDateTime fecha) {
		List<Bono> bonos;
		if (marcaId != null) {
			bonos = bonoRepository.findAllByMarcaAndFecha(marcaId, fecha);
		}
		else {
			bonos = bonoRepository.findAll();
		}
		return convertBonosToDTOs(bonos);
	}

	public List<BonoGroupedByFechaInicioDTO> getAllBonosByGroup() {
		List<BonoGroupedByFechaInicioDTO> groups = bonoRepository.groupByFechaInicio();
		MarcaDTO marca;
		for(BonoGroupedByFechaInicioDTO group : groups){
			marca = marcasClient.getMarcaById(group.getId_marca());
			group.setMarca(marca);
		}
		return groups;
	}

	public BonoDTO createBono(Long marca, Integer monto, LocalDateTime day) {
		LocalDateTime workingDate = day;
		if(workingDate == null){
			workingDate = LocalDateTime.now();
		}
		LocalDateTime fechaInicio = LocalDateTime.of(workingDate.getYear(), workingDate.getMonth(), 1, 0, 0, 0);
		LocalDateTime fechaTermino = fechaInicio.plusMonths(1);
		Bono bono = new Bono(marca, monto, fechaInicio, fechaTermino);
		bono = bonoRepository.save(bono);
		return convertBonoToDTO(bono);
	}

	public List<BonoDTO> createBonos(Long marca, Integer monto, Integer cantidad, LocalDateTime day) {
		List<BonoDTO> bonos = new ArrayList<>();
		BonoDTO bono;
		for(int i = 0; i < cantidad; ++i){
			bono = createBono(marca, monto, day);
			bonos.add(bono);
		}
		return bonos;
	}

	public BonoDTO updateBono(Long id, Boolean usado) {
		Bono bono = bonoRepository.findById(id).orElse(null);
		if (bono != null) {
			bono.setUsado(usado);
			bono = bonoRepository.save(bono);
			return convertBonoToDTO(bono);
		}
		return null;
	}
}
