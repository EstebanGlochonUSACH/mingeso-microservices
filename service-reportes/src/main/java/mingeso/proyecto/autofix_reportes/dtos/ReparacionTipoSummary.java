package mingeso.proyecto.autofix_reportes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReparacionTipoSummary
{
	private ReparacionDTO tipo;

	private Long totalSedan;
	private Long totalMontoSedan;
	private Long totalHatchback;
	private Long totalMontoHatchback;
	private Long totalSuv;
	private Long totalMontoSuv;
	private Long totalPickup;
	private Long totalMontoPickup;
	private Long totalFurgoneta;
	private Long totalMontoFurgoneta;

	private Long totalAutos;
	private Long totalMonto;

	public ReparacionTipoSummary(ReparacionDTO tipo) {
		this.tipo = tipo;
		this.totalSedan = 0L;
		this.totalMontoSedan = 0L;
		this.totalHatchback = 0L;
		this.totalMontoHatchback = 0L;
		this.totalSuv = 0L;
		this.totalMontoSuv = 0L;
		this.totalPickup = 0L;
		this.totalMontoPickup = 0L;
		this.totalFurgoneta = 0L;
		this.totalMontoFurgoneta = 0L;
		this.totalAutos = 0L;
		this.totalMonto = 0L;
	}

	public void addSedan(Long monto){
		this.totalSedan += 1;
		this.totalMontoSedan += monto;
		this.totalAutos += 1;
		this.totalMonto += monto;
	}

	public void addHatchback(Long monto){
		this.totalHatchback += 1;
		this.totalMontoHatchback += monto;
		this.totalAutos += 1;
		this.totalMonto += monto;
	}

	public void addSuv(Long monto){
		this.totalSuv += 1;
		this.totalMontoSuv += monto;
		this.totalAutos += 1;
		this.totalMonto += monto;
	}

	public void addPickup(Long monto){
		this.totalPickup += 1;
		this.totalMontoPickup += monto;
		this.totalAutos += 1;
		this.totalMonto += monto;
	}

	public void addFurgoneta(Long monto){
		this.totalFurgoneta += 1;
		this.totalMontoFurgoneta += monto;
		this.totalAutos += 1;
		this.totalMonto += monto;
	}
}
