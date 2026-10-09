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
                case 0://Salir
                    System.out.println("-Saliendo del sistema-");
                    break;
                case 1://Insertar
                    System.out.println();             
                    System.out.print("Codigo: ");
                    int cod = scanner.nextInt();
                    
                    if(inventario.buscar(cod) != null){//Verifica si existe el codigo
                        System.out.println("\nNo se permiten códigos repetidos");
                        break;
                    }

                    scanner.nextLine();

                    System.out.print("Nombre: ");
                    String nom = scanner.nextLine();

                    if(inventario.buscar(nom) != null){//Verifica si existe el nombre
                        System.out.println("\nNo se permiten nombres repetidos");
                        break;
                    }
                    
                    System.out.print("Cantidad: ");
                    int cant = scanner.nextInt();
                    if(cant < 0){//Verifica si la cantidad/stock es negativa //RF-38
                        System.out.println("\nLa cantidad no puede ser negativa");
                        break;
                    }
                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();
                    if(precio <= 0){//Verfica si el precio es <= a cero //RF-39
                        System.out.println("\nEl precio no puede ser igual o menor a 0");
                        break;
                    }
                    inventario.Mostrarcategorias();//Muestra las categorias
                    System.out.println(" - '+1': Nueva categoria ");
                    scanner.nextLine();
                    System.out.print("\nCategoria: ");
                    
                    String categoria = scanner.nextLine();

                    if(categoria.equalsIgnoreCase("+1")){

                        System.out.println("Ingrese la nueva categoria: ");
                        String NueCat = scanner.nextLine();

                        if(inventario.BuscarCat(NueCat)){
                           System.out.println("La categoria ingresada ya existe"); 
                           break;
                        }
                        inventario.NueCat(NueCat);
                        categoria = NueCat;
                    }

                    else if(!inventario.BuscarCat(categoria)){//Verifica si no existe la categoria
                        System.out.println("La categoria ingresada no existe");
                        break;
                    }
                    inventario.insertar(cod, nom, cant, precio, categoria);
                    break;
            
                case 2://Listar
                    System.out.println("---LISTAR COMPONENTES POR:---");
                    System.out.println("1. Codigo");
                    System.out.println("2. Nombres");
                    System.out.println("3. Categoria");
                    System.out.println("4. Cantidad de stock");
                    System.out.println("5. Precio");
                    System.out.println("6. Fecha de insercion");
                    System.out.print("\nElija una opción: " );
                    int opc = scanner.nextInt();

                    switch (opc) {
                        case 1:
                            inventario.OrdenarCod();
                            break;
                        case 2:
                            inventario.OrdenarNom();
                            break;
                        case 3:
                            inventario.OrdenarCat();
                            break;
                        case 4:
                            inventario.OrdenarStock();
                            break;
                        case 5:
                            inventario.OrdenarPrecio();
                            break;
                        case 6:
                            inventario.OrdenarFecha();
                            break;   
                        default:
                            System.out.println("Ingrese una opcion valida");
                            break;
                    }
                    if(opc >0 || opc < 7){
                        inventario.listar();
                    }     
                    break;

                case 3://Buscar
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

                case 4://Modificar

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
                                System.out.println("4. Categoria");
                                System.out.println("5. Todo el componente");
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
                                    case 4://Modificacion individual de la categoria
                                        scanner.nextLine();
                                        inventario.Mostrarcategorias();
                                        System.out.print("Ingrese la nueva categoria: ");
                                        categoria = scanner.nextLine();

                                        if(!inventario.BuscarCat(categoria)){
                                            System.out.println("La categoria ingresada no existe"); 
                                            break;
                                        }
                                        b.setCategoria(categoria);
                                        break;
                                    case 5:
                                        scanner.nextLine();
                                        System.out.print("Ingrese el nuevo nombre: ");
                                        nom = scanner.nextLine();
                                        if(inventario.buscar(nom) != null){
                                            System.out.println("Ya existe un componente con ese nombre"); 
                                            break;
                                        }
                                        System.out.print("Ingrese la nueva cantidad: ");
                                        cant = scanner.nextInt();
                                        System.out.print("Ingrese el nuevo precio: ");
                                        precio = scanner.nextDouble();
                                        scanner.nextLine();
                                        inventario.Mostrarcategorias();
                                        System.out.print("Ingrese la nueva categoria: ");//Modificacion de categoria
                                        categoria = scanner.nextLine();

                                        if(!inventario.BuscarCat(categoria)){//Verifica si la categoria no existe
                                            System.out.println("Debe ingresar una categoria existente"); 
                                            break;
                                        }

                                        inventario.modificar(cod, nom, cant, precio, categoria);
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

                case 5://Eliminar

                    System.out.print("\nIngrese el código del componente a eliminar: ");
                    int del= scanner.nextInt();
                    Componentes d = inventario.buscar(del);
                    inventario.info(d);
                    System.out.print("Esta seguro que desea eliminar este componente?(S/N):");//Confirmacion de eliminacion //RF-15
                        
                    scanner.nextLine();
                    String conf = scanner.nextLine();

                    if("s".equalsIgnoreCase(conf)){
                    inventario.eliminar(d);
                        System.out.println("Se elimino correctamente el componente");
                        break;
                    }
                    else if("n".equalsIgnoreCase(conf)){
                        System.out.println("Se cancelo la eliminación del componente");
                        break;
                    }
                    else{
                        System.out.println("Ingrese ua opción valida");
                    }
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
