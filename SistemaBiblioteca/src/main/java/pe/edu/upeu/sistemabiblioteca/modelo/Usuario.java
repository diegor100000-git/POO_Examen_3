package pe.edu.upeu.sistemabiblioteca.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 100, unique = true)
    private String nombreUsuario;

    @Column(name = "clave", nullable = false, length = 100)
    private String clave;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado;


}
