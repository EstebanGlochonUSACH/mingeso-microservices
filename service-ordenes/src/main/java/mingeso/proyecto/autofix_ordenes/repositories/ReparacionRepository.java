package mingeso.proyecto.autofix_ordenes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mingeso.proyecto.autofix_ordenes.entities.Reparacion;

@Repository
public interface ReparacionRepository extends JpaRepository<Reparacion, Long>
{
};
