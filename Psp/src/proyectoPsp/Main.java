package proyectoPsp;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class Main {
	  private static boolean compilarArchivo(String archivoJava) throws IOException, InterruptedException {
		// Creamos un ProcessBuilder para ejecutar el comando javac con el archivo Java
	    ProcessBuilder processBuilder = new ProcessBuilder("javac", archivoJava);
	    // Redirigimos la salida de error a la salida estándar
	    processBuilder.redirectErrorStream(true);

	    /*
	    Iniciamos el proceso de compilación
	    Guardamos el tiempo de inicio
	    */
	    long inicioTiempo = System.currentTimeMillis();
	    Process proceso = processBuilder.start();
	        
	    // Capturamos la salida del proceso
	    String salida = capturarSalidaProceso(proceso);
	    System.out.println(salida);

	    // Esperamos a que el proceso termine y obtenemos el código de salida
	    boolean exitStatus = proceso.waitFor() == 0;

	    // Guardamos el tiempo de fin
	    long finTiempo = System.currentTimeMillis();
	    // Calculamos el tiempo total
	    long tiempoEjecucion = finTiempo - inicioTiempo;
	    System.out.println("Tiempo de ejecución: " + tiempoEjecucion + " ms");

	    return exitStatus; // Retornamos el estado de la compilación
	}

	private static String capturarSalidaProceso(Process proceso) throws IOException {
	     // Usamos un StringBuilder para almacenar la salida
	     StringBuilder salida = new StringBuilder();
	     // Creamos un lector para la salida del proceso
	     BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream())); 

	     String linea;
	     // Leemos cada línea de la salida estándar
	    while ((linea = reader.readLine()) != null) {
	    	 // Agregamos cada línea a la salida
	         salida.append(linea).append("\n");
	    }

	    return salida.toString(); // Retornamos la salida completa del proceso
	}

    private static void copiarArchivoaClass(String archivoJava, String destino) throws IOException {
        // Generamos el nombre del archivo .class
        String nombreClass = archivoJava.substring(0, archivoJava.lastIndexOf('.')) + ".class";
        
        // Copiamos el archivo .class a la ubicación especificada
        Path origen = Paths.get(nombreClass);
        Path destinoPath = Paths.get(destino, nombreClass);
        Files.copy(origen, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("Archivo .class copiado a: " + destinoPath.toString());
    }
    
    public static void main(String[] args) {
    	// Verificamos que se haya pasado un argumento (el archivo Java y el destino)
        if (args.length < 2) {
            System.out.println("Uso: java CompiladorJava <archivo.java> <destino>");
        // Salimos del programa si no se pasan argumentos suficientes
            return;
        }
        // El archivo Java a compilar
        String archivoJava = args[0];
        // La ubicación donde se copiará el archivo .class
        String destino = args[1]; 
        try {
            // Llamamos al método que compila el archivo Java
            boolean compilacionExitosa = compilarArchivo(archivoJava);

            if (compilacionExitosa) {
            	// Si la compilación fue exitosa, copiamos el archivo .class
                copiarArchivoaClass(archivoJava, destino);
            } else {
                System.out.println("La compilación falló, error error onggggggggggggggggggg");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
