package edu.cunoc.Enlace;

import edu.cunoc.DB.DB;
import edu.cunoc.DB.Manejador;
import edu.cunoc.Sitios.Componente;
import edu.cunoc.Sitios.Pagina;
import edu.cunoc.Sitios.Sitio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

public class CreadorWeb {

    private ArchivadorHtml archivadorHtml;
    private DB db;

    public CreadorWeb() {
        this.archivadorHtml = new ArchivadorHtml();
    }

    public boolean buildHtml(DB db, Respuesta respuesta){
        boolean respuestaCorrecta = true;
        DB dbCopia = new DB(db);
        try {
        for (Map.Entry<String, Sitio> entry : db.getSitiosDB().entrySet()){
            Sitio sitio = entry.getValue();
            if (sitio.isModificado()){
                try {
                    buildSitio(dbCopia, sitio,respuesta);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    respuestaCorrecta = false;
                }
            }
        }} catch (ConcurrentModificationException e){
            e.printStackTrace();
        }
        if (respuestaCorrecta){
            db = dbCopia;
        }
        return respuestaCorrecta;
    }

    public boolean buildSitio(DB db, Sitio sitio, Respuesta respuesta) throws FileNotFoundException {
        Manejador manejador = new Manejador();
        try {
            manejador.deleteSitio(db, sitio.getiD(), respuesta, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        db.getSitiosDB().put(sitio.getiD(),sitio);
        archivadorHtml.crearSitioDir(sitio.getiD(),respuesta);
        for (Map.Entry<String, Pagina> entry : sitio.getPaginas().entrySet()){
            Pagina pagina = entry.getValue();
            archivadorHtml.guardarHtml(pagina.getID(),sitio.getiD(),buildPagina(pagina),respuesta);
        }
        return false;
    }

    public String buildPagina(Pagina pagina){
        String html = "<html>\n";
        html += "<head>\n<title>"+pagina.getTitulo()+"</title>\n";
        html += "<body>\n";
        for (Componente componente : pagina.getComponentes()){
            dibujarComponente(componente);
        }
        html += "</body>\n</html>";
        return html;
    }

    public String dibujarComponente(Componente componente){
        String html = "";
        switch (componente.getTipo()){
            case IMAGEN:
                html += "<img class=\"imagen\" src=\""+componente.getImagen()+"\" width=\""+componente.getWidth()+"\"" +
                        " height=\""+componente.getHeight()+alignImagen(componente)+"\">\n";
                break;
            case PARRAFO:
                html += "<p class=\"parrafo\" id=\"" +componente.getNombre()+"\" "+ajustarTexto(componente) +  ">\n";
                html += componente.getTexto() + "\n";
                html += "</p>\n>";
                break;
            case TITULO:
                html += "<h1 class=\"titulo\" id=\""+componente.getNombre()+"\" " + ajustarTexto(componente)+">\n";
                html += componente.getTexto() + "\n";
                html += "</h1>\n";
                break;
            case VIDEO:
                html += "<video class=\"video\" id=\"" + componente.getNombre()+"\" width=\""+componente.getWidth()+"\"" +
                        " height=\""+componente.getHeight()+"\" >\n";
                html += "source src=\"" + componente.getVideo() + "\">\n";
                html += "</video>\n";
                break;
            case MENU:
                 buildMenu(componente);
        }
        return html;
    }

    public String ajustarTexto(Componente componente){
        String html = "style=";
        if (componente.getColor()!=null){
            html += "\"color:" + componente.getColor() + "\"; ";
            html += asignarAlineacion(componente);
        } else if (componente.getAlineacion()!=null){
            html += asignarAlineacion(componente);
        } else {
            return null;
        }
        return html;
    }

    public String asignarAlineacion(Componente componente){
        if (componente.getAlineacion()!=null) {
            switch (componente.getAlineacion()) {
                case DERECHA:
                    return " \"text-align: right;\"";
                case IZQUIERDA:
                    return " \"text-align: left;\"";
                case JUSTIFICADA:
                    return " \"text-align: justify;\"";
                default:
                    return " \"text-align: center;\"";
            }
        } else {
            return "";
        }
    }

    public String alignImagen(Componente componente){
        if (componente.getAlineacion()!=null){
            switch (componente.getAlineacion()){
                case DERECHA:
                    return " align= \"right\"";
                case IZQUIERDA:
                    return " align= \"left\"";
                default:
                    return " align= \"center\"";
            }
        } else {
            return "";
        }
    }

    public String buildMenu(Componente componente){
        ArrayList<Pagina> hijas = new ArrayList<>();
        String menu = "";
        for (Map.Entry<String, Sitio> entry : db.getSitiosDB().entrySet()){
            for (Map.Entry<String, Pagina> paginaEntry : entry.getValue().getPaginas().entrySet()){
                if (componente.getPadre()!=null) {
                    if (paginaEntry.getValue().getParentID().equals(componente.getPadre())) {
                        for (String etiqueta : paginaEntry.getValue().getEtiquetas())
                            for (String compEt : componente.getEtiquetas()) {
                                if (etiqueta.equals(compEt)) {
                                    hijas.add(paginaEntry.getValue());
                                }
                            }
                    }
                } else {
                    for (String etiqueta : paginaEntry.getValue().getEtiquetas())
                        for (String compEt : componente.getEtiquetas()) {
                            if (etiqueta.equals(compEt)) {
                                hijas.add(paginaEntry.getValue());
                            }
                        }
                }
            }
        }
        for (Pagina pagina : hijas){
            menu += "<a href=\""+"http://localhost/"+ pagina.getSiteID()+ "/" +pagina.getID()+".html \">";
            menu += pagina.getID();
            menu += "</a>\n";
        }
        return menu;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
