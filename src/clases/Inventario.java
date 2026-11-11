package clases;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Inventario {

    private ArrayList<Componentes> componentes = new ArrayList<>();
    private ArrayList<String> categorias = new ArrayList<>();

    
    public Inventario() {
        GestorArchivos.cargarCategorias(this);
        
        if(categorias.isEmpty()){
            categorias.add("Microcontroladores");
            categorias.add("Sensores");
            categorias.add("Componentes Pasivos");
            categorias.add("Semiconductores");
            
            GestorArchivos.guardarCat(this);
        }
    }
    
    public ArrayList<String> getCategoria(){
        return categorias;
    }
    
    public void insertar(int cod, String nom, int cant, double precio, String cat){
       // Validamos si el nombre está compuesto únicamente por números
        if (cod <= 0) {throw new IllegalArgumentException("El código debe ser un número entero positivo.");}
        if (cant < 0) {throw new IllegalArgumentException("La cantidad no puede ser un valor negativo.");} 
        if (precio < 0) {throw new IllegalArgumentException("El precio no puede ser un valor negativo.");}
        if (nom == null || nom.trim().isEmpty()) {throw new IllegalArgumentException("El nombre del componente no puede estar vacío.");}
        if (buscar(cod) != null) {throw new IllegalArgumentException("Ya existe un componente registrado con el código: " + cod);}
        if (buscar(nom) != null) {throw new IllegalArgumentException("Ya existe un componente registrado con el nombre: '" + nom + "'");}
        if (nom.matches("^\\d+$")) {throw new IllegalArgumentException("El nombre no puede estar compuesto unicamente por números.");}
        
        componentes.add(new Componentes(nom, cod, cant, precio, cat));
        GestorArchivos.guardarEnArchivo(this); 
        GestorArchivos.registrarHistorial("INSERTAR", "N/A", cod, nom, "N/A", String.valueOf(cant), "Registro inicial de producto");
    }

    public Object[] info(Componentes c) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = c.getFecha() != null ? c.getFecha().format(formato) : "";

        return new Object[] {
            c.getCodigo(),
            c.getNombre(),
            c.getCategoria(),
            c.getCantidad(),
            c.getPrecio(),
            fechaFormateada
        };
    }
    
    public ArrayList<Object[]> listar(int opcion) {
        // Aplicas el ordenamiento según la opción elegida
        OrdenarPor(opcion); 
        
        ArrayList<Object[]> listaFilas = new ArrayList<>();
        for (Componentes c : componentes) { 
            listaFilas.add(info(c)); // Usa tu método info() para cada elemento
        }
        
        return listaFilas;
    }

    public void OrdenarPor(int opcion) {
        switch (opcion) {
            case 1: OrdenarCod(); break;
            case 2: OrdenarNom(); break;
            case 3: OrdenarCat(); break;
            case 4: OrdenarStock(); break;
            case 5: OrdenarPrecio(); break;
            case 6: OrdenarFecha(); break;
            default: OrdenarCod(); break;
        }
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

    // Modifica un componente validando los datos y registrando en el historial cada campo que cambió.
    // Lanza IllegalArgumentException si algún dato es inválido. Retorna true si hubo cambios.
    public boolean modificarComponente(int codigo, String nombre, int cantidad, double precio, String categoria) {
        Componentes c = buscar(codigo);
        if (c == null) {throw new IllegalArgumentException("No se encontró un componente con el código: " + codigo);}
        if (nombre == null || nombre.trim().isEmpty()) {throw new IllegalArgumentException("El nombre del componente no puede estar vacío.");}
        nombre = nombre.trim();
        if (nombre.matches("^\\d+$")) {throw new IllegalArgumentException("El nombre no puede estar compuesto unicamente por números.");}
        Componentes otro = buscar(nombre);
        if (otro != null && otro != c) {throw new IllegalArgumentException("Ya existe un componente registrado con el nombre: '" + nombre + "'");}
        if (cantidad < 0) {throw new IllegalArgumentException("La cantidad no puede ser un valor negativo.");}
        if (precio < 0) {throw new IllegalArgumentException("El precio no puede ser un valor negativo.");}
        if (categoria == null || categoria.trim().isEmpty()) {throw new IllegalArgumentException("La categoría no puede estar vacía.");}
        categoria = categoria.trim();

        // Si la categoría ya existe se usa su nombre registrado; si no, se crea
        String catFinal = categoria;
        for (String ca : categorias) {
            if (ca.equalsIgnoreCase(categoria)) {catFinal = ca;}
        }
        if (!BuscarCat(catFinal)) {NueCat(catFinal);}

        boolean cambio = false;

        if (!c.getNombre().equals(nombre)) {
            String anterior = c.getNombre();
            c.setNombre(nombre);
            GestorArchivos.registrarHistorial("MODIFICAR", "NOMBRE", c.getCodigo(), c.getNombre(), anterior, nombre, "Cambio de nombre del componente");
            cambio = true;
        }
        if (c.getCantidad() != cantidad) {
            String anterior = String.valueOf(c.getCantidad());
            c.setCantidad(cantidad);
            GestorArchivos.registrarHistorial("MODIFICAR", "CANTIDAD", c.getCodigo(), c.getNombre(), anterior, String.valueOf(cantidad), "Ajuste directo de stock");
            cambio = true;
        }
        if (c.getPrecio() != precio) {
            String anterior = String.valueOf(c.getPrecio());
            c.setPrecio(precio);
            GestorArchivos.registrarHistorial("MODIFICAR", "PRECIO", c.getCodigo(), c.getNombre(), anterior, String.valueOf(precio), "Actualizacion de precio");
            cambio = true;
        }
        if (!c.getCategoria().equals(catFinal)) {
            String anterior = c.getCategoria();
            c.setCategoria(catFinal);
            GestorArchivos.registrarHistorial("MODIFICAR", "CATEGORIA", c.getCodigo(), c.getNombre(), anterior, catFinal, "Reclasificación de categoría");
            cambio = true;
        }

        if (cambio) {GestorArchivos.guardarEnArchivo(this);}
        return cambio;
    }


    public void eliminar(Componentes c){
        if (c != null) {
            componentes.remove(c);
            GestorArchivos.guardarEnArchivo(this); // <-- AUTO-GUARDADO
            GestorArchivos.registrarHistorial("ELIMINAR", "N/A", c.getCodigo(), c.getNombre(), String.valueOf(c.getCantidad()), "0", "Producto eliminado del sistema");
            System.out.println("Componente eliminado correctamente.");
        } else {
            System.out.println("No se encontró un componente con ese código.");
        }
    }

    /*public void Mostrarcategorias() {
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
            return;
        }
        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + ". " + categorias.get(i));
        }
        System.out.println((categorias.size() + 1) + ". [ + Crear nueva categoría ]");
    }*/

    public boolean BuscarCat(String cate) {//Verifica si existe o no la categoria ingresada
        for (String ca : categorias) {
            if (ca.equalsIgnoreCase(cate)) {
                return true;
            }      
        }
        return false;
    }
    public void NueCat(String nueCat) { // Añade una nueva categoria
        if (nueCat == null || nueCat.trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
    
        // Usamos tu propio método BuscarCat para evitar duplicados
        if (BuscarCat(nueCat)) {
            throw new IllegalArgumentException("La categoría '" + nueCat + "' ya existe.");
        }
        categorias.add(nueCat.trim()); 
        GestorArchivos.guardarCat(this);
    }
        
    // Obtiene el nombre de la categoría según el número ingresado (1, 2, 3, etc.)
    /*public String getCategoriaPorIndice(int indice) {
        if (indice >= 1 && indice <= categorias.size()) {
            return categorias.get(indice - 1);
        }
        return null;
    }*/
        


    // Registrar entrada de stock (+ Reposición)
    public boolean registrarEntrada(int codigo, int cantidadIngreso) {
        Componentes c = buscar(codigo);
        if (c != null) {
            // 1. Guardamos el stock previo antes de aumentar
            int stockAnterior = c.getCantidad();

            // 2. Modificamos la cantidad
            c.setCantidad(stockAnterior + cantidadIngreso);

            // 3. Auto-guardado en el CSV del inventario
            GestorArchivos.guardarEnArchivo(this);

            // 4. Registro en el Historial con VALOR_ANTERIOR y VALOR_NUEVO
            GestorArchivos.registrarHistorial(
                "ENTRADA",
                "CANTIDAD",
                c.getCodigo(),
                c.getNombre(),
                String.valueOf(stockAnterior),            // VALOR_ANTERIOR (ej: "10")
                String.valueOf(c.getCantidad()),           // VALOR_NUEVO (ej: "25")
                "Ingreso de " + cantidadIngreso + " unidades" // DETALLE
            );

            return true;
        }
        return false;
    }

    // Registrar salida de stock (- Venta / Despacho)
    // Retorna -2 si la salida supera el stock actual, o el nuevo stock
    public int registrarSalida(int codigo, int cantidadSalida) {
        Componentes c = buscar(codigo);

        if (cantidadSalida > c.getCantidad()) {
            return -2; // Stock insuficiente
        }

        // 1. Guardamos el stock anterior antes de restarle la salida
        int stockAnterior = c.getCantidad();

        // 2. Modificamos el stock
        c.setCantidad(stockAnterior - cantidadSalida);

        // 3. Auto-guardado en el CSV principal
        GestorArchivos.guardarEnArchivo(this);

        // 4. Registro en el Historial con VALOR_ANTERIOR y VALOR_NUEVO
        GestorArchivos.registrarHistorial(
            "SALIDA", 
            "CANTIDAD", 
            c.getCodigo(), 
            c.getNombre(), 
            String.valueOf(stockAnterior),            // VALOR_ANTERIOR
            String.valueOf(c.getCantidad()),           // VALOR_NUEVO 
            "Despacho de " + cantidadSalida + " unidades" // DETALLE
        );

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

    // Método getter para permitir al GestorArchivos acceder a la lista
    public ArrayList<Componentes> getComponentes() {
        return componentes;
    }

}
