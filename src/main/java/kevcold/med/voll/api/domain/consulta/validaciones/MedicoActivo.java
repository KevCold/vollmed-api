package kevcold.med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import kevcold.med.voll.api.domain.consulta.DatosAgendarConsulta;
import kevcold.med.voll.api.repository.IMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private IMedicoRepository medicoRepository;

    public void validar(DatosAgendarConsulta datos) {
        if (datos.idMedico() == null) {
            return;
        }
        var medicoActivo = medicoRepository.findActivoById(datos.idMedico());

        if (!medicoActivo) {
            throw new ValidationException("No se puede permitir agendar citas con medicos inactivos en el sistema");
        }
    }
}
