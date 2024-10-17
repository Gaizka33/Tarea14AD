package ej2_2;

import java.io.File;

public class MainHijo1erproceso {

	public static void main(String[] args) {
		File file = new File("fichero.txt");
		ManejarFicheros mf = new ManejarFicheros("a", file);
	}

}
