package SistemaCalificaciones1;

import Controlador.ControladorAlumno;
import Modelo.GestorArchivos;
import Vista.VistaAlumno;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Archivo por defecto (si no existe, se crea)
            String rutaArchivo = "alumnos.txt";

            VistaAlumno vista = new VistaAlumno();
            GestorArchivos gestor = new GestorArchivos(rutaArchivo);

            new ControladorAlumno(vista, gestor);

            vista.setVisible(true);
        });
    }
}
