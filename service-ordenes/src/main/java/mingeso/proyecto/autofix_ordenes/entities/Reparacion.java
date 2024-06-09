package mingeso.proyecto.autofix_ordenes.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

	public Reparacion() {}

	public Reparacion(Orden orden, Long tipo, Integer monto){
		this.orden = orden;
		this.id_tipo = tipo;
		this.monto = monto;
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
}
