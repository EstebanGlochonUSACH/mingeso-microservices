package mingeso.proyecto.autofix_ordenes.clients;

import mingeso.proyecto.autofix_ordenes.dtos.MarcaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(
    name = "autofix-marcas",
    url = "autofix-autos",
    path = "/marcas"
)
public interface MarcasFeignClient {
    @GetMapping
    List<MarcaDTO> getAllMarcas();

    @GetMapping("/{id}")
    MarcaDTO getMarcaById(@PathVariable Long id);

    @PostMapping("/create")
    MarcaDTO createMarca(@RequestBody MarcaDTO marcaDTO);

    @PutMapping("/{id}/update")
    MarcaDTO updateMarca(@PathVariable Long id, @RequestParam String nombre);

    @DeleteMapping("/{id}")
    void deleteMarca(@PathVariable Long id);
}
