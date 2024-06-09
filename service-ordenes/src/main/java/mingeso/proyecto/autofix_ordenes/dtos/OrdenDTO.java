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
    List<Reparacion> reparaciones;
    Long valorIva;
    Long montoTotal;
    LocalDateTime fechaIngreso;
    LocalDateTime fechaSalida;
    LocalDateTime fechaEntrega;
};
