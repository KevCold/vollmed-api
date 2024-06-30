package kevcold.med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import kevcold.med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion) {
}
