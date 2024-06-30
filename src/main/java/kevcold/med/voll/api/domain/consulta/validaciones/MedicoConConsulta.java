package kevcold.med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import kevcold.med.voll.api.domain.consulta.DatosAgendarConsulta;
import kevcold.med.voll.api.repository.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {
    @Autowired
    private IConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datos) {
        if (datos.idMedico() == null) {
            return;
        }
        var medicoConConsulta = consultaRepository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());

        if (medicoConConsulta) {
            throw new ValidationException("El medico seleccionado ya tiene una consulta en ese horario");
        }
    }
}