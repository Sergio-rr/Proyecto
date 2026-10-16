package clases;

import java.time.LocalDateTime;

public class Historial {
    private int id;
    private LocalDateTime fechaHora;
    private String accion;          // INSERTAR, MODIFICAR, ELIMINAR, ENTRADA, SALIDA
    private String campoAfectado;   // NOMBRE, CANTIDAD, PRECIO, CATEGORIA, TODO, N/A
    private int codigoComponente;
    private String nombreComponente;
    private String valorAnterior;  
    private String valorNuevo;
    private String detalle;


    public Historial(int id, LocalDateTime fechaHora, String accion, String campoAfectado, 
                     int codigoComponente, String nombreComponente, String valorAnterior, 
                     String valorNuevo, String detalle) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.accion = accion;
        this.campoAfectado = campoAfectado;
        this.codigoComponente = codigoComponente;
        this.nombreComponente = nombreComponente;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.detalle = detalle;
    }

    public int getId() { return id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getAccion() { return accion; }
    public String getCampoAfectado() { return campoAfectado; }
    public int getCodigoComponente() { return codigoComponente; }
    public String getNombreComponente() { return nombreComponente; }
    public String getValorAnterior() { return valorAnterior; }
    public String getValorNuevo() { return valorNuevo; }
    public String getDetalle() { return detalle; }
    
}