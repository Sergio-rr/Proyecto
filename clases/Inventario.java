package clases;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventario {

    private ArrayList<Componentes> componentes = new ArrayList<>();
    private ArrayList<String> categorias = new ArrayList<>();

    public Inventario() {
        categorias.add("Microcontroladores");
        categorias.add("Sensores");
        categorias.add("Componentes Pasivos");
        categorias.add("Semiconductores");
    }
    public void insertar(int cod, String nom, int cant, double precio, String cat){
       Scanner sc = new Scanner(System.in);

       while(true){
        try{
            if (nom.matches(".*\\d.*")){
                throw new IllegalArgumentException("El nombre no puede contener números.");
            }
            
        componentes.add(new Componentes(nom, cod, cant, precio, cat));
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
            " | Cantidad: " + c.getCantidad() + " | Precio: S/ " + c.getPrecio() + " | Nombre: " + c.getCategoria()+ " | Fecha: " + c.getFecha()
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

    public void eliminar(Componentes c){
        if (c != null) {
            componentes.remove(c);
            System.out.println("Componente eliminado correctamente.");
        } else {
            System.out.println("No se encontró un componente con ese código.");
        }
    }

    public void Mostrarcategorias(){//Muestra las categorias disponibles
        if (categorias.isEmpty()) {
        System.out.println("No hay categorias registradas.");
        return;
        }
        System.out.println("\n---CATEGORIAS---");
        for (String ca : categorias) {System.out.println(" - " + ca);
        }
    }

    public boolean BuscarCat(String cate) {//Verifica si existe o no la categoria ingresada
        for (String ca : categorias) {
            if (ca.equalsIgnoreCase(cate)) {
                return true;
            }      
        }
        return false;
    }
    public void NueCat(String nueCat) { // Añade una nueva categoria
        categorias.add(nueCat); 
        System.out.println("Categoría '" + nueCat + "' registrada con éxito.");
    }
}
