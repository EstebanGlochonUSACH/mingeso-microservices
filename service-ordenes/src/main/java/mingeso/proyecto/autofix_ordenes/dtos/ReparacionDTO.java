package mingeso.proyecto.autofix_ordenes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReparacionDTO {
    Long id;
    Long id_tipo;
    OrdenDTO orden;
}
