package mingeso.proyecto.autofix_reportes.clients;

import mingeso.proyecto.autofix_reportes.dtos.ReparacionTipoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = "autofix-reparaciones",
        path = "/reparaciones"
)
public interface ReparacionesFeignClient {
    @GetMapping
    List<ReparacionTipoDTO> getAllReparaciones();
}
