package edu.cunoc.Sitios;

import java.io.Serializable;
import java.util.ArrayList;

public class Componente implements Serializable {

    private TipoComp tipo;
    private String nombre;
    private String color;
    private Alineacion alineacion;
    private String imagen;
    private String video;
    private String texto;
    private int width;
    private int height;
    private ArrayList<String> etiquetas;
    private String padre;

    public Componente() {
    }

    public Componente(TipoComp tipo) {
        this.tipo = tipo;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(Integer witdth) {
        if (witdth!=null){
            this.width = witdth;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        if (height!=null){
            this.height = height;
        }
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        if (texto!=null){
            this.texto = texto;
        }
    }

    public TipoComp getTipo() {
        return tipo;
    }

    public void setTipo(TipoComp tipo) {
        if (tipo!=null){
            this.tipo = tipo;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre!=null){
            this.nombre = nombre;
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color!=null){
            this.color = color;
        }
    }

    public Alineacion getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(Alineacion alineacion) {
        if (alineacion!=null){
            this.alineacion = alineacion;
        }
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        if (imagen!=null){
            this.imagen = imagen;
        }
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        if (video!=null){
            this.video = video;
        }
    }

    public ArrayList<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(ArrayList<String> etiquetas) {
        if (etiquetas!=null){
            this.etiquetas = etiquetas;
        }
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        if (padre!=null){
            this.padre = padre;
        }
    }
}
