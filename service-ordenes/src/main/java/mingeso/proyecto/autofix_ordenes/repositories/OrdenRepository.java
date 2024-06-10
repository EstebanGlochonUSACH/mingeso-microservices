package mingeso.proyecto.autofix_ordenes.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import mingeso.proyecto.autofix_ordenes.entities.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>
{
	Page<Orden> findAll(Pageable pageable);

	List<Orden> findAll();

	@Query("SELECT o FROM Orden o ORDER BY o.fechaIngreso ASC")
	Page<Orden> findAllSorted(Pageable pageable);

	@Query("SELECT o FROM Orden o ORDER BY o.fechaIngreso ASC")
	List<Orden> findAllSorted();

	@Query("SELECT o FROM Orden o WHERE o.id_auto = :id_auto ORDER BY o.fechaIngreso ASC")
	List<Orden> findAllById_auto(Long id_auto);
}
