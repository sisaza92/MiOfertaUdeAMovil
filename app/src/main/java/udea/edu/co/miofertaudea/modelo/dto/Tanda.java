/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udea.edu.co.miofertaudea.modelo.dto;

import java.util.Date;

/**
 * Representa la tanda asignada a un estudiante para su respectiva Matricula.
 * @author CristianCamilo
 */
public class Tanda {

    //para consumir el servicio necesita cedula semestre
    private String nombre;
    private int nroTanda;
    private Date fecha;
    private Date horaInicio;
    private Date horaFin;

    public Tanda() {

    }

    public Tanda(String nombre, int nroTanda, Date fecha, Date horaInicio, Date horaFin) {
        this.nombre = nombre;
        this.nroTanda = nroTanda;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getNroTanda() {
        return nroTanda;
    }

    public void setNroTanda(int nroTanda) {
        this.nroTanda = nroTanda;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
}
