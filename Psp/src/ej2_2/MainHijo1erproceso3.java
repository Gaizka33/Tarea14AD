package ej2_2;

import java.io.File;

public class MainHijo1erproceso3 {

	public static void main(String[] args) {
		File file = new File("fichero3.txt");
		ManejarFicheros mf = new ManejarFicheros("i", file);
	}

}
