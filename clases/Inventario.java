package clases;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventario {

    private ArrayList<Componentes> componentes = new ArrayList<>();

    public void insertar(int cod, String nom, int cant, double precio){
       Scanner sc = new Scanner(System.in);

       while(true){
        try{
            if (nom.matches(".*\\d.*")){
                throw new IllegalArgumentException("El nombre no puede contener números.");
            }
            
        componentes.add(new Componentes(nom, cod, cant, precio));
        System.out.println("Componente registrado correctamente.");
        break;
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
            System.out.println("Ingrese un nombre válido");
            nom = sc.nextLine();
        }
    }
    }

    public void info(Componentes c){//Para usarlo en listar y buscar
        System.out.println(
            "Código: " + c.getCodigo() + " | Nombre: " + c.getNombre() +
            " | Cantidad: " + c.getCantidad() + " | Precio: S/ " + c.getPrecio()
        );
    }

    public void listar(){//Muestra todos los componentes
        if (componentes.isEmpty()) {
            System.out.println("No hay componentes registrados.");
            return;
        }
        
        System.out.println("\n---LISTA DE COMPONENTES---");
        for (Componentes c : componentes) { info(c); }
    }

    public Componentes buscar(int codigo){ //Busqueda por codigo
        for (Componentes c : componentes){
            if (c.getCodigo() == codigo){
                return c;
            }
        }
        return null;
    }

    public Componentes buscar(String nombre){ //Busqueda por nombre
        for (Componentes n : componentes){
                if (n.getNombre().equalsIgnoreCase(nombre)){
                    return n;
                }
        }
        return null;
    }

    public void modificar(int codigo, String nuevoNombre, int nuevaCantidad, double nuevoPrecio){
        Componentes c = buscar(codigo);
        if (c != null) {
            c.setNombre(nuevoNombre);
            c.setCantidad(nuevaCantidad);
            c.setPrecio(nuevoPrecio);
            System.out.println("Componente modificado correctamente.");
        } else {
            System.out.println("No se encontró un componente con ese código.");
        }
    }

    //Buscar por nombre(sobrecarga), Modificar, Eliminar 
}
