package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {

    private File archivo;

    public GestorArchivos(String ruta) {
        archivo = new File(ruta);
    }

    // ---------------------------
    // LECTURA
    // ---------------------------
    public List<Alumno> leer() {
        List<Alumno> lista = new ArrayList<>();

        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] p = linea.split("\\|");
                if (p.length != 4) continue;

                String nombre = p[0].trim();
                String matricula = p[1].trim();
                double promedio = Double.parseDouble(p[2].trim());
                String semestre = p[3].trim();

                lista.add(new Alumno(nombre, matricula, promedio, semestre));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ---------------------------
    // ESCRITURA
    // ---------------------------
    public void guardar(List<Alumno> alumnos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {

            for (Alumno a : alumnos) {
                bw.write(a.getNombre() + " | " +
                         a.getMatricula() + " | " +
                         a.getPromedio() + " | " +
                         a.getSemestre());
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------
    // EXPORTAR TXT
    // ---------------------------
    public void exportarTxt(File destino, List<Alumno> alumnos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(destino))) {

            for (Alumno a : alumnos) {
                bw.write(a.getNombre() + " | " +
                         a.getMatricula() + " | " +
                         a.getPromedio() + " | " +
                         a.getSemestre());
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------
    // EXPORTAR DOC (simple)
    // ---------------------------
    public void exportarDoc(File destino, List<Alumno> alumnos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(destino))) {
            bw.write("Listado de alumnos\n\n");

            for (Alumno a : alumnos) {
                bw.write("Nombre: " + a.getNombre() + "\n");
                bw.write("Matricula: " + a.getMatricula() + "\n");
                bw.write("Promedio: " + a.getPromedio() + "\n");
                bw.write("Semestre: " + a.getSemestre() + "\n");
                bw.write("------------------------------\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
