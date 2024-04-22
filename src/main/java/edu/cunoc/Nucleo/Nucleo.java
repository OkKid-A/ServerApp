package edu.cunoc.Nucleo;

import java.util.ArrayList;

public class Nucleo {

    private ArrayList<Accion> acciones;

    public Nucleo() {
        this.acciones = new ArrayList<>();
    }

    public void addAccion(Accion accion) {
        this.acciones.add(accion);
    }

    public ArrayList<Accion> getAcciones() {
        return acciones;
    }

    public void setAcciones(ArrayList<Accion> acciones) {
        this.acciones = acciones;
    }


}
