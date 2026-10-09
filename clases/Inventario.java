package clases;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = c.getFecha().format(formato);

        System.out.println(
            "Código: " + c.getCodigo() + " | Nombre: " + c.getNombre() +
            " | Cantidad: " + c.getCantidad() + " | Precio: S/ " + c.getPrecio() + " | Categoria: " + c.getCategoria()+ " | Fecha: " + fechaFormateada
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

    public void OrdenarCod(){
        Collections.sort(componentes, (c1, c2) -> Integer.valueOf(c1.getCodigo()).compareTo(c2.getCodigo()));//RF-22
    }

    public void OrdenarNom(){
        Collections.sort(componentes, (c1, c2) -> c1.getNombre().compareTo(c2.getNombre()));//RF-23
    }

    public void OrdenarCat(){
        Collections.sort(componentes, (c1, c2) -> c1.getCategoria().compareTo(c2.getCategoria()));//RF-24
    }

    public void OrdenarStock(){
        Collections.sort(componentes, (c1, c2) -> Integer.valueOf(c1.getCantidad()).compareTo(c2.getCantidad()));//RF-26
    }

    public void OrdenarPrecio(){
        Collections.sort(componentes, (c1, c2) -> Double.compare(c1.getPrecio(), c2.getPrecio()));//RF-EXTRA
    }

    public void OrdenarFecha(){
        Collections.sort(componentes, (c1, c2) -> c1.getFecha().compareTo(c2.getFecha()));//RF-25
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

    public void buscarPorCategoria(String categoria) { //Busqueda por categoria
    boolean encontrado = false;
    System.out.println("\n--- COMPONENTES EN LA CATEGORÍA: " + categoria.toUpperCase() + " ---");
    for (Componentes c : componentes) {
        if (c.getCategoria().equalsIgnoreCase(categoria)) {
            info(c);
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("No se encontraron componentes en esta categoría.");
    }
}

    public void modificar(int codigo, String nuevoNombre, int nuevaCantidad, double nuevoPrecio, String nuevaCategoria){
        Componentes c = buscar(codigo);
        if (c != null) {
            c.setNombre(nuevoNombre);
            c.setCantidad(nuevaCantidad);
            c.setPrecio(nuevoPrecio);
            c.setCategoria(nuevaCategoria);
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

    public void Mostrarcategorias() {
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
            return;
        }
        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + ". " + categorias.get(i));
        }
        System.out.println((categorias.size() + 1) + ". [ + Crear nueva categoría ]");
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
        
    // Obtiene el nombre de la categoría según el número ingresado (1, 2, 3, etc.)
    public String getCategoriaPorIndice(int indice) {
        if (indice >= 1 && indice <= categorias.size()) {
            return categorias.get(indice - 1);
        }
        return null;
    }
        


    // Registrar entrada de stock (+ Reposición)
    public boolean registrarEntrada(int codigo, int cantidadIngreso) {
        Componentes c = buscar(codigo);
        if (c != null) {
            c.setCantidad(c.getCantidad() + cantidadIngreso);
            return true;
        }
        return false;
    }

    // Registrar salida de stock (- Venta / Despacho)
    // Retorna -1 si el código no existe, -2 si la salida supera el stock actual, o el nuevo stock
    public int registrarSalida(int codigo, int cantidadSalida) {
        Componentes c = buscar(codigo);
        
        if (cantidadSalida > c.getCantidad()) {
            return -2; // Stock insuficiente
        }
        c.setCantidad(c.getCantidad() - cantidadSalida);
        return c.getCantidad(); // Retorna el nuevo stock
    }

    // Generar reporte de componentes en estado crítico (Stock <= 5)
    public void reporteStockMinimo() {
        System.out.println("**************************************************");
        System.out.println("       REPORTE DE COMPONENTES CON STOCK MÍNIMO    ");
        System.out.println("**************************************************");
        
        int contador = 0;
        for (Componentes c : componentes) {
            if (c.getCantidad() <= 5) {
                System.out.println("Código: " + c.getCodigo() + " | Componente: " + c.getNombre() + " | Stock Actual: " + c.getCantidad());
                contador++;
            }
        }
        
        if (contador == 0) {
            System.out.println(" No hay componentes con stock crítico (≤ 5 unidades).");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Total de productos en estado crítico: " + contador);
        }
        System.out.println("**************************************************");
    }

}
