package pe.edu.upeu.sistemabiblioteca.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long idLibro;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;


    @Column(name = "cantidad_ejemplares", nullable = false)
    private Double CantidadEjemplares;

    @NotNull(message = "Categoria no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName =
            "id_categoria",
            nullable = false, foreignKey = @ForeignKey(name =
            "FK_CATEGORIA_LIBRO") )
    private Categoria categoria;

    @NotNull(message = "Libro unidad no puede estar vacío")
    @ManyToOne@JoinColumn(name = "id_libro_unidad", referencedColumnName = "id_libro_unidad",
            nullable = false, foreignKey = @ForeignKey(name =
            "FK_UNIDADMEDIDA_PRODUCTO"))
    private LibroUnidad libroUnidad;
}
