package mingeso.proyecto.autofix_reportes.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReparacionMesTipoSummary {
    private ReparacionDTO tipo;

    private LocalDate mes0;
    private Long totalMes0;
    private Long totalMontoMes0;
    private LocalDate mes1;
    private Long totalMes1;
    private Long totalMontoMes1;
    private LocalDate mes2;
    private Long totalMes2;
    private Long totalMontoMes2;
    private LocalDate mes3;
    private Long totalMes3;
    private Long totalMontoMes3;

    private Long totalReparaciones;
    private Long totalMonto;

    public ReparacionMesTipoSummary(
        ReparacionDTO tipo,
        LocalDate mes0,
        LocalDate mes1,
        LocalDate mes2,
        LocalDate mes3
    ) {
        this.tipo = tipo;

        this.mes0 = mes0;
        this.totalMes0 = 0L;
        this.totalMontoMes0 = 0L;

        this.mes1 = mes1;
        this.totalMes1 = 0L;
        this.totalMontoMes1 = 0L;

        this.mes2 = mes2;
        this.totalMes2 = 0L;
        this.totalMontoMes2 = 0L;

        this.mes3 = mes3;
        this.totalMes3 = 0L;
        this.totalMontoMes3 = 0L;

        this.totalReparaciones = 0L;
        this.totalMonto = 0L;
    }

    public void addMes0(Long monto){
        this.totalMes0 += 1;
        this.totalMontoMes0 += monto;
        this.totalReparaciones += 1;
        this.totalMonto += monto;
    }

    public void addMes1(Long monto){
        this.totalMes1 += 1;
        this.totalMontoMes1 += monto;
        this.totalReparaciones += 1;
        this.totalMonto += monto;
    }

    public void addMes2(Long monto){
        this.totalMes2 += 1;
        this.totalMontoMes2 += monto;
        this.totalReparaciones += 1;
        this.totalMonto += monto;
    }

    public void addMes3(Long monto){
        this.totalMes3 += 1;
        this.totalMontoMes3 += monto;
        this.totalReparaciones += 1;
        this.totalMonto += monto;
    }
}
