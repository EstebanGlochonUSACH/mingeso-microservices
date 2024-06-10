package mingeso.proyecto.autofix_ordenes.clients;

import mingeso.proyecto.autofix_ordenes.dtos.ReparacionTipoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "autofix-reparaciones",
    path = "/reparaciones"
)
public interface ReparacionesFeignClient {
    @GetMapping
    List<ReparacionTipoDTO> getAllReparaciones();

    @GetMapping("/{id}")
    ReparacionTipoDTO getReparacion(@PathVariable Long id);

    @PostMapping("/create")
    ReparacionTipoDTO createReparacion(@RequestBody ReparacionTipoDTO reparacion);

    @PutMapping("/{id}")
    ReparacionTipoDTO updateReparacion(@PathVariable Long id, @RequestBody ReparacionTipoDTO updatedReparacion);

    @DeleteMapping("/{id}")
    String deleteReparacion(@PathVariable Long id);
}
