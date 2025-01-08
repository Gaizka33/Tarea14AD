package hilos_1;

class ThreadCalculador extends Thread {
	public int suma = 0;
	private int[] array;
	private boolean finale = false;

	public ThreadCalculador(int[] array) {
		this.array = array;
	}

	@Override
	public void run() {
		for (int i : array) {
			suma += i;
		}
		finale = true;
	}

	public boolean aVerSiEstaTerminado() {
		return finale;
		
	}

}

public class Main {
	private int[] arrayNumeros = { 0, 1, 2, 32, 4, 5, 61, 7, 88, 91 };

	public static void main(String[] args) {
		Main m = new Main();
		ThreadCalculador t = new ThreadCalculador(m.arrayNumeros);
		t.start();
//		try {
//			t.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (t.aVerSiEstaTerminado() == false) {
			try {
				t.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(t.suma);

	}

}
