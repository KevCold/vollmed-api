package kevcold.med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import kevcold.med.voll.api.domain.consulta.DatosAgendarConsulta;
import kevcold.med.voll.api.repository.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas {
    @Autowired
    private IPacienteRepository pacienteRepository;

    public void validar(DatosAgendarConsulta datos) {
        if (datos.idPaciente() == null) {
            return;
        }
        var pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());

        if (!pacienteActivo) {
            throw new ValidationException("No se puede permitir agendas con pacientes inactivos" +
                    "en el sistema");
        }
    }
}
