package clases;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GestorArchivos {

    private static final String NOMBRE_ARCHIVO = "inventario.csv";

    // 1. Método para GUARDAR todos los componentes en el archivo CSV
    public static void guardarEnArchivo(Inventario inventario) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, false); // false = sobrescribe el archivo con los datos actualizados
             PrintWriter pw = new PrintWriter(fw)) {

            // Escribimos cada componente en una línea formateada con punto y coma (;)
            for (Componentes c : inventario.getComponentes()) {
                pw.println(c.getCodigo() + ";" + 
                           c.getNombre() + ";" + 
                           c.getCantidad() + ";" + 
                           c.getPrecio() + ";" + 
                           c.getCategoria() + ";" + 
                           c.getFecha());
            }

        } catch (Exception e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }

    // 2. Método para CARGAR los componentes desde el archivo CSV al iniciar el programa
    public static void cargarDesdeArchivo(Inventario inventario) {
        File archivo = new File(NOMBRE_ARCHIVO);

        // Si el archivo no existe aún (primera vez que corre el programa), se crea solo
        if (!archivo.exists()) {
            return;
        }

        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.isEmpty()) continue;

                // Separamos los atributos por el punto y coma (;)
                String[] datos = linea.split(";");

                if (datos.length == 6) {
                    int codigo = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    int cantidad = Integer.parseInt(datos[2]);
                    double precio = Double.parseDouble(datos[3]);
                    String categoria = datos[4];
                    LocalDateTime fecha = LocalDateTime.parse(datos[5]);

                    // Recreamos el objeto componente
                    Componentes comp = new Componentes(nombre, codigo, cantidad, precio, categoria);
                    comp.setFecha(fecha); // Preservamos la fecha original guardada

                    // Lo insertamos directamente al ArrayList
                    inventario.getComponentes().add(comp);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo de datos: " + e.getMessage());
        }
    }
}