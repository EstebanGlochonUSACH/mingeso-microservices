package mingeso.proyecto.autofix_reparaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mingeso.proyecto.autofix_reparaciones.entities.Reparacion;

@Repository
public interface ReparacionRepository extends JpaRepository<Reparacion, Long>
{};
