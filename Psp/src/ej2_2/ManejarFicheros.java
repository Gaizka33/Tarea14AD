package ej2_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManejarFicheros {
	private static final File usedfiletoread = new File("fichero.txt");
	private static final File usedfiletowrite  = new File("ficherocreado.txt");
	private String chosencharacter;
	private static FileReader conexion;
	private static BufferedReader read;
	private static String line;
	private static FileWriter conexionwrite;
	private static BufferedWriter write;

	public ManejarFicheros(String chosencharacter) {
		this.chosencharacter = chosencharacter;
	}

	public void readfile() {
		try {
			conexion = new FileReader(usedfiletoread);
			read = new BufferedReader(conexion);
			while ((line = read.readLine()) != null) {
				if (line.equalsIgnoreCase(chosencharacter)) {
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cerrarArchivo() {

	}
}
