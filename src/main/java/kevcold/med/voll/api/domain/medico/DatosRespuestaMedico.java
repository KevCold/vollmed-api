package kevcold.med.voll.api.domain.medico;

import kevcold.med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id, String nombre, String email, String telefono, String documento,
        DatosDireccion direccion) {

}
