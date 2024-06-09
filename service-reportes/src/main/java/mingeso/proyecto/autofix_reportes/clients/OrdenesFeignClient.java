package mingeso.proyecto.autofix_reportes.clients;

import mingeso.proyecto.autofix_reportes.dtos.OrdenDTO;
import mingeso.proyecto.autofix_reportes.models.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(
        value = "autofix-ordenes",
        path = "/ordenes"
)
public interface OrdenesFeignClient {
    @GetMapping("/all")
    List<OrdenDTO> getAllOrdenes();

    @GetMapping("/{id}")
    OrdenDTO getOrdenById(@PathVariable Long id);

    @PostMapping("/create")
    ResponseObject<OrdenDTO> createOrden(@RequestBody OrdenDTO orden);

    @PutMapping("/{id}")
    ResponseObject<OrdenDTO> updateOrden(@PathVariable Long id, @RequestBody OrdenDTO updatedOrden);

    @DeleteMapping("/{id}")
    void deleteOrden(@PathVariable Long id);
}
