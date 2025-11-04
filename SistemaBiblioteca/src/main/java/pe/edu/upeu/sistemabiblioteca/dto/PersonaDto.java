package pe.edu.upeu.sistemabiblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonaDto {
    String dni, nombre, apellidoPaterno, apellidoMaterno;
}