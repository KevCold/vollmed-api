package kevcold.med.voll.api.repository;

import kevcold.med.voll.api.domain.consulta.Consulta;
import kevcold.med.voll.api.domain.direccion.DatosDireccion;
import kevcold.med.voll.api.domain.medico.DatosRegistroMedico;
import kevcold.med.voll.api.domain.medico.Especialidad;
import kevcold.med.voll.api.domain.medico.Medico;
import kevcold.med.voll.api.domain.paciente.DatosRegistroPaciente;
import kevcold.med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class IMedicoRepositoryTest {

    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia retornar nulo si el medico se encuentra en consulta en ese horario")
    void seleccionarMedicoPorEspecialidadEnFechaEscenario1() {
        // given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("Kevin Nuñez", "seidnez@gmail.com", "1081905312", Especialidad.PEDIATRIA);
        //when
        var medicoLibre = medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(Especialidad.PEDIATRIA, proximoLunes10H);
        //Then
        assertThat(medicoLibre).isEqualTo(medico);

    }

    @Test
    @DisplayName("Deberia retornar un medico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoPorEspecialidadEnFechaEscenario2() {
        //given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("Kevin Nuñez", "seidnez@gmail.com", "1081905312", Especialidad.PEDIATRIA);
        var paciente = registrarPaciente("Kevin Nuñez", "zaza34559@gmail.com", "1081915313");
        registrarConsulta(medico, paciente, proximoLunes10H);
        //when
        var medicoLibre = medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(Especialidad.PEDIATRIA, proximoLunes10H);
        //then
        assertThat(medicoLibre).isNull();

    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "23332222",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private Paciente registrarPaciente(String nombre, String email, String documentoIdentidad) {
        var paciente = new Paciente(datosPaciente(nombre, email, documentoIdentidad));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documentoIdentidad) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "345675434",
                documentoIdentidad,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "local",
                "azul",
                "acapulco",
                "321",
                "12"
        );
    }

}



