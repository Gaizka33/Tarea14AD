package ej1;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static File ficherodeseado;
	private static Scanner abielto = new Scanner(System.in);
	private static ArrayList<Alumno> Alumnos = new ArrayList<>(); // Initialize the ArrayList
	static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static void main(String[] args) {
		DataOutputStream escribir = null;
		try {
			System.out.println("Dime el nombre y dirección del fichero");
			ficherodeseado = new File(abielto.next(), abielto.next());
			FileOutputStream conexion = new FileOutputStream(ficherodeseado);
			escribir = new DataOutputStream(conexion);

			for (int i = 0; i < 5; i++) {
				System.out.println("Dame la fecha de nacimiento del alumno (dd-MM-yyyy): ");
				String fechaNacimientoString = abielto.next();
				LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoString, formato);

				System.out.println(	"Ahora Dame los datos del alumno (Nombre, Apellidos, Ciclo, Curso, Grupo, NIA y Genero): ");

				String nombre = abielto.next();
				String apellidos = abielto.next();
				String ciclo = abielto.next();
				String curso = abielto.next();
				String grupo = abielto.next();
				int nia = abielto.nextInt();
				char genero = abielto.next().charAt(0);

				Alumno alumno = new Alumno(nombre, apellidos, ciclo, curso, grupo, nia, genero, fechaNacimiento);
				Alumnos.add(alumno);

				escribir.writeUTF(alumno.getNombre());
				escribir.writeUTF(alumno.getApellidos());
				escribir.writeUTF(alumno.getCiclo());
				escribir.writeUTF(alumno.getCurso());
				escribir.writeUTF(alumno.getGrupo());
				escribir.writeInt(alumno.getNia());
				escribir.writeChar(alumno.getGenero());

				// Antes de escribir en el archivo, convertimos la fecha a milisegundos
				long fechaNacimientoMillis = fechaNacimiento.atStartOfDay().toInstant(java.time.ZoneOffset.UTC)
						.toEpochMilli();

				// Luego, escribimos en el fichero
				escribir.writeLong(fechaNacimientoMillis);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (escribir != null) {
					escribir.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
