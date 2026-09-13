package clases;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Componentes> componentes = new ArrayList<>();

   
    public void insertar(int cod, String nom, int cant, double precio){
        componentes.add(new Componentes(nom, cod, cant, precio));
        System.out.println("Componente registrado correctamente.");
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

    //Buscar por nombre(sobrecarga), Modificar, Eliminar 
}
