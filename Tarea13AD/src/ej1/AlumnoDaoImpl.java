package ej1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDaoImpl implements AlumnoDao {
	
	private static AlumnoDaoImpl instancia;
	
	static {
		instancia = new AlumnoDaoImpl();
	}
	
	public static AlumnoDaoImpl getInstancia() {
		return instancia;
	}
	
	
    @Override
    public int anadir(Alumno a) throws SQLException {
        String query = "INSERT INTO Alumno (NIA, NOMBRE, APELLIDO, GENERO, FECHANACIMIENTO, CICLO, CURSO, GRUPO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Pool.getConnection().prepareStatement(query)) {
            ps.setInt(1, a.getNia());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellido());
            ps.setString(4, String.valueOf(a.getGenero()));
            ps.setDate(5, a.getFechaNacimiento());
            ps.setString(6, a.getCiclo());
            ps.setString(7, a.getCurso());
            ps.setInt(8, a.getGrupo());
            return ps.executeUpdate();
        }
    }

    @Override
    public Alumno chuparPorPk(int id) throws SQLException {
        String query = "SELECT * FROM Alumno WHERE NIA = ?";
        try (PreparedStatement ps = Pool.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Alumno(
                        rs.getInt("NIA"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getString("GENERO").charAt(0),
                        rs.getDate("FECHANACIMIENTO"),
                        rs.getString("CICLO"),
                        rs.getString("CURSO"),
                        rs.getInt("GRUPO")
                    );
                }
            }
        }
        return null; // Devuelve null si no se encuentra el alumno
    }

    @Override
    public List<Alumno> chuparTodo() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM Alumno";
        try (Statement statement = Pool.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Alumno a = new Alumno(
                    rs.getInt("NIA"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("GENERO").charAt(0),
                    rs.getDate("FECHANACIMIENTO"),
                    rs.getString("CICLO"),
                    rs.getString("CURSO"),
                    rs.getInt("GRUPO")
                );
                alumnos.add(a);
            }
        }
        return alumnos;
    }

    @Override
    public int modificar(Alumno a) throws SQLException {
        String query = "UPDATE Alumno SET NOMBRE = ?, APELLIDO = ?, GENERO = ?, FECHANACIMIENTO = ?, CICLO = ?, CURSO = ?, GRUPO = ? WHERE NIA = ?";
        try (PreparedStatement ps = Pool.getConnection().prepareStatement(query)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, String.valueOf(a.getGenero()));
            ps.setDate(4, a.getFechaNacimiento());
            ps.setString(5, a.getCiclo());
            ps.setString(6, a.getCurso());
            ps.setInt(7, a.getGrupo());
            ps.setInt(8, a.getNia());
            return ps.executeUpdate();
        }
    }

    @Override
    public void borrar(int id) throws SQLException {
        String query = "DELETE FROM Alumno WHERE NIA = ?";
        try (PreparedStatement ps = Pool.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
