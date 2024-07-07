package kevcold.med.voll.api.domain.medico;

import jakarta.persistence.*;
import kevcold.med.voll.api.domain.direccion.Direccion;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.telefono = datosRegistroMedico.telefono();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizarDatos(DatosActualizarMedicos datosActualizarMedico) {
        if (datosActualizarMedico.nombre() != null){
            this.nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.documento() != null){
            this.documento = datosActualizarMedico.documento();
        }
        if (direccion.actualizarDatos(datosActualizarMedico.direccion()) != null){
            this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}