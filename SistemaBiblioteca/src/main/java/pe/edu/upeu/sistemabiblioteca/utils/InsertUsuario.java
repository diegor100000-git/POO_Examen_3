package pe.edu.upeu.sistemabiblioteca.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
@RequiredArgsConstructor
public class InsertUsuario implements CommandLineRunner {

    private final DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection con = dataSource.getConnection()) {

            String sql = "INSERT INTO usuario (nombre_usuario, clave, estado) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "admin2");
            ps.setString(2, "123");
            ps.setString(3, "activo");

            ps.executeUpdate();

            System.out.println("Usuario insertado correctamente usando CommandLineRunner.");
        }
        catch (Exception e) {
            System.out.println("⚠ Error insertando usuario: " + e.getMessage());
        }
    }
}
