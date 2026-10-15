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
            System.out.println("5. Movimientos / Alertas de Stock");
            System.out.println("6. Eliminar");
            System.out.println("0. Salir ");
            System.out.print("\nEliga una opción: ");

            try{
            opcion = scanner.nextInt();

            switch (opcion) {
                case 0://Salir
                    System.out.println("-Saliendo del sistema-");
                    break;
                case 1: // Insertar
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

                    if(inventario.buscar(nom) != null){ 
                        System.out.println("\nNo se permiten nombres repetidos"); 
                        break; 
                    } 

                    System.out.print("Cantidad: "); 
                    int cant = scanner.nextInt(); 
                    if(cant < 0){ 
                        System.out.println("\nLa cantidad no puede ser negativa"); 
                        break; 
                    } 

                    System.out.print("Precio: "); 
                    double precio = scanner.nextDouble(); 
                    if(precio <= 0){ 
                        System.out.println("\nEl precio no puede ser igual o menor a 0"); 
                        break; 
                    } 

                    // --- SELECCIÓN POR NÚMERO DIRECTA Y SENCILLA ---
                    inventario.Mostrarcategorias(); 
                    System.out.print("\nElija el número de la categoría: "); 
                    int numCat = scanner.nextInt();
                    scanner.nextLine(); // Limpiamos el buffer para evitar fallos de lectura

                    String categoria = "";

                    // Si elige 1, 2, 3 o 4 (categorías existentes)
                    if (numCat >= 1 && numCat <= 4) {
                        categoria = inventario.getCategoriaPorIndice(numCat);
                    } 
                    // Si elige 5 (Crear nueva categoría)
                    else if (numCat == 5) {
                        System.out.print("Ingrese la nueva categoría: ");
                        String nuevaCat = scanner.nextLine();

                        if (inventario.BuscarCat(nuevaCat)) {
                            System.out.println("La categoría ingresada ya existe.");
                            break;
                        }
                        inventario.NueCat(nuevaCat);
                        categoria = nuevaCat;
                    } else {
                        System.out.println("Opción de categoría no válida.");
                        break;
                    }

                    // Se guarda en el inventario normalmente
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

                    if (opc >= 1 && opc <= 6) {
                        switch (opc) {
                            case 1: inventario.OrdenarCod(); break;
                            case 2: inventario.OrdenarNom(); break;
                            case 3: inventario.OrdenarCat(); break;
                            case 4: inventario.OrdenarStock(); break;
                            case 5: inventario.OrdenarPrecio(); break;
                            case 6: inventario.OrdenarFecha(); break;
                        }
                        inventario.listar();
                    } else {
                        System.out.println("\nERROR: Opción no válida. Debe ingresar un número entre 1 y 6.");
                    }
                    break;

                case 3: // Buscar
                    int op;
                    do {
                        System.out.println("\n--- BÚSQUEDA ---");
                        System.out.println("1. Por código");
                        System.out.println("2. Por nombre");
                        System.out.println("3. Por categoría");
                        System.out.println("0. Regresar");
                        System.out.print("\nElija una opción: ");
                        op = scanner.nextInt();

                        switch (op) {
                            case 0:
                                break;

                            case 1: // Búsqueda por código
                                System.out.print("\nIngrese el código del componente: ");
                                cod = scanner.nextInt();
                                Componentes b = inventario.buscar(cod);

                                if (b == null) {
                                    System.out.println("\nNo se encontró un componente con ese código.");
                                    break;
                                }
                                System.out.println("\nComponente encontrado: ");
                                inventario.info(b);
                                break;

                            case 2: // Búsqueda por nombre
                                scanner.nextLine();
                                System.out.print("\nIngrese el nombre del componente: ");
                                nom = scanner.nextLine();
                                Componentes n = inventario.buscar(nom);

                                if (n == null) {
                                    System.out.println("\nNo se encontró un componente con ese nombre.");
                                    break;
                                }
                                System.out.println("\nComponente encontrado: ");
                                inventario.info(n);
                                break;

                            case 3: // Búsqueda por categoría
                                inventario.Mostrarcategorias();
                                System.out.print("\nSeleccione el número de categoría a buscar: ");
                                int numCatBusqueda = scanner.nextInt();
                                scanner.nextLine(); // Limpiar el buffer

                                String catBusqueda = inventario.getCategoriaPorIndice(numCatBusqueda);

                                if (catBusqueda != null) {
                                    inventario.buscarPorCategoria(catBusqueda);
                                } else {
                                    System.out.println("Opción de categoría no válida.");
                                }
                                break;

                            default:
                                System.out.print("\nElija una opción válida\n");
                                break;
                        }
                    } while (op != 0);
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
                                    case 4: // Modificación individual de categoría por número
                                        inventario.Mostrarcategorias();
                                        System.out.print("\nSeleccione el número de la nueva categoría: ");
                                        int opCatMod = scanner.nextInt();
                                        scanner.nextLine(); // Limpiar el búfer

                                        if (opCatMod >= 1 && opCatMod <= 4) {
                                            categoria = inventario.getCategoriaPorIndice(opCatMod);
                                            b.setCategoria(categoria);
                                            System.out.println("\nCategoría modificada correctamente.");
                                        } else if (opCatMod == 5) {
                                            System.out.print("Ingrese la nueva categoría: ");
                                            String nuevaCat = scanner.nextLine().trim();
                                            if (inventario.BuscarCat(nuevaCat)) {
                                                System.out.println("La categoría ingresada ya existe.");
                                                break;
                                            }
                                            inventario.NueCat(nuevaCat);
                                            b.setCategoria(nuevaCat);
                                            System.out.println("\nCategoría modificada correctamente.");
                                        } else {
                                            System.out.println("Opción no válida.");
                                        }
                                        break;
                                    case 5:
                                        scanner.nextLine(); 
                                        System.out.print("Ingrese el nuevo nombre: "); 
                                        nom = scanner.nextLine(); 

                                        if(inventario.buscar(nom) != null && !b.getNombre().equalsIgnoreCase(nom)){ 
                                            System.out.println("Ya existe un componente con ese nombre"); 
                                            break; 
                                        } 

                                        System.out.print("Ingrese la nueva cantidad: "); 
                                        cant = scanner.nextInt(); 

                                        System.out.print("Ingrese el nuevo precio: "); 
                                        precio = scanner.nextDouble(); 

                                        // Selección por NÚMERO en la modificación total
                                        inventario.Mostrarcategorias(); 
                                        System.out.print("\nSeleccione el número de la nueva categoría: "); 
                                        int opCatTodo = scanner.nextInt();
                                        scanner.nextLine(); // Limpiar el buffer

                                        if (opCatTodo >= 1 && opCatTodo <= 4) {
                                            categoria = inventario.getCategoriaPorIndice(opCatTodo);
                                        } else if (opCatTodo == 5) {
                                            System.out.print("Ingrese la nueva categoría: ");
                                            String nuevaCat = scanner.nextLine().trim();
                                            if (inventario.BuscarCat(nuevaCat)) {
                                                System.out.println("La categoría ingresada ya existe");
                                                break;
                                            }
                                            inventario.NueCat(nuevaCat);
                                            categoria = nuevaCat;
                                        } else {
                                            System.out.println("Opción de categoría no válida.");
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
                
                case 5://Movimientos / Alertas de Stock
                    int opMov;
                    do {
                        System.out.println("\n--- MOVIMIENTOS Y ALERTAS DE STOCK ---");
                        System.out.println("1. Registrar Entrada (+ Reposición)");
                        System.out.println("2. Registrar Salida (- Venta / Despacho)");
                        System.out.println("3. Reporte de Alertas (Stock ≤ 5)");
                        System.out.println("0. Regresar");
                        System.out.print("\nElija una opción: ");
                        opMov = scanner.nextInt();

                        switch (opMov) {
                            case 0: // Regresar al menú principal
                                break;

                            case 1: // Registrar Entrada (+ Reposición)
                                System.out.print("\nIngrese el código del componente: ");
                                cod = scanner.nextInt();
                                Componentes compEntrada = inventario.buscar(cod);

                                if (compEntrada == null) {
                                    System.out.println("\nNo se encontró un componente con ese código.");
                                    break;
                                }

                                // Mostramos la información actual del componente antes de pedir la cantidad
                                System.out.println("\nComponente encontrado:");
                                inventario.info(compEntrada);

                                int stockPrevioEntrada = compEntrada.getCantidad();
                                System.out.print("\n¿Cuántas unidades ingresan al almacén?: ");
                                int ing = scanner.nextInt();

                                if (ing <= 0) {
                                    System.out.println("\nLa cantidad a ingresar debe ser mayor a 0.");
                                    break;
                                }

                                inventario.registrarEntrada(cod, ing);

                                System.out.println("\n==================================================");
                                System.out.println("  ¡ENTRADA REGISTRADA CON ÉXITO!");
                                System.out.println("  Componente: " + compEntrada.getNombre());
                                System.out.println("  Stock anterior: " + stockPrevioEntrada + " | Ingreso: +" + ing + " | Stock actual: " + compEntrada.getCantidad());
                                System.out.println("==================================================");
                                break;

                            case 2: // Registrar Salida (- Venta / Despacho)
                                System.out.print("\nIngrese el código del componente: ");
                                cod = scanner.nextInt();
                                Componentes compSalida = inventario.buscar(cod);

                                if (compSalida == null) {
                                    System.out.println("\nNo se encontró un componente con ese código.");
                                    break;
                                }

                                // Mostramos la información actual del componente antes de pedir la cantidad
                                System.out.println("\nComponente encontrado:");
                                inventario.info(compSalida);

                                int stockPrevioSalida = compSalida.getCantidad();
                                System.out.print("\n¿Cuántas unidades se van a despachar?: ");
                                int sal = scanner.nextInt();

                                if (sal <= 0) {
                                    System.out.println("\nLa cantidad a despachar debe ser mayor a 0.");
                                    break;
                                }

                                int nuevoStock = inventario.registrarSalida(cod, sal);

                                if (nuevoStock == -2) {
                                    System.out.println("\nERROR: No hay stock suficiente para despachar esa cantidad. Stock disponible: " + compSalida.getCantidad());
                                    break;
                                }

                                System.out.println("\n==================================================");
                                System.out.println("  ¡SALIDA REGISTRADA CON ÉXITO!");
                                System.out.println("  Componente: " + compSalida.getNombre());
                                System.out.println("  Stock anterior: " + stockPrevioSalida + " | Salida: -" + sal + " | Stock actual: " + nuevoStock);
                                System.out.println("==================================================");

                                // Alerta automática de stock crítico (<= 5)
                                if (nuevoStock <= 5) {
                                    System.out.println("\n**************************************************");
                                    System.out.println("  ¡ALERTA DE STOCK CRÍTICO!");
                                    System.out.println("  El componente [" + compSalida.getNombre() + "] ha alcanzado un stock bajo");
                                    System.out.println("  (Quedan solo " + nuevoStock + " unidades en almacén).");
                                    System.out.println("  Se requiere reabastecimiento urgente.");
                                    System.out.println("**************************************************");
                                }
                                break;

                            case 3: // Reporte de Alertas (Consulta General ≤ 5)
                                System.out.println();
                                inventario.reporteStockMinimo();
                                break;

                            default:
                                System.out.println("\nElija una opción válida.");
                                break;
                        }
                    } while (opMov != 0);
                    break;
                case 6://Eliminar

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
