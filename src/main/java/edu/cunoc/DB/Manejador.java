package edu.cunoc.DB;

import edu.cunoc.Enlace.ArchivadorHtml;
import edu.cunoc.Enlace.Respuesta;
import edu.cunoc.Sitios.Componente;
import edu.cunoc.Sitios.Pagina;
import edu.cunoc.Sitios.Sitio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.Map;

public class Manejador {

    private ArchivadorHtml archivadorHtml;

    public Manejador() {
        archivadorHtml = new ArchivadorHtml();
    }

    public void addSitio(DB db, Sitio sitio, Respuesta respuesta) throws FileNotFoundException {
        if (db.getSitiosDB().containsKey(sitio.getiD())){
            respuesta.addErrorArchivo("No se puede agregar el sitio. El sitio:"+sitio.getiD()+", ya existe");
            throw new FileNotFoundException();
        } else {
            db.getSitiosDB().put(sitio.getiD(), sitio);
            sitio.setModificado(true);
            respuesta.addExito("Sitio web: "+sitio.getiD() + " creado con exito.");
        }
    }

    public void deleteSitio(DB db, String sitio, Respuesta respuesta) throws FileNotFoundException {
        if (db.getSitiosDB().containsKey(sitio)){
            db.getSitiosDB().remove(sitio);
            try {
                archivadorHtml.deleteSitio(sitio,respuesta);
                respuesta.addExito("Sitio web: "+ sitio + " eliminado con exito.");
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        } else {
            respuesta.addErrorArchivo("No se puede eliminar el sitio: "+sitio + ", porque no fue encontrado.");
            throw new FileNotFoundException();
        }
    }

    public void deleteSitio(DB db, String sitio, Respuesta respuesta, boolean sinMensaje) throws FileNotFoundException {
        if (db.getSitiosDB().containsKey(sitio)){
            db.getSitiosDB().remove(sitio);
            try {
                archivadorHtml.deleteSitio(sitio,respuesta);
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        } else {
            System.out.println("No se puede eliminar el sitio: "+sitio + ", porque no fue encontrado.");
            throw new FileNotFoundException();
        }
    }
    public void addPagina(DB db, Pagina pagina, String sitio, Respuesta respuesta, String userID) throws FileNotFoundException {
        if (buscarPagina(db,pagina.getID())!=null){
            respuesta.addErrorArchivo("No se puede agregar la pagina. La pagina con ID:" + pagina.getID() + ", ya existe");
            throw new FileNotFoundException();
        } else {
            db.getSitiosDB().get(sitio).getPaginas().put(pagina.getID(),pagina);
            db.getSitiosDB().get(sitio).setModificado(true);
            db.getSitiosDB().get(sitio).setLastModUSer(userID);
            respuesta.addExito("Pagina web: "+pagina.getID() + " creada con exito.");
        }
    }

    public void deletePagina(DB db, String pagina,Respuesta respuesta) throws FileNotFoundException {
        Pagina paginaOb = buscarPagina(db,pagina);
        if (paginaOb!=null){
            db.getSitiosDB().get(paginaOb.getSiteID()).getPaginas().remove(pagina);
            try {
                archivadorHtml.deletePagina(paginaOb.getSiteID(),pagina,respuesta);
                respuesta.addExito("Pagina: "+pagina + " eliminada con exito.");
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        } else {
            respuesta.addErrorArchivo("No se puede eliminar la pagina: "+pagina + ", porque no fue encontrada.");
            throw new FileNotFoundException();
        }
    }

    public void addComponente(DB db, Componente comp, String sitio, String pagina, Respuesta respuesta, String idUser) throws FileNotFoundException {
        if (buscarPagina(db,pagina)==null){
            respuesta.addErrorArchivo("No se puede agregar el componente. La pagina con ID:" + pagina + ", no existe");
            throw new FileNotFoundException();
        } else {
            if (buscarComponente(db.getSitiosDB().get(sitio).getPaginas().get(pagina), comp.getNombre())!=null){
                respuesta.addErrorArchivo("No se puede agregar el componente. El componente con ID:" + comp.getNombre() + ", ya existe");
                throw new FileNotFoundException();
            } else {
                db.getSitiosDB().get(sitio).getPaginas().get(pagina).getComponentes().add(comp);
                db.getSitiosDB().get(sitio).setModificado(true);
                db.getSitiosDB().get(sitio).setLastModUSer(idUser);
                respuesta.addExito("El componente: "+comp.getNombre()+" se ha agregado con exito.");
            }
        }
    }

    public void deleteComponente(DB db, String comp, String pagina, Respuesta respuesta, String idUser) throws FileNotFoundException {
        if (buscarPagina(db,pagina)==null){
            respuesta.addErrorArchivo("No se puede eliminar el componente. La pagina con ID:" + pagina + ", no existe");
            throw new FileNotFoundException();
        } else {
            Pagina paginaOb = buscarPagina(db,pagina);
            if (buscarComponente(paginaOb, comp)==null){
                respuesta.addErrorArchivo("No se puede eliminar el componente. El componente con ID:" + comp + ", no existe");
                throw new FileNotFoundException();
            } else {
                db.getSitiosDB().get(paginaOb.getSiteID()).getPaginas().get(pagina).getComponentes().remove(buscarComponente(paginaOb,comp));
                db.getSitiosDB().get(paginaOb.getSiteID()).setModificado(true);
                db.getSitiosDB().get(paginaOb.getSiteID()).setLastModUSer(idUser);
                respuesta.addExito("El componente: "+comp+" se ha eliminado con exito.");
            }
        }
    }

    public Pagina buscarPagina(DB db, String pagina){
        Pagina encontrado = null;
        for (Map.Entry<String, Sitio> entry : db.getSitiosDB().entrySet()){
            if (entry.getValue().getPaginas().containsKey(pagina)){
                encontrado = entry.getValue().getPaginas().get(pagina);
                break;
            }
        }
        return encontrado;
    }

    public Componente buscarComponente(Pagina pagina, String comp){
        Componente encontrado = null;
        for (Componente componente : pagina.getComponentes()){
            if (componente.getNombre().equals(comp)){
                encontrado = componente;
                break;
            }
        }
        return encontrado;
    }
}
