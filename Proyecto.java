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
                    
                    if(inventario.buscar(cod) != null){
                        System.out.println("\nNo se permiten códigos repetidos");
                        break;
                    }

                    scanner.nextLine();

                    System.out.print("Nombre: ");
                    String nom = scanner.nextLine();

                    System.out.print("Cantidad: ");
                    int cant = scanner.nextInt();

                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();

                    inventario.insertar(cod, nom, cant, precio);
                    break;

                case 2:
                    inventario.listar();
                    break;

                case 3:
                    int op;
                    do{                  
                    System.out.println("\n---BÚSQUEDA---");
                    System.out.println("\n1. Por código");
                    System.out.println("2. Por nombre");
                    System.out.println("0. Regresar");                    
                    System.out.print("\nElija una opción: ");
                    op = scanner.nextInt();

                        switch (op){
                            case 0:
                                break;
                            case 1:
                                System.out.print("\nIngrese el código del componente: ");
                                cod = scanner.nextInt();
                                Componentes b = inventario.buscar(cod);

                                if(b == null){
                                    System.out.println("\nNo se encontró un componente con ese código.");
                                    break;
                                }
                                System.out.println("\nComponente encontrado: ");
                                inventario.info(b);
                                break;

                            case 2:
                                break;
                            
                            default:
                                System.out.print("\nElija una opcion valida\n");
                                break;
                        }
                    }   while(op != 0);
                    break;

                case 4:
                    break;

                case 5:
                    break;

                default:
                    System.out.print("\nElija una opcion valida\n");
                    break;
                    
            }
        }while(opcion != 0);
    }
}