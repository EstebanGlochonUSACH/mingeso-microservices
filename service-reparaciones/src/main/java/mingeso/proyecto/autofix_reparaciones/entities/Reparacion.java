package mingeso.proyecto.autofix_reparaciones.entities;

import jakarta.persistence.*;

@Entity
public class Reparacion
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	@Column(name = "monto_gasolina")
	private Integer montoGasolina;

	@Column(name = "monto_diesel")
	private Integer montoDiesel;

	@Column(name = "monto_hibrido")
	private Integer montoHibrido;

	@Column(name = "monto_electrico")
	private Integer montoElectrico;

	@Column(nullable = true)
	private String descripcion;

	public Reparacion() {};

	public Long getId(){
		return id;
	}

	public void setId(){
		this.id = id;
	}

	public String getNombre(){
		return nombre;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	public Integer getMontoGasolina(){
		return montoGasolina;
	}

	public void setMontoGasolina(Integer monto){
		this.montoGasolina = monto;
	}

	public Integer getMontoDiesel(){
		return montoDiesel;
	}

	public void setMontoDiesel(Integer monto){
		this.montoDiesel = monto;
	}

	public Integer getMontoHibrido(){
		return montoHibrido;
	}

	public void setMontoHibrido(Integer monto){
		this.montoHibrido = monto;
	}

	public Integer getMontoElectrico(){
		return montoElectrico;
	}

	public void setMontoElectrico(Integer monto){
		this.montoElectrico = monto;
	}

	public String getDescripcion(){
		return descripcion;
	}

	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
}
