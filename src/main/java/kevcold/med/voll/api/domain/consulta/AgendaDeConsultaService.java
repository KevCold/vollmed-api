package kevcold.med.voll.api.domain.consulta;

import kevcold.med.voll.api.domain.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import kevcold.med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import kevcold.med.voll.api.domain.medico.Medico;
import kevcold.med.voll.api.infra.errors.ValidacionDeIntegridad;
import kevcold.med.voll.api.repository.IConsultaRepository;
import kevcold.med.voll.api.repository.IMedicoRepository;
import kevcold.med.voll.api.repository.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {

        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        validadores.forEach(v-> v.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        if (medico == null) {
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario," +
                    "y especialidad");
        }

        var consulta = new Consulta(medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    public void cancelar(DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionDeIntegridad("Id de la consulta informado no existe");
        }
        validadoresCancelamiento.forEach(v ->v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {

        if(datosAgendarConsulta.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad");
        }
        return medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(datosAgendarConsulta.especialidad(),datosAgendarConsulta.fecha());
    }
}
