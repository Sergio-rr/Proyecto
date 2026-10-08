import clases.Componentes;
import clases.Inventario;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Proyecto{

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Inventario inventario = new Inventario();
        int opcion;

        do{
            //Menu de opciones
            //Insertar, Listar, Buscar, Modificar, Eliminar, Salir
            System.out.println("\n---SIPCE---");
            System.out.println("\n1. Insertar ");
            System.out.println("2. Listar ");
            System.out.println("3. Buscar ");
            System.out.println("4. Modificar ");
            System.out.println("5. Eliminar ");
            System.out.println("0. Salir ");
            System.out.print("\nEliga una opción: ");

            try{
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

                    scanner.nextLine();
                    System.out.print("Categoria: ");
                    inventario.Mostrarcategorias();
                    String categoria = scanner.nextLine();

                    if(categoria.equalsIgnoreCase("+1")){

                        System.out.println("Ingrese la nueva categoria: ");
                        String NueCat = scanner.nextLine();
                        inventario.NueCat(NueCat);
                        categoria = NueCat;
                    }

                    else if(!inventario.BuscarCat(categoria)){
                        System.out.println("No existe esa categoria");
                        break;
                    }
                    inventario.insertar(cod, nom, cant, precio, categoria);
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
                            scanner.nextLine();
                            System.out.print("\nIngrese el nombre del componente: ");
                            nom = scanner.nextLine();
                            Componentes n = inventario.buscar(nom);

                            if(n == null){
                                System.out.println("\nNo se encontró un componente con ese nombre.");
                                break;
                            }
                            System.out.println("\nComponente encontrado: ");
                            inventario.info(n);

                                
                            nom = scanner.nextLine();
                            break;
                          
                        default:
                            System.out.print("\nElija una opcion valida\n");
                            break;
                        }
                    }   while(op != 0);
                    break;

                case 4:

                    int mod;
                    do{
                        System.out.println("\n---MODIFICAR COMPONENTE---");
                        System.out.println("\n1. Modificar por código");
                        System.out.println("0. Regresar");
                        System.out.print("\nElija una opción: ");
                        mod = scanner.nextInt();

                        switch (mod){
                            case 0:
                                break;
                            case 1:
                                System.out.print("\nIngrese el código del componente a modificar: ");
                                cod = scanner.nextInt();
                                Componentes b = inventario.buscar(cod);


                                if(b == null){
                                    System.out.println("\nNo se encontró un componente con ese código.");
                                    break;
                                }

                                System.out.println("\nComponente a modificar: ");
                                inventario.info(b);
                                System.out.println();

                                System.out.println("Deseas modificar el nombre, cantidad, precio o todo el componente?");
                                System.out.println("1. Nombre");
                                System.out.println("2. Cantidad");  
                                System.out.println("3. Precio");
                                System.out.println("4. Todo el componente");
                                System.out.println("0. Regresar");
                                System.out.print("\nElija una opción: ");
                                int opMod = scanner.nextInt();

                                switch (opMod){
                                    case 1:
                                        scanner.nextLine();
                                        System.out.print("Ingrese el nuevo nombre: ");
                                        nom = scanner.nextLine();
                                        b.setNombre(nom);
                                        System.out.println("\nNombre modificado correctamente.");
                                        break;
                                    case 2:
                                        System.out.print("Ingrese la nueva cantidad: ");
                                        cant = scanner.nextInt();
                                        b.setCantidad(cant);
                                        System.out.println("\nCantidad modificada correctamente.");
                                        break;
                                    case 3:
                                        System.out.print("Ingrese el nuevo precio: ");
                                        precio = scanner.nextDouble();
                                        b.setPrecio(precio);
                                        System.out.println("\nPrecio modificado correctamente.");
                                        break;
                                    case 4:
                                        scanner.nextLine();
                                        System.out.print("Ingrese el nuevo nombre: ");
                                        nom = scanner.nextLine();
                                        System.out.print("Ingrese la nueva cantidad: ");
                                        cant = scanner.nextInt();
                                        System.out.print("Ingrese el nuevo precio: ");
                                        precio = scanner.nextDouble();
                                        inventario.modificar(cod, nom, cant, precio);
                                        break;
                                    case 0:
                                        System.out.println("\nRegresando al menú principal.");
                                        break;
                                    default:
                                    System.out.print("\nElija una opcion valida\n");
                                    break;   
                                }

                        }
                    }   while(mod != 0);


                    break;

                case 5:

                    System.out.print("\nIngrese el código del componente a eliminar: ");
                    int del= scanner.nextInt();
                    Componentes d = inventario.buscar(del);
                    inventario.eliminar(d);                
                    break;

                default:
                    System.out.print("\nElija una opcion valida\n");
                    break;                  
            }
        }
            catch (InputMismatchException e){
                System.out.println("\nERROR: Debe ingresar un número.");
                scanner.nextLine();
                opcion = -1;
            }
        }while(opcion != 0);
    }
}
