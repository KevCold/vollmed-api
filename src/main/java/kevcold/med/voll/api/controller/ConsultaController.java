package kevcold.med.voll.api.controller;

import jakarta.validation.Valid;
import kevcold.med.voll.api.domain.consulta.AgendaDeConsultaService;
import kevcold.med.voll.api.domain.consulta.DatosAgendarConsulta;
import kevcold.med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import kevcold.med.voll.api.domain.consulta.DatosDetalleConsulta;
import kevcold.med.voll.api.infra.errors.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos) throws ValidacionDeIntegridad {
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }
}
