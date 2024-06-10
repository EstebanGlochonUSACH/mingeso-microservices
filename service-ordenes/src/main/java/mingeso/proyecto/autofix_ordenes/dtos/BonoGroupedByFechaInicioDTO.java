package mingeso.proyecto.autofix_ordenes.dtos;

import java.time.LocalDateTime;

public class BonoGroupedByFechaInicioDTO {
	private Long id_marca;
	private MarcaDTO marca;
	private Integer monto;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaTermino;
	private Long count;

	public BonoGroupedByFechaInicioDTO(Long id_marca, Integer monto, LocalDateTime fechaInicio, LocalDateTime fechaTermino, Long count) {
		this.id_marca = id_marca;
		this.marca = null;
		this.monto = monto;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.count = count;
	}

	public MarcaDTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaDTO marca) {
		this.marca = marca;
	}

	public Long getId_marca() {
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
