package kevcold.med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import kevcold.med.voll.api.domain.consulta.DatosAgendarConsulta;
import kevcold.med.voll.api.repository.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {

    @Autowired
    private IConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);

        var pacienteConConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente(),
                primerHorario, ultimoHorario);
        if (pacienteConConsulta) {
            throw new ValidationException("El paciente ya tiene una consulta agendada para ese dia");
        }
    }
}
