package mingeso.proyecto.autofix_reportes.dtos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrdenDTO {
    Long id;
    AutoDTO auto;
    List<ReparacionDTO> reparaciones;
    Long valorIva;
    Long montoTotal;
    LocalDateTime fechaIngreso;
    LocalDateTime fechaSalida;
    LocalDateTime fechaEntrega;
};
