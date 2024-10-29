package proyectoPsp;

import java.io.*;
import java.nio.file.*;

public class Main {
	private static String linea;

    // Compila un archivo Java y captura su salida
    private static boolean compilarArchivo(String archivoJava) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("javac", archivoJava);
        // Redirigimos la salida de error a la salida estándar
        processBuilder.redirectErrorStream(false);

        // Iniciamos el proceso de compilación
        Process proceso = processBuilder.start();
        
        // Capturamos la salida y la salida de error
        String salida = capturarSalidaProceso(proceso);
        String error = capturarErrorProceso(proceso);

        // Esperamos a que el proceso termine y obtenemos el código de salida
        boolean exitStatus = proceso.waitFor() == 0;

        // Informamos al usuario de la salida del proceso
        System.out.println("Salida:\n" + salida);
        if (!exitStatus) {
            System.out.println("Errores:\n" + error);
        }

        return exitStatus; // Retornamos el estado de la compilación
    }

    // Captura la salida estándar del proceso
 // Captura la salida estándar del proceso
    private static String capturarSalidaProceso(Process proceso) throws IOException {
        // StringBuilder para construir la salida como una cadena
        StringBuilder salida = new StringBuilder();
        
        // Crea un BufferedReader para leer la salida estándar del proceso
        BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        
        String linea;
        // Lee la salida línea por línea hasta que no haya más líneas
        while ((linea = reader.readLine()) != null) {
            // Agrega cada línea leída al StringBuilder, seguida de un salto de línea
            salida.append(linea).append("\n");
        }

        // Retorna la salida completa como una cadena
        return salida.toString();
    }

    // Captura la salida de error del proceso
    private static String capturarErrorProceso(Process proceso) throws IOException {
        // StringBuilder para construir la salida de error como una cadena
        StringBuilder error = new StringBuilder();
        
        // Crea un BufferedReader para leer la salida de error del proceso
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
        
        String linea;
        // Lee la salida de error línea por línea hasta que no haya más líneas
        while ((linea = errorReader.readLine()) != null) {
            // Agrega cada línea leída al StringBuilder de error, seguida de un salto de línea
            error.append(linea).append("\n");
        }

        // Retorna la salida de error completa como una cadena
        return error.toString();
    }

    // Copia el archivo .class a la ubicación especificada por el usuario
    private static void copiarArchivoaClass(String archivoJava, String destino) throws IOException {
        // Genera el nombre del archivo .class a partir del nombre del archivo Java
        String nombreClass = archivoJava.substring(0, archivoJava.lastIndexOf('.')) + ".class";
        
        // Crea un objeto Path para la ubicación del archivo .class generado
        Path origen = Paths.get(nombreClass);
        
        // Crea un objeto Path para la ubicación de destino donde se copiará el archivo .class
        Path destinoPath = Paths.get(destino, nombreClass);
        
        // Copia el archivo .class al destino especificado, reemplazando cualquier archivo existente
        Files.copy(origen, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        
        // Informa al usuario que el archivo .class ha sido copiado con éxito
        System.out.println("Archivo .class copiado a: " + destinoPath.toString());
    }


    // Método para leer la entrada del usuario en un hilo separado
    private static void leerEntradaUsuario(Process proceso) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((linea = reader.readLine()) != null) {
                if ("salir".equalsIgnoreCase(linea)) {
                    proceso.destroy(); // Termina el proceso si se introduce "salir"
                    System.out.println("Proceso terminado por el usuario.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer la entrada del usuario: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("elol elol, yo fon linging");
            return; // Salimos del programa si no se pasan argumentos suficientes
        }

        String archivoJava = args[0];
        String destino = args[1]; 

        try {
            // Iniciar el proceso de compilación en un hilo
            ProcessBuilder processBuilder = new ProcessBuilder("javac", archivoJava);
            Process proceso = processBuilder.start();
            /*
            Creamos una clase anónima(Una clase anónima en Java es una clase que no tiene un nombre y se declara e instancia en una sola línea.
             Normalmente, se usa cuando necesitamos una implementación puntual y única de una interfaz o una clase abstracta, 
             sin necesidad de definir una clase completa por separado.) que implementa Runnable
	        */
//--------------------------------------------------------------------------
            Thread hiloLecturaUsuario = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Dentro del método run() llamamos a leerEntradaUsuario
                    leerEntradaUsuario(proceso);
                }
            });
//--------------------------------------------------------------------------
            // Iniciamos el hilo de forma explícita
            hiloLecturaUsuario.start();

            long inicioTiempo = System.currentTimeMillis();
            boolean compilacionExitosa = compilarArchivo(archivoJava);
            long finTiempo = System.currentTimeMillis();
            
            // Calcular y mostrar el tiempo de ejecución
            long tiempoEjecucion = finTiempo - inicioTiempo;
            System.out.println("Tiempo de ejecución: " + tiempoEjecucion + " ms");

            if (compilacionExitosa) {
                // Si la compilación fue exitosa, copiamos el archivo .class
                copiarArchivoaClass(archivoJava, destino);
            } else {
                System.out.println("La compilación fallo, Mueq mueq. ");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
 