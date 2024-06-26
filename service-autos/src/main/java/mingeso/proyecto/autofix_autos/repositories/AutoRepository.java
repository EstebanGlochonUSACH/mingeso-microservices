package mingeso.proyecto.autofix_autos.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mingeso.proyecto.autofix_autos.entities.Auto;
import mingeso.proyecto.autofix_autos.entities.Marca;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long>
{
	@Query("SELECT a FROM Auto a WHERE :patente IS NULL OR a.patente LIKE %:patente%")
	public Page<Auto> findAllMatchPatente(@Param("patente") String patente, Pageable pageable);

	public Optional<Auto> findByPatente(String patente);

	public Boolean existsByPatente(String patente);

	Long countByMarca(Marca marca);
}
