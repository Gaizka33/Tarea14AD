package ej1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.*;

public class Pruebas {

	@Mock
	private Connection con;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private ResultSet mockResultSet;

	private AlumnoDaoImpl alumnoDao;

	@BeforeEach
	void setUp() throws SQLException {
		alumnoDao = AlumnoDaoImpl.getInstancia();
		con = Pool.getConnection(); // Simular conexión con el Pool
	}

	@Test
	void testAnadirAlumno() throws SQLException {
		Alumno alumno = new Alumno(1, "Juan", "Pérez", 'M', Date.valueOf("2000-01-01"), "Informática", "2A", 1);

		when(con.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);

		int result = alumnoDao.anadir(alumno);

		assertEquals(1, result); // Verificar que devuelve 1
		verify(mockPreparedStatement, times(1)).executeUpdate(); // Confirmar que se ejecutó la consulta
	}

	@Test
	void testChuparPorPk() throws SQLException {
		when(con.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true); // Simular que encuentra un alumno
		when(mockResultSet.getInt("NIA")).thenReturn(1);
		when(mockResultSet.getString("NOMBRE")).thenReturn("Juan");
		when(mockResultSet.getString("APELLIDO")).thenReturn("Pérez");

		Alumno result = alumnoDao.chuparPorPk(1);

		// Comprobar que no es nulo
		assertNotNull(result);
		assertEquals(1, result.getNia());
		assertEquals("Juan", result.getNombre());
		assertEquals("Pérez", result.getApellido());
	}
}
