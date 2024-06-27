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

    public void actualizarDatos(DatosActualizarMedicos datosActualizarMedicos) {
        if (datosActualizarMedicos.nombre() != null){
            this.nombre = datosActualizarMedicos.nombre();
        }
        if (datosActualizarMedicos.documento() != null){
            this.documento = datosActualizarMedicos.documento();
        }
        if (direccion.actualizarDatos(datosActualizarMedicos.direccion()) != null){
            this.direccion = direccion.actualizarDatos(datosActualizarMedicos.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}