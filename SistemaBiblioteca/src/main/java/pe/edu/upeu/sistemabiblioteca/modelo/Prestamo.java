package pe.edu.upeu.sistemabiblioteca.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Long idPrestamo;

    @Column(name = "dni_cliente", nullable = false)
    private String dniCliente;



    @Column(name = "fecha_prestamo", nullable = false, length = 20)
    private String fechaPrestamo;

    @Column(name = "fecha_retorno", nullable = false, length = 20)
    private String fechaRetorno;

    @Column(name = "descripcion", nullable = false)
    private String Descripcion;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<DetallePrestamo> detallePrestamos;
}
