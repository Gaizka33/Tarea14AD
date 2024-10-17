package ej2_2;

import java.io.File;

public class MainHijo1erproceso2 {

	public static void main(String[] args) {
		File file = new File("fichero2.txt");
		ManejarFicheros mf = new ManejarFicheros("e", file);
	}

}
