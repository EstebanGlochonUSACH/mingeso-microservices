package mingeso.proyecto.autofix_ordenes.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reparacion
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_reparacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_orden")
	@JsonBackReference
	public Orden orden;

	private Long id_tipo;

	private Integer monto;

	@Column(name = "fecha_registro", nullable = true)
	private LocalDateTime fechaRegistro;

	public Reparacion() {}

	public Reparacion(Orden orden, Long id_tipo, Integer monto){
		this.orden = orden;
		this.id_tipo = id_tipo;
		this.monto = monto;
		this.fechaRegistro = LocalDateTime.now();
	}

	public Long getId(){
		return id_reparacion;
	}

	public void setId(Long id_reparacion){
		this.id_reparacion = id_reparacion;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public Long getTipo(){
		return id_tipo;
	}

	public void setTipo(Long tipo){
		this.id_tipo = tipo;
	}

	public Integer getMonto(){
		return monto;
	}

	public void setMonto(Integer monto){
		this.monto = monto;
	}

	public LocalDateTime getFechaRegistro(){
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro){
		this.fechaRegistro = fechaRegistro;
	}
}
