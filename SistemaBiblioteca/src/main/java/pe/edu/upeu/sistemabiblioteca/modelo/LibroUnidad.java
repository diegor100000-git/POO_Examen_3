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
@Table(name = "libro_unidad")
public class LibroUnidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro_unidad")
    private Long idLibroUnidad;

    @ManyToOne
    @JoinColumn (name = "id_libro", nullable = false)
    private Libro libro;

    @Column(name = "codigo_unico", nullable = false)
    private double codigoUnico;

    @Column(name = "estado", nullable = false, length = 20)
    private String Estado;
}
