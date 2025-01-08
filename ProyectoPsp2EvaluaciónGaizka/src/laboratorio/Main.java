package laboratorio;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner abielto = new Scanner(System.in);
		System.out.println("Cuantos Microorganismos va a haber?");
		int numero = abielto.nextInt();
		abielto.nextLine();
		abielto.close();
		Monitor m = new Monitor(numero);
		for (int i = 0; i < numero; i++) {
			new HiloMicroOrganismo(i, m).start();
		}
	}

}
