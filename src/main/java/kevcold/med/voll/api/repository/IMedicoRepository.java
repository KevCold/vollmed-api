package kevcold.med.voll.api.repository;

import kevcold.med.voll.api.domain.medico.Especialidad;
import kevcold.med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IMedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            select m from Medico m
            where m.activo = true and
            m.especialidad = :especialidad and
            m.id not in (
                select c.medico.id from Consulta c
                where c.fecha = :fecha
            )
            order by function('rand')
            """)
    Medico seleccionarMedicoPorEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

    @Query("""
            select m.activo
            from Medico m
            where m.id = :idMedico
            """)
    Boolean findActivoById(Long idMedico);
}
