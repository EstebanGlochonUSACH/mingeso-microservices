package mingeso.proyecto.autofix_ordenes.clients;

import mingeso.proyecto.autofix_ordenes.dtos.AutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "autofix-autos",
    url = "autofix-autos-service:8090",
    path = "/autos"
)
public interface AutosFeignClient {
    @GetMapping("/{id}")
    AutoDTO getAutoById(@PathVariable Long id);

    @PostMapping
    AutoDTO createAuto(@RequestBody AutoDTO auto);

    @PutMapping(value = "/{id}", consumes = "application/json")
    AutoDTO updateAuto(@PathVariable Long id, @RequestBody AutoDTO updatedAuto);

    @DeleteMapping("/{id}")
    void deleteAuto(@PathVariable Long id);
}
