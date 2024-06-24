package kevcold.med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kevcold.med.voll.api.direccion.DatosDireccion;

public record DatosActualizarMedicos(
        @NotNull
        Long id,
        String nombre,
        String documento,
        @Valid
        DatosDireccion direccion) {
}
