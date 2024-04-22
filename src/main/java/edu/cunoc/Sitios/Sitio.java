package edu.cunoc.Sitios;

import java.io.Serializable;
import java.util.HashMap;

public class Sitio implements Serializable {

    private String iD;
    private HashMap<String,Pagina> paginas;
    private String lastModUSer;
    private String creador;
    private String fechaCreacion;
    private String fechaModificacion;
    private boolean modificado;

    public Sitio(String iD){
        this.paginas = new HashMap<>();
        this.iD = iD;
        paginas.put("-index-"+iD,new Pagina());
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

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public HashMap<String, Pagina> getPaginas() {
        return paginas;
    }

    public void setPaginas(HashMap<String, Pagina> paginas) {
        this.paginas = paginas;
    }

    public String getLastModUSer() {
        return lastModUSer;
    }

    public void setLastModUSer(String lastModUSer) {
        this.lastModUSer = lastModUSer;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }
}
