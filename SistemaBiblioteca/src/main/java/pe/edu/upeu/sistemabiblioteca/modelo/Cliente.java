package pe.edu.upeu.sistemabiblioteca.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "dni", nullable = false, length = 8)
    @NotBlank(message = "El DNI no puede estar vacío.")
    @Size (min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 160, message = "El nombre debe tener entre 2 y 160 caracteres.")
    @Column(name = "nombres", nullable = false, length = 160)
    private String nombres;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(min = 2, max = 160, message = "El apellido debe tener entre 2 y 160 caracteres.")
    @Column(name = "apellido", nullable = false, length = 160)
    private String apellido;

    @NotBlank(message = "La dirección no puede estar vacía.")
    @Size(min = 5, max = 160, message = "La dirección debe tener entre 5 y 160 caracteres.")
    @Column(name = "direccion", nullable = false, length = 160)
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Size (min = 9, max = 9, message = "El telefono debe tener 9 caracteres")
    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

}
