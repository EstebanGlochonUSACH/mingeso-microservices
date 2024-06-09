package mingeso.proyecto.autofix_ordenes.dtos;

import java.time.LocalDateTime;

public class BonoGroupedByFechaInicioDTO {
	private Long id_marca;
	private Integer monto;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaTermino;
	private Long count;

	public BonoGroupedByFechaInicioDTO(Long marca, Integer monto, LocalDateTime fechaInicio, LocalDateTime fechaTermino, Long count) {
		this.id_marca = marca;
		this.monto = monto;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.count = count;
	}

	public Long getMarca() {
		return id_marca;
	}

	public Integer getMonto() {
		return monto;
	}

	public LocalDateTime getFechaTermino() {
		return fechaTermino;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public Long getCount() {
		return count;
	}
};
