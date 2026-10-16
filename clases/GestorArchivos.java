package clases;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestorArchivos {

    private static final String NOMBRE_ARCHIVO = "inventario.csv";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 1. Método para GUARDAR todos los componentes en el archivo CSV
    public static void guardarEnArchivo(Inventario inventario) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, false);
             PrintWriter pw = new PrintWriter(fw)) {

            for (Componentes c : inventario.getComponentes()) {
                pw.println(c.getCodigo() + ";" + 
                           c.getNombre() + ";" + 
                           c.getCantidad() + ";" + 
                           c.getPrecio() + ";" + 
                           c.getCategoria() + ";" + 
                           c.getFecha().format(FORMATO_FECHA)); // Formato yyyy-MM-dd
            }

        } catch (Exception e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }

    // 2. Método para CARGAR los componentes desde el archivo CSV al iniciar
    public static void cargarDesdeArchivo(Inventario inventario) {
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) {
            return;
        }

        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(";");

                if (datos.length == 6) {
                    int codigo = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    int cantidad = Integer.parseInt(datos[2]);
                    double precio = Double.parseDouble(datos[3]);
                    String categoria = datos[4];
                    
                    // Parseamos solo la fecha yyyy-MM-dd
                    LocalDate fechaCorta = LocalDate.parse(datos[5], FORMATO_FECHA);
                    LocalDateTime fecha = fechaCorta.atStartOfDay();

                    Componentes comp = new Componentes(nombre, codigo, cantidad, precio, categoria);
                    comp.setFecha(fecha);

                    inventario.getComponentes().add(comp);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo de datos: " + e.getMessage());
        }
    }
}