package udea.edu.co.miofertaudea.modelo.dto;

import java.io.Serializable;

/**
 * Created by Santiago on 29/11/2016.
 */
public class Estudiante implements Serializable{

    //cambiar fechaDeNacimiento a tipo Date

    String cedula;
    String nombres;
    String apellidos;
    String fechaDeNacimiento;
    String email;

    public Estudiante(String cedula, String nombres, String apellidos, String fechaDeNacimiento, String email) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.email = email;
    }

    public Estudiante(){}

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
