package view;

import clases.Componentes;
import clases.Inventario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Módulo independiente de movimientos (entradas y salidas) y alertas de stock crítico.
 * - Pestaña "Movimientos": ficha previa del componente, registro de entrada o salida y banner de alerta.
 * - Pestaña "Alertas de stock": reporte de los componentes con stock crítico (<= Inventario.STOCK_CRITICO).
 */
public class MenuMovimientos extends javax.swing.JDialog {

    private Inventario inv;
    private Componentes componente; // componente cuya ficha está cargada (null si no hay ficha)

    private JLabel lblBanner;
    private JTabbedPane tabs;

    // Pestaña Movimientos
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblNombre, lblCategoria, lblStock, lblPrecio;
    private Color colorNormal;
    private JRadioButton rbEntrada, rbSalida;
    private JTextField txtUnidades;
    private JButton btnRegistrar;

    // Pestaña Alertas
    private JLabel lblResumen;
    private JTable tablaAlertas;
    private DefaultTableModel modelo;
    private JButton btnActualizar;

    public MenuMovimientos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Movimientos y alertas de stock");
        initComponents();
        setLocationRelativeTo(parent);
    }

    // Recibe el inventario y carga el reporte de stock crítico
    public void setInventario(Inventario invR) {
        this.inv = invR;
        actualizarAlertas();
    }

    // Opcional: precarga el componente seleccionado en la tabla de la ventana principal
    public void setCodigoInicial(int codigo) {
        txtCodigo.setText(String.valueOf(codigo));
        cargarFicha();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // ---------- Banner de alerta (oculto hasta que un movimiento deje stock crítico) ----------
        lblBanner = new JLabel("", SwingConstants.CENTER);
        lblBanner.setOpaque(true);
        lblBanner.setBackground(new Color(0xC00000));
        lblBanner.setForeground(Color.WHITE);
        lblBanner.setFont(lblBanner.getFont().deriveFont(Font.BOLD, 13f));
        lblBanner.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        lblBanner.setVisible(false);

        // ---------- Pestaña 1: Movimientos ----------
        txtCodigo = new JTextField(10);
        txtCodigo.setBorder(BorderFactory.createTitledBorder("Codigo del componente"));
        txtCodigo.addActionListener(e -> cargarFicha());
        // Si el usuario cambia el código, la ficha cargada deja de ser válida
        txtCodigo.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { limpiarFicha(); }
            @Override public void removeUpdate(DocumentEvent e) { limpiarFicha(); }
            @Override public void changedUpdate(DocumentEvent e) { limpiarFicha(); }
        });
        btnBuscar = new JButton("Ver ficha");
        btnBuscar.addActionListener(e -> cargarFicha());

        lblNombre = new JLabel("-");
        lblCategoria = new JLabel("-");
        lblStock = new JLabel("-");
        lblPrecio = new JLabel("-");
        colorNormal = lblStock.getForeground();

        rbEntrada = new JRadioButton("Entrada (reposicion / compra)", true);
        rbSalida = new JRadioButton("Salida (despacho / venta)");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbEntrada);
        grupoTipo.add(rbSalida);

        txtUnidades = new JTextField(10);
        txtUnidades.setBorder(BorderFactory.createTitledBorder("Cantidad de unidades"));
        btnRegistrar = new JButton("Registrar movimiento");
        btnRegistrar.addActionListener(e -> registrarMovimiento());

        JPanel filaCodigo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaCodigo.add(txtCodigo);
        filaCodigo.add(btnBuscar);

        JPanel ficha = new JPanel(new GridLayout(0, 2, 8, 4));
        ficha.setBorder(BorderFactory.createTitledBorder("Ficha del componente (antes del movimiento)"));
        ficha.add(new JLabel("Nombre:"));
        ficha.add(lblNombre);
        ficha.add(new JLabel("Categoria:"));
        ficha.add(lblCategoria);
        ficha.add(new JLabel("Stock actual:"));
        ficha.add(lblStock);
        ficha.add(new JLabel("Precio:"));
        ficha.add(lblPrecio);

        JPanel filaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaTipo.setBorder(BorderFactory.createTitledBorder("Tipo de movimiento"));
        filaTipo.add(rbEntrada);
        filaTipo.add(rbSalida);

        JPanel filaUnidades = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaUnidades.add(txtUnidades);
        filaUnidades.add(btnRegistrar);

        JPanel panelMov = new JPanel();
        panelMov.setLayout(new BoxLayout(panelMov, BoxLayout.Y_AXIS));
        panelMov.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        filaCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        ficha.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaUnidades.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMov.add(filaCodigo);
        panelMov.add(ficha);
        panelMov.add(filaTipo);
        panelMov.add(filaUnidades);
        panelMov.add(Box.createVerticalGlue());

        // ---------- Pestaña 2: Alertas de stock ----------
        lblResumen = new JLabel("", SwingConstants.CENTER);
        lblResumen.setOpaque(true);
        lblResumen.setFont(lblResumen.getFont().deriveFont(Font.BOLD, 13f));
        lblResumen.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        modelo = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Categoria", "Stock", "Precio (S/)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAlertas = new JTable(modelo);
        tablaAlertas.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                comp.setFont(comp.getFont().deriveFont(Font.BOLD));
                if (!isSelected) {
                    comp.setForeground(new Color(0xC00000));
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return comp;
            }
        });

        btnActualizar = new JButton("Actualizar reporte");
        btnActualizar.addActionListener(e -> actualizarAlertas());
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.add(btnActualizar);

        JPanel panelAlertas = new JPanel(new BorderLayout(0, 8));
        panelAlertas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAlertas.add(lblResumen, BorderLayout.NORTH);
        panelAlertas.add(new JScrollPane(tablaAlertas), BorderLayout.CENTER);
        panelAlertas.add(sur, BorderLayout.SOUTH);

        // ---------- Ventana ----------
        tabs = new JTabbedPane();
        tabs.addTab("Movimientos", panelMov);
        tabs.addTab("Alertas de stock", panelAlertas);
        tabs.setPreferredSize(new Dimension(560, 400));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(lblBanner, BorderLayout.NORTH);
        getContentPane().add(tabs, BorderLayout.CENTER);
        pack();
    }

    // ---------------- Ficha previa ----------------
    private void cargarFicha() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            Componentes c = inv.buscar(codigo);
            if (c == null) {
                limpiarFicha();
                JOptionPane.showMessageDialog(this,
                    "No se encontró un componente con el código: " + codigo,
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            componente = c;
            mostrarFicha();
        } catch (NumberFormatException e) {
            limpiarFicha();
            JOptionPane.showMessageDialog(this,
                "Ingrese un código numérico válido.",
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarFicha() {
        lblNombre.setText(componente.getNombre());
        lblCategoria.setText(componente.getCategoria());
        lblPrecio.setText("S/ " + componente.getPrecio());
        lblStock.setText(String.valueOf(componente.getCantidad()));
        if (inv.esStockCritico(componente)) {
            lblStock.setText(componente.getCantidad() + "  (stock crítico)");
            lblStock.setForeground(new Color(0xC00000));
        } else {
            lblStock.setForeground(colorNormal);
        }
    }

    private void limpiarFicha() {
        componente = null;
        lblNombre.setText("-");
        lblCategoria.setText("-");
        lblStock.setText("-");
        lblStock.setForeground(colorNormal);
        lblPrecio.setText("-");
    }

    // ---------------- Movimiento ----------------
    private void registrarMovimiento() {
        if (componente == null) {
            JOptionPane.showMessageDialog(this,
                "Primero busque un componente por su código para ver su ficha.",
                "Movimientos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int unidades = Integer.parseInt(txtUnidades.getText().trim());
            boolean esEntrada = rbEntrada.isSelected();
            int stockAnterior = componente.getCantidad();

            int nuevoStock = inv.registrarMovimiento(componente.getCodigo(), esEntrada, unidades);

            mostrarFicha();
            txtUnidades.setText("");
            actualizarAlertas();

            if (inv.esStockCritico(componente)) {
                mostrarBanner("¡STOCK CRÍTICO! " + componente.getNombre() + " quedó con " + nuevoStock + " unidad(es). Reponer.");
            } else {
                ocultarBanner();
            }
            JOptionPane.showMessageDialog(this,
                (esEntrada ? "Entrada" : "Salida") + " registrada correctamente. Stock: " + stockAnterior + " → " + nuevoStock + ".");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Ingrese una cantidad entera válida.",
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- Banner y reporte de alertas ----------------
    private void mostrarBanner(String texto) {
        lblBanner.setText(texto);
        lblBanner.setVisible(true);
        pack();
    }

    private void ocultarBanner() {
        lblBanner.setVisible(false);
        pack();
    }

    private void actualizarAlertas() {
        if (inv == null) {
            return;
        }
        ArrayList<Object[]> filas = inv.listarStockCritico();
        modelo.setRowCount(0);
        for (Object[] f : filas) {
            modelo.addRow(new Object[]{f[0], f[1], f[2], f[3], f[4]});
        }

        if (filas.isEmpty()) {
            lblResumen.setText("Sin alertas: no hay componentes con stock crítico (≤ " + Inventario.STOCK_CRITICO + " unidades).");
            lblResumen.setBackground(new Color(0xE2F0D9));
            lblResumen.setForeground(new Color(0x375623));
            tabs.setTitleAt(1, "Alertas de stock");
        } else {
            lblResumen.setText("ALERTA: " + filas.size() + " componente(s) con stock crítico (≤ " + Inventario.STOCK_CRITICO + " unidades).");
            lblResumen.setBackground(new Color(0xFBE5D6));
            lblResumen.setForeground(new Color(0xC00000));
            tabs.setTitleAt(1, "Alertas de stock (" + filas.size() + ")");
        }
    }
}
