package proyectoPsp;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class Main {

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
    private static String capturarSalidaProceso(Process proceso) throws IOException {
        StringBuilder salida = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        
        String linea;
        while ((linea = reader.readLine()) != null) {
            salida.append(linea).append("\n");
        }

        return salida.toString();
    }

    // Captura la salida de error del proceso
    private static String capturarErrorProceso(Process proceso) throws IOException {
        StringBuilder error = new StringBuilder();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
        
        String linea;
        while ((linea = errorReader.readLine()) != null) {
            error.append(linea).append("\n");
        }

        return error.toString();
    }

    // Copia el archivo .class a la ubicación especificada
    private static void copiarArchivoaClass(String archivoJava, String destino) throws IOException {
        String nombreClass = archivoJava.substring(0, archivoJava.lastIndexOf('.')) + ".class";
        Path origen = Paths.get(nombreClass);
        Path destinoPath = Paths.get(destino, nombreClass);
        Files.copy(origen, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("Archivo .class copiado a: " + destinoPath.toString());
    }

    // Método para leer la entrada del usuario en un hilo separado
    private static void leerEntradaUsuario(Process proceso) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String comando;
            while ((comando = reader.readLine()) != null) {
                if ("salir".equalsIgnoreCase(comando)) {
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

            // Iniciar hilo para leer la entrada del usuario
            new Thread(() -> leerEntradaUsuario(proceso)).start();

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
                System.out.println("La compilación falló.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
 