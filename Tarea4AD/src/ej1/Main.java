package ej1;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal que gestiona la creación de un fichero binario para almacenar
 * información sobre alumnos.
 * Hehco por Gaizka Martin
 */
public class Main {
	/** Archivo en el que se guardará la información. */
	private static File ficherodeseado;

	/** Scanner para la entrada de datos del usuario. */
	private static Scanner abielto = new Scanner(System.in);

	/** Lista de alumnos que se van a almacenar. */
	private static ArrayList<Alumno> Alumnos = new ArrayList<>(); // Inicializa la lista de alumnos

	/** Formato para parsear fechas de nacimiento. */
	static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	/**
	 * Método principal que inicia la ejecución del programa.
	 *
	 * @param args Argumentos de línea de comandos (no se utilizan).
	 */
	public static void main(String[] args) {
		DataOutputStream escribir = null; // Inicializa DataOutputStream a null

		try {
			// Solicita al usuario el nombre y la dirección del fichero
			System.out.println("Dime el nombre y dirección del fichero");
			ficherodeseado = new File(abielto.next(), abielto.next());

			// Crea un flujo de salida para escribir en el fichero
			FileOutputStream conexion = new FileOutputStream(ficherodeseado);
			escribir = new DataOutputStream(conexion);

			// Ciclo para recoger datos de 5 alumnos
			for (int i = 0; i < 5; i++) {
				// Solicita la fecha de nacimiento del alumno
				System.out.println("Dame la fecha de nacimiento del alumno (dd-MM-yyyy): ");
				String fechaNacimientoString = abielto.next();
				LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoString, formato); // Parsea la fecha

				// Solicita los datos del alumno
				System.out.println(
						"Ahora Dame los datos del alumno (Nombre, Apellidos, Ciclo, Curso, Grupo, NIA y Genero): ");

				String nombre = abielto.next(); // Nombre del alumno
				String apellidos = abielto.next(); // Apellidos del alumno
				String ciclo = abielto.next(); // Ciclo que cursa
				String curso = abielto.next(); // Curso actual
				String grupo = abielto.next(); // Grupo al que pertenece
				int nia = abielto.nextInt(); // Número de identificación del alumno
				char genero = abielto.next().charAt(0); // Género del alumno

				// Crea un objeto Alumno con los datos recogidos
				Alumno alumno = new Alumno(nombre, apellidos, ciclo, curso, grupo, nia, genero, fechaNacimiento);
				Alumnos.add(alumno); // Añade el alumno a la lista

				// Escribe los datos del alumno en el fichero
				escribir.writeUTF(alumno.getNombre());
				escribir.writeUTF(alumno.getApellidos());
				escribir.writeUTF(alumno.getCiclo());
				escribir.writeUTF(alumno.getCurso());
				escribir.writeUTF(alumno.getGrupo());
				escribir.writeInt(alumno.getNia());
				escribir.writeChar(alumno.getGenero());

				// Convierte la fecha de nacimiento a milisegundos y la escribe en el fichero
				long fechaNacimientoMillis = fechaNacimiento.atStartOfDay().toInstant(java.time.ZoneOffset.UTC)
						.toEpochMilli();
				escribir.writeLong(fechaNacimientoMillis); // Escribe la fecha en el fichero
			}
		} catch (Exception e) {
			e.printStackTrace(); // Imprime la traza de la excepción en caso de error
		} finally {
			// Cierra el DataOutputStream
			try {
				if (escribir != null) {
					escribir.close();
				}
			} catch (Exception e) {
				e.printStackTrace(); // Imprime la traza de la excepción al cerrar
			}
		}
	}
}
