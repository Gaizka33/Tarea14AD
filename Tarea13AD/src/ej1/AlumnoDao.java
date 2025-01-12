package ej1;

import java.sql.SQLException;
import java.util.List;

public interface AlumnoDao {
	
	int anadir (Alumno a) throws SQLException;
	
	Alumno chuparPorPk(int id) throws SQLException;
	
	List<Alumno> chuparTodo() throws SQLException;
	
	int modificar (Alumno a ) throws SQLException;
	
	void borrar (int id ) throws SQLException;
	
}
