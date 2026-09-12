import java.util.Scanner;

import clases.Inventario;
import clases.Componentes;

public class Proyecto{

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Inventario inventario = new Inventario();
        int opcion;

        do{
            //Menu de opciones
            //Insertar, Listar, Buscar, Modificar, Eliminar, Salir
            System.out.println("\n---SISTEMA DE INVENTARIO DE COMPONENTES ELECTRONICOS(TEMPORAL)---");
            System.out.println("\n1. Insertar ");
            System.out.println("2. Listar ");
            System.out.println("3. Buscar ");
            System.out.println("4. Modificar ");
            System.out.println("5. Eliminar ");
            System.out.println("0. Salir ");
            System.out.print("\nEliga una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 0:
                    System.out.println("-Saliendo del sistema-");
                    break;
                case 1:
                    System.out.println();
                    System.out.print("Codigo: ");
                    int cod = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nombre: ");
                    String nom = scanner.nextLine();

                    System.out.print("Cantidad: ");
                    int cant = scanner.nextInt();

                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();

                    Componentes c = new Componentes(nom, cod, cant, precio);

                    inventario.insertar(c);
                    break;

                case 2:
                    inventario.Listar();
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                default:
                    break;

            }
        }while(opcion != 0);
    }
}