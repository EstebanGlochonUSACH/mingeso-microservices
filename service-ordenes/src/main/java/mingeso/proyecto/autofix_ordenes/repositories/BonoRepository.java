package mingeso.proyecto.autofix_ordenes.repositories;

import java.time.LocalDateTime;
import java.util.List;
import mingeso.proyecto.autofix_ordenes.dtos.BonoGroupedByFechaInicioDTO;
import mingeso.proyecto.autofix_ordenes.entities.Bono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BonoRepository extends JpaRepository<Bono, Long>
{
	@Query("SELECT new mingeso.proyecto.autofix_ordenes.dtos.BonoGroupedByFechaInicioDTO(b.id_marca, b.monto, b.fechaInicio, b.fechaTermino, COUNT(b)) " +
		   "FROM Bono b GROUP BY b.id_marca, b.monto, b.fechaInicio, b.fechaTermino " +
		   "ORDER BY b.fechaInicio DESC")
	List<BonoGroupedByFechaInicioDTO> groupByFechaInicio();

	@Query("SELECT b FROM Bono b WHERE b.id_marca = :marca AND b.usado IS FALSE")
	List<Bono> findAllByMarca(Long marca);

	@Query("SELECT b FROM Bono b WHERE b.id_marca = :marca " +
		   "AND (b.fechaInicio <= COALESCE(:fecha, CURRENT_DATE) AND b.fechaTermino >= COALESCE(:fecha, CURRENT_DATE)) " +
		   "AND b.usado IS FALSE")
	List<Bono> findAllByMarcaAndFecha(
		@Param("marca") Long marca,
		@Param("fecha") LocalDateTime fecha
	);
}
