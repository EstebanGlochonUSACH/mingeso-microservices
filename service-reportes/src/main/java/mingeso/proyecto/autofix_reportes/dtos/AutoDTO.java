package mingeso.proyecto.autofix_reportes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutoDTO {
    Long id_auto;
    String patente;
    MarcaDTO marca;
    String modelo;
    String tipo;
    Integer anio;
    String motor;
    Integer asientos;
    Integer kilometraje;
};
