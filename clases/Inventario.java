package clases;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Componentes> componentes = new ArrayList<>();

   
         public void insertar(Componentes componentes){
        this.componentes.add(componentes);
        System.out.println("Componente registrado correctamente.");
    }

    public void Listar(){
        if (componentes.isEmpty()) {
            System.out.println("No hay componentes registrados.");
            return;
        }
        
        System.out.println("\n---LISTA DE COMPONENTES---");
        componentes.stream().forEach(c -> System.out.println(
            "Codigo: " + c.getCodigo() +" |  Nombre:" + c.getNombre() + 
            " |  Cantidad: " + c.getCantidad() + " |  Precio: " + c.getPrecio()));
    }
    
     //Buscar, Modificar, Eliminar
}
