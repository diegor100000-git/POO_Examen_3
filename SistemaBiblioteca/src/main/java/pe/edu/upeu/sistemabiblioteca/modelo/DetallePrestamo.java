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
@Table(name = "Detalle_prestamo")
public class DetallePrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_prestamo")
    private Long idDetallePrestamo;

    @ManyToOne
    @JoinColumn(name = "id_prestamo", referencedColumnName = "id_prestamo",
            nullable = false, foreignKey = @ForeignKey(name =
            "FK_DETALLE_PRESTAMO"))
    private Prestamo prestamo;
    @ManyToOne
    @JoinColumn(name = "id_libro_unidad", referencedColumnName = "id_libro_unidad",
            nullable = false, foreignKey = @ForeignKey(name =
            "FK_LIBRO_UNIDAD"))
    private LibroUnidad libroUnidad;

    @Column(name = "estado", nullable = false)
    private String estado;
}
