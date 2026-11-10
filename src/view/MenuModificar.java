package view;

import clases.Componentes;
import clases.Inventario;
import javax.swing.JOptionPane;

/**
 * Ventana para modificar un componente existente (nombre, cantidad, precio y categoría).
 * El código no se puede cambiar porque identifica al componente.
 */
public class MenuModificar extends javax.swing.JDialog {

    private Inventario inv;
    private Componentes componente;

    public MenuModificar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Modificar componente");
        initComponents();
        setLocationRelativeTo(parent);
    }

    // Recibe el inventario y el componente seleccionado en la tabla, y rellena los campos
    public void setInventario(Inventario invR, Componentes comp) {
        this.inv = invR;
        this.componente = comp;

        txtCodigo.setText(String.valueOf(comp.getCodigo()));
        txtNombre.setText(comp.getNombre());
        txtCantidad.setText(String.valueOf(comp.getCantidad()));
        txtPrecio.setText(String.valueOf(comp.getPrecio()));
        cargarCategoriasEnCombo();
        cbCategoria.setSelectedItem(comp.getCategoria());
    }

    private void cargarCategoriasEnCombo() {
        cbCategoria.removeAllItems();
        for (String cat : inv.getCategoria()) {
            cbCategoria.addItem(cat);
        }
        cbCategoria.addItem("--Nueva categoria--");
    }

    private void initComponents() {
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        cbCategoria = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtCodigo.setBorder(javax.swing.BorderFactory.createTitledBorder("Codigo (no editable)"));
        txtCodigo.setEditable(false);
        txtNombre.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));
        txtCantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("Cantidad"));
        txtPrecio.setBorder(javax.swing.BorderFactory.createTitledBorder("Precio"));
        cbCategoria.setBorder(javax.swing.BorderFactory.createTitledBorder("Categoria"));

        btnGuardar.setText("Guardar cambios");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.JPanel campos = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 0, 5));
        campos.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 0, 10));
        campos.add(txtCodigo);
        campos.add(txtNombre);
        campos.add(txtCantidad);
        campos.add(txtPrecio);
        campos.add(cbCategoria);

        javax.swing.JPanel botones = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        botones.add(btnCancelar);
        botones.add(btnGuardar);

        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(campos, java.awt.BorderLayout.CENTER);
        getContentPane().add(botones, java.awt.BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnGuardar);
        pack();
        setMinimumSize(new java.awt.Dimension(300, getHeight()));
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nombre = txtNombre.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            String catSeleccionada = cbCategoria.getSelectedItem().toString();
            if (catSeleccionada.equals("--Nueva categoria--")) {
                String nuevaCat = JOptionPane.showInputDialog(this, "Ingrese el nombre de la nueva categoría:");
                if (nuevaCat == null || nuevaCat.trim().isEmpty()) {
                    return; // Si cancela o deja vacío, no se guarda nada
                }
                catSeleccionada = nuevaCat.trim(); // Se crea en el Inventario solo si el resto de datos es válido
            }

            boolean huboCambios = inv.modificarComponente(componente.getCodigo(), nombre, cantidad, precio, catSeleccionada);

            if (huboCambios) {
                JOptionPane.showMessageDialog(this, "Componente modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se realizaron cambios.");
            }
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Verifique que Cantidad y Precio sean valores numericos validos.",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
}
