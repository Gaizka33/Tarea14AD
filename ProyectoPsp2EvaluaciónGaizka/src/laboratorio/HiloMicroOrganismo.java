package laboratorio;

public class HiloMicroOrganismo extends Thread {
	private int id;
	private Monitor m;
	private final String REPOSO = "Microorganismo " + id + " en reposo";
	private final String REQUEST = "Microorganismo " + id + " quiere absorber nutrientes";

	public HiloMicroOrganismo(int i, Monitor m) {
		this.id = i;
		this.m = m;
	}

	@Override
	public void run() {
		while (true) {
			System.out.println(REQUEST);
			if (m.comerNutriente(id)) {
				System.out.println("Microorganismo " + id + " termino");
				System.out.println(REPOSO);
				m.reponerNutrientes(id);
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			} else {
				System.out.println("Microorganismo " + id + " intento comer, pero no pudo");
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
