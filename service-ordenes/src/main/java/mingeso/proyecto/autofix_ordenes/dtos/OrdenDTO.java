package mingeso.proyecto.autofix_ordenes.dtos;

import lombok.Getter;
import lombok.Setter;
import mingeso.proyecto.autofix_ordenes.entities.Bono;
import mingeso.proyecto.autofix_ordenes.entities.Reparacion;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrdenDTO {
    Long id;
    AutoDTO auto;
    Bono bono;
    List<Reparacion> reparaciones;
    Long descuentoDiaAtencion;
    Long descuentoReparaciones;
    Long recargaAntiguedad;
    Long recargaAtraso;
    Long recargaKilometraje;
    Long montoReparaciones;
    Long valorIva;
    Long montoTotal;
    LocalDateTime fechaIngreso;
    LocalDateTime fechaSalida;
    LocalDateTime fechaEntrega;
};
