package laboratorio;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
	int numdenutrientes;
	List<Boolean> listaNutrientes = new ArrayList<Boolean>();

	public Monitor(int numeroDeMicoorganismos) {
		this.numdenutrientes = numeroDeMicoorganismos;
		crearNutrientes();
	}

	private void crearNutrientes() {
		for (int i = 0; i < numdenutrientes; i++) {
			listaNutrientes.add(true);

		}
	}

	public synchronized boolean comerNutriente(int id) {
		System.out.println("Microorganismo " + id + " absorbiendo");
		if (recorrerParaVerSiSePuede(id)) {
			return true;
		}
		return false;
	}

	public synchronized void reponerNutrientes(int id) {
		listaNutrientes.set(id, true);

		if (id + 1 > listaNutrientes.size()) {
			listaNutrientes.set(0, true);
		} else {
			listaNutrientes.set(id + 1, true);
		}

		listaNutrientes.set(id, true);

		System.out.println("Se repusieron los nutrientes: " + id + " y " + (id + 1));

	}

	private synchronized boolean recorrerParaVerSiSePuede(int id) {
		int contador = 0;

		if (id + 1 > listaNutrientes.size()) {
			for (int i = 0; i < listaNutrientes.size(); i++) {
				Boolean disponible = listaNutrientes.get(i);
				if (contador < 2) {

					if (disponible && (i == 0 || i == id)) {
						contador++;
						listaNutrientes.set(i, false);
						System.out.println("El MicroOrganismo " + id + " se ha comido el nutriente en posicion " + i);
					}
				} else {
					System.out.println(listaNutrientes);
					return true;
				}
			}
		} else {
			for (int i = 0; i < listaNutrientes.size(); i++) {
				Boolean disponible = listaNutrientes.get(i);
				if (contador < 2) {

					if (disponible && (i == id + 1 || i == id)) {
						contador++;
						listaNutrientes.set(i, false);
						System.out.println("El MicroOrganismo " + id + " se ha comido el nutriente en posicion " + i);
					}
				} else {
					System.out.println(listaNutrientes);
					return true;
				}
			}
		}

		return false;
	}
}
