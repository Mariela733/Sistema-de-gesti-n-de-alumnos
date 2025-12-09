 package Modelo;

public class Alumno {

    public static final String SEP = ",";

    private String nombre;
    private String matricula;
    private double promedio;
    private String semestre;

    public Alumno(String nombre, String matricula, double promedio, String semestre) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.promedio = promedio;
        this.semestre = semestre;
    }

    public String toCSV() {
        return nombre + SEP + matricula + SEP + promedio + SEP + semestre;
    }

    public static Alumno desdeCSV(String linea) {
        String[] p = linea.split(SEP, -1);
        String nombre = p.length > 0 ? p[0] : "";
        String matricula = p.length > 1 ? p[1] : "";
        double promedio = 0.0;
        if (p.length > 2 && !p[2].isEmpty()) {
            try { promedio = Double.parseDouble(p[2]); } catch (Exception ignored) {}
        }
        String semestre = p.length > 3 ? p[3] : "";
        return new Alumno(nombre, matricula, promedio, semestre);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
}
