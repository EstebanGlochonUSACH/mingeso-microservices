package mingeso.proyecto.autofix_ordenes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReparacionTipoDTO {
    Long id;
    String nombre;
    Integer montoGasolina;
    Integer montoDiesel;
    Integer montoHibrido;
    Integer montoElectrico;
    String descripcion;
}
