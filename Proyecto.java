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
            opcion = scanner.nextInt();
        }while(opcion != 0);
    }
}