package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaAlumno extends JFrame {

    public JTextField txtNombre, txtMatricula, txtPromedio, txtSemestre, txtBuscar;
    public JTable tabla;
    public DefaultTableModel modelo;

    public JMenuItem miNuevo, miAbrir, miGuardar, miGuardarComo, miExportTXT, miExportDoc, miSalir;
    public JMenuItem miNuevoAl, miModificarAl, miEliminarAl, miBuscarAl;

    public JButton btnAlta, btnModificar, btnEliminar, btnAbrir, btnGuardar, btnExportTXT, btnExportDoc;

    public VistaAlumno() {
        super("Sistema - Universidad del Istmo (MVC)");
        initUI();

        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initUI() {

        Color rosa = new Color(255, 182, 193);
        setLayout(new BorderLayout());

        // ===== MENÚ =====
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        miNuevo = new JMenuItem("Nuevo");
        miAbrir = new JMenuItem("Abrir...");
        miGuardar = new JMenuItem("Guardar");
        miGuardarComo = new JMenuItem("Guardar como...");
        miExportTXT = new JMenuItem("Exportar .txt");
        miExportDoc = new JMenuItem("Exportar .docx");
        miSalir = new JMenuItem("Salir");

        menuArchivo.add(miNuevo);
        menuArchivo.add(miAbrir);
        menuArchivo.addSeparator();
        menuArchivo.add(miGuardar);
        menuArchivo.add(miGuardarComo);
        menuArchivo.addSeparator();
        menuArchivo.add(miExportTXT);
        menuArchivo.add(miExportDoc);
        menuArchivo.addSeparator();
        menuArchivo.add(miSalir);

        JMenu menuAlumnos = new JMenu("Alumnos");
        miNuevoAl = new JMenuItem("Nuevo alumno");
        miModificarAl = new JMenuItem("Modificar alumno");
        miEliminarAl = new JMenuItem("Eliminar alumno");
        miBuscarAl = new JMenuItem("Buscar por matrícula");

        menuAlumnos.add(miNuevoAl);
        menuAlumnos.add(miModificarAl);
        menuAlumnos.add(miEliminarAl);
        menuAlumnos.addSeparator();
        menuAlumnos.add(miBuscarAl);

        menuBar.add(menuArchivo);
        menuBar.add(menuAlumnos);
        setJMenuBar(menuBar);

        // ===== FORMULARIO =====
        JPanel panelForm = new JPanel(new GridLayout(2, 5, 8, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        txtNombre = new JTextField();
        txtMatricula = new JTextField();
        txtPromedio = new JTextField();
        txtSemestre = new JTextField();
        txtBuscar = new JTextField();

        panelForm.add(labeledField("Nombre:", txtNombre));
        panelForm.add(labeledField("Matrícula:", txtMatricula));
        panelForm.add(labeledField("Promedio:", txtPromedio));
        panelForm.add(labeledField("Semestre:", txtSemestre));
        panelForm.add(labeledField("Buscar matrícula:", txtBuscar));

        add(panelForm, BorderLayout.NORTH);

        // ===== TABLA =====
        modelo = new DefaultTableModel(new Object[]{"Nombre", "Matrícula", "Promedio", "Semestre"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ===== BOTONES =====
        JPanel panelBot = new JPanel();

        btnAlta = new JButton("Alta");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnAbrir = new JButton("Abrir...");
        btnGuardar = new JButton("Guardar");
        btnExportTXT = new JButton("Exportar .txt");
        btnExportDoc = new JButton("Exportar .docx");

        JButton[] botones = { btnAlta, btnModificar, btnEliminar, btnAbrir, btnGuardar, btnExportTXT, btnExportDoc };
        for (JButton b: botones) b.setBackground(rosa);

        for (JButton b: botones) panelBot.add(b);

        add(panelBot, BorderLayout.SOUTH);
    }

    private JPanel labeledField(String label, JTextField txt) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(txt, BorderLayout.CENTER);
        return p;
    }
}
