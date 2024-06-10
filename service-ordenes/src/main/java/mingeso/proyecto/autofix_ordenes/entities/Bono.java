package mingeso.proyecto.autofix_ordenes.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bono
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_bono;

	public Long id_marca;

	private Integer monto;

	@Column(name = "fecha_inicio")
	private LocalDateTime fechaInicio;

	@Column(name = "fecha_termino")
	private LocalDateTime fechaTermino;

	private Boolean usado;

	public Bono() {}

	public Bono(Long id_marca, Integer monto, LocalDateTime fechaInicio, LocalDateTime fechaTermino){
		this.id_marca = id_marca;
		this.monto = monto;
		this.usado = false;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
	}

	public Long getId(){
		return id_bono;
	}

	public void setId(Long id_bono){
		this.id_bono = id_bono;
	}

	public Long getMarca(){
		return id_marca;
	}

	public void setMarca(Long marca){
		this.id_marca = marca;
	}

	public Integer getMonto(){
		return monto;
	}

	public void setMonto(Integer monto){
		this.monto = monto;
	}

	public Boolean getUsado(){
		return usado;
	}

	public void setUsado(Boolean usado){
		this.usado = usado;
	}

	public LocalDateTime getFechaInicio(){
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio){
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaTermino(){
		return fechaTermino;
	}

	public void setFechaTermino(LocalDateTime fechaTermino){
		this.fechaTermino = fechaTermino;
	}
}
