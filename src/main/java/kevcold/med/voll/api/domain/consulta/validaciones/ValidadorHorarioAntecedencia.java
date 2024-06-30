package kevcold.med.voll.api.domain.consulta.validaciones;

import kevcold.med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import kevcold.med.voll.api.infra.errors.ValidacionDeIntegridad;
import kevcold.med.voll.api.repository.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamiento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsulta {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelamientoConsulta datos) {
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaHoras < 24) {
            throw new ValidacionDeIntegridad("La consulta solo puede ser cancelada con una" +
                    "antecedencia minima de 24h");
        }

    }
}
