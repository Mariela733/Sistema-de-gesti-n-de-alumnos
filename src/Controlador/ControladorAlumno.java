package Controlador;

import Modelo.Alumno;
import Modelo.GestorArchivos;
import Vista.VistaAlumno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ControladorAlumno {

    private VistaAlumno vista;
    private GestorArchivos gestor;
    private List<Alumno> lista;

    public ControladorAlumno(VistaAlumno vista, GestorArchivos gestor) {
        this.vista = vista;
        this.gestor = gestor;

        lista = gestor.leer(); // Cargar alumnos desde archivo
        cargarTabla();
        conectarEventos();
    }

    private void conectarEventos() {
        vista.btnAlta.addActionListener(e -> altaAlumno());
        vista.btnModificar.addActionListener(e -> modificarAlumno());
        vista.btnEliminar.addActionListener(e -> eliminarAlumno());
        vista.btnGuardar.addActionListener(e -> guardarArchivo());
        vista.btnAbrir.addActionListener(e -> abrirArchivo());
        vista.btnExportTXT.addActionListener(e -> exportarTXT());
        vista.btnExportDoc.addActionListener(e -> exportarDoc());

        vista.txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarPorMatricula();
            }
        });
    }

    // ======= ALTA DE ALUMNO =======
    private void altaAlumno() {
        String nombre = vista.txtNombre.getText().trim();
        String matricula = vista.txtMatricula.getText().trim();
        String promedioStr = vista.txtPromedio.getText().trim();
        String semestre = vista.txtSemestre.getText().trim();

        if (nombre.isEmpty() || matricula.isEmpty() || promedioStr.isEmpty() || semestre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios");
            return;
        }

        // Verificar que la matrícula no exista
        for (Alumno al : lista) {
            if (al.getMatricula().equalsIgnoreCase(matricula)) {
                JOptionPane.showMessageDialog(vista, "La matrícula ya existe");
                return;
            }
        }

        double promedio;
        try {
            promedio = Double.parseDouble(promedioStr);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Promedio inválido");
            return;
        }

        lista.add(new Alumno(nombre, matricula, promedio, semestre));
        cargarTabla();
        gestor.guardar(lista); // Guardar cambios en el archivo
        JOptionPane.showMessageDialog(vista, "Alumno agregado correctamente");
    }

    // ======= MODIFICAR ALUMNO =======
    private void modificarAlumno() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un alumno");
            return;
        }

        Alumno al = lista.get(fila);

        String nuevoNombre = vista.txtNombre.getText().trim();
        String nuevaMatricula = vista.txtMatricula.getText().trim();
        String nuevoPromedioStr = vista.txtPromedio.getText().trim();
        String nuevoSemestre = vista.txtSemestre.getText().trim();

        if (nuevoNombre.isEmpty() || nuevaMatricula.isEmpty() || nuevoPromedioStr.isEmpty() || nuevoSemestre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios");
            return;
        }

        // Verificar que la nueva matrícula no exista en otro alumno
        for (int i = 0; i < lista.size(); i++) {
            if (i != fila && lista.get(i).getMatricula().equalsIgnoreCase(nuevaMatricula)) {
                JOptionPane.showMessageDialog(vista, "La matrícula ya existe en otro alumno");
                return;
            }
        }

        al.setNombre(nuevoNombre);
        al.setMatricula(nuevaMatricula);
        al.setSemestre(nuevoSemestre);

        try {
            al.setPromedio(Double.parseDouble(nuevoPromedioStr));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Promedio inválido");
            return;
        }

        cargarTabla();
        gestor.guardar(lista); // Guardar cambios en el archivo
        JOptionPane.showMessageDialog(vista, "Alumno modificado correctamente");
    }

    // ======= ELIMINAR ALUMNO =======
    private void eliminarAlumno() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un alumno");
            return;
        }

        int r = JOptionPane.showConfirmDialog(vista,
                "¿Seguro que desea eliminar este registro?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (r == JOptionPane.YES_OPTION) {
            lista.remove(fila);
            cargarTabla();
            gestor.guardar(lista); // Guardar cambios en el archivo
            JOptionPane.showMessageDialog(vista, "Alumno eliminado correctamente");
        }
    }

    // ======= GUARDAR ARCHIVO =======
    private void guardarArchivo() {
        gestor.guardar(lista);
        JOptionPane.showMessageDialog(vista, "Archivo guardado");
    }

    // ======= ABRIR ARCHIVO =======
    private void abrirArchivo() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            gestor = new GestorArchivos(fc.getSelectedFile().getPath());
            lista = gestor.leer();
            cargarTabla();
        }
    }

    // ======= EXPORTAR TXT =======
    private void exportarTXT() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            gestor.exportarTxt(fc.getSelectedFile(), lista);
        }
    }

    // ======= EXPORTAR DOC =======
    private void exportarDoc() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            gestor.exportarDoc(fc.getSelectedFile(), lista);
        }
    }

    // ======= BUSCAR POR MATRÍCULA =======
    private void buscarPorMatricula() {
        String busqueda = vista.txtBuscar.getText().trim().toLowerCase();

        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        for (Alumno al : lista) {
            if (al.getMatricula().toLowerCase().contains(busqueda)) {
                modelo.addRow(new Object[]{
                        al.getNombre(),
                        al.getMatricula(),
                        al.getPromedio(),
                        al.getSemestre()
                });
            }
        }
    }

    // ======= CARGAR TABLA =======
    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        for (Alumno al : lista) {
            modelo.addRow(new Object[]{
                    al.getNombre(),
                    al.getMatricula(),
                    al.getPromedio(),
                    al.getSemestre()
            });
        }
    }
}
