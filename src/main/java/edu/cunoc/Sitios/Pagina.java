package edu.cunoc.Sitios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Pagina implements Serializable {

    private String ID;
    private String siteID;
    private String parentID;
    private String lastMOdUser;
    private String creadorUser;
    private String fechaCreacion;
    private String fechaModificacion;
    private ArrayList<Componente> componentes;
    private ArrayList<String> etiquetas;
    private String titulo;

    public Pagina() {
        this.componentes = new ArrayList<>();
        this.etiquetas = new ArrayList<>();
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getCreadorUser() {
        return creadorUser;
    }

    public void setCreadorUser(String creadorUser) {
        this.creadorUser = creadorUser;
    }

    public ArrayList<String> getEtiquetas() {
        return etiquetas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEtiquetas(ArrayList<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public ArrayList<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Componente> componentes) {
        this.componentes = componentes;
    }

    public String getLastMOdUser() {
        return lastMOdUser;
    }

    public void setLastMOdUser(String lastMOdUser) {
        this.lastMOdUser = lastMOdUser;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }
}
