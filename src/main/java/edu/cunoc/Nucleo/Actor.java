package edu.cunoc.Nucleo;

import edu.cunoc.DB.DB;
import edu.cunoc.DB.Manejador;
import edu.cunoc.Enlace.Respuesta;
import edu.cunoc.Sitios.Componente;
import edu.cunoc.Sitios.Pagina;
import edu.cunoc.Sitios.Sitio;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Actor {

    private Manejador manejador;

    public Actor() {
        this.manejador = new Manejador();
    }

    public DB correrAcciones(DB db, ArrayList<Accion> acciones, Respuesta respuesta, String IdUSer){
        DB proxDB = Objects.requireNonNullElseGet(db, DB::new);
        boolean erroneo = false;
        for (int i = acciones.size() - 1; i >= 0; i--){
            Accion accion = acciones.get(i);
            try {
                switch (accion.getTipoAccion()) {
                    case CREAR_WEB:
                        crearWeb(proxDB, accion, respuesta, IdUSer);
                        break;
                    case ELIMINAR_WEB:
                        eliminarWeb(proxDB,accion,respuesta);
                        break;
                    case CREAR_PAGINA:
                        crearPagina(proxDB,accion,respuesta, IdUSer);
                        break;
                    case ELIMINAR_PAGINA:
                        eliminarPagina(proxDB,accion,respuesta);
                        break;
                    case MODIFICAR_PAGINA:
                        modificarPagina(proxDB,accion,respuesta, IdUSer);
                        break;
                    case AGREGAR_COMP:
                        agregarComponente(proxDB,accion,respuesta,IdUSer);
                        break;
                    case ELIMINAR_COMP:
                        eliminarComponente(proxDB, accion, respuesta, IdUSer);
                        break;
                    case MODIFICAR_COMP:
                        modificarComponente(proxDB,accion,respuesta, IdUSer);
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                erroneo = true;
            }
        }
        return proxDB;
    }

    public void crearWeb(DB db, Accion accion, Respuesta respuesta, String idUser) throws FileNotFoundException {
        HashMap<String,String> atributos = accion.getParametros();
        if (db.isEmpty()){
            db.setSitiosDB(new HashMap<String, Sitio>());
        }
        Sitio sitio = new Sitio(atributos.get("id"));
        Pagina pagina = sitio.getPaginas().get("-index-"+sitio.getiD());
        pagina.setID("-index-"+sitio.getiD());
        pagina.setSiteID(sitio.getiD());
        pagina.setParentID(sitio.getiD());
        pagina.setTitulo(pagina.getID());
        sitio.setCreador(atributos.getOrDefault("userAuth", idUser));
        pagina.setCreadorUser(atributos.getOrDefault("userAuth", idUser));
        sitio.setFechaCreacion(atributos.getOrDefault("fechaCr", String.valueOf((java.util.Calendar.getInstance().getTime()))));
        pagina.setFechaCreacion(atributos.getOrDefault("fechaCr", String.valueOf((java.util.Calendar.getInstance().getTime()))));
        sitio.setFechaModificacion(atributos.getOrDefault("fechaMod", String.valueOf((java.util.Calendar.getInstance().getTime()))));
        pagina.setFechaModificacion(atributos.getOrDefault("fechaMod", String.valueOf((java.util.Calendar.getInstance().getTime()))));
        sitio.setLastModUSer(atributos.getOrDefault("userMod",idUser));
        pagina.setLastMOdUser(atributos.getOrDefault("userMod",idUser));
        manejador.addSitio(db,sitio,respuesta);
        respuesta.addExito("Sitio web: "+sitio.getiD() + " creado con exito.");
    }

    public void eliminarWeb(DB db, Accion accion, Respuesta respuesta) throws FileNotFoundException {
        if (db.isEmpty()){
            respuesta.addErrorArchivo("No se puede eliminar el sitio: "+accion.getParametros().get("id") + ", porque no existe ningun sitio web.");
            throw new FileNotFoundException();
        } else {
            manejador.deleteSitio(db,accion.getParametros().get("id"),respuesta);
            respuesta.addExito("Sitio web: "+accion.getParametros().get("id") + " eliminado con exito.");
        }
    }

    public void crearPagina(DB db, Accion accion, Respuesta respuesta, String userID) throws FileNotFoundException {
        HashMap<String,String> atributos = accion.getParametros();
        Pagina pagina = new Pagina();
        pagina.setID(atributos.get("id"));
        pagina.setTitulo(atributos.getOrDefault("titlePar",pagina.getID()));
        pagina.setSiteID(atributos.get("sitioPar"));
        pagina.setParentID(atributos.get("padrePar"));
        pagina.setCreadorUser(atributos.getOrDefault("userAuth", userID));
        pagina.setFechaCreacion(atributos.getOrDefault("fechaCr", String.valueOf((java.util.Calendar.getInstance().getTime()))));
        pagina.setFechaModificacion(atributos.getOrDefault("fechaMod", String.valueOf((java.util.Calendar.getInstance().getTime()))));
        pagina.setLastMOdUser(atributos.getOrDefault("userMod",userID));
        pagina.setEtiquetas(Objects.requireNonNullElseGet(accion.getetiquetasList(), ArrayList::new));
        manejador.addPagina(db,pagina,atributos.get("sitioPar"),respuesta, userID);
        respuesta.addExito("Pagina web: "+pagina.getID() + " creada con exito.");
    }

    public void eliminarPagina(DB db, Accion accion, Respuesta respuesta) throws FileNotFoundException {
        if (db.isEmpty()){
            respuesta.addErrorArchivo("No se puede eliminar la pagina: "+accion.getParametros().get("id") + ", porque no existe ningun sitio web.");
            throw new FileNotFoundException();
        } else {
            manejador.deletePagina(db,accion.getParametros().get("id"),respuesta);
            respuesta.addExito("Pagina: "+accion.getParametros().get("id") + " eliminada con exito.");
        }
    }

    private void modificarPagina(DB proxDB, Accion accion, Respuesta respuesta, String idUSer) throws FileNotFoundException {
        HashMap<String,String> atributos = accion.getParametros();
        if (atributos.get("titlePar")==null && accion.getetiquetasList()==null){
            respuesta.addErrorArchivo("No se modifico la pagina: " + accion.getParametros().get("id") + ", porque no ser recibio el elemento a modificar.");
            throw new FileNotFoundException();
        } else {
            Pagina pagina = manejador.buscarPagina(proxDB,atributos.get("id"));
            pagina.setLastMOdUser(idUSer);
            if (atributos.get("titlePar")!=null){
                respuesta.addExito("La pagina: "+accion.getParametros().get("id") + " ha cambiado su titulo a: " + atributos.get("titlePar"));
                pagina.setTitulo(atributos.get("titlePar"));
            }
            if (accion.getetiquetasList()!=null){
                respuesta.addExito("La pagina: "+accion.getParametros().get("id") + " ha cambiado sus etiquetas a: " + accion.getetiquetasList().toString());
                pagina.setEtiquetas(accion.getetiquetasList());
            }
            proxDB.getSitiosDB().get(pagina.getSiteID()).setModificado(true);
            proxDB.getSitiosDB().get(pagina.getSiteID()).setLastModUSer(idUSer);

        }
    }

    private void agregarComponente(DB proxDB, Accion accion, Respuesta respuesta, String idUSer) throws FileNotFoundException {
        HashMap<String,String> parametros = accion.getParametros();
        Componente componente = accion.getAtributos();
        Pagina pagina = manejador.buscarPagina(proxDB,parametros.get("id"));
        String sitio = pagina.getSiteID();
        manejador.addComponente(proxDB,componente,sitio,pagina.getID(),respuesta,idUSer);
    }

    public void eliminarComponente(DB proxDB, Accion accion, Respuesta respuesta, String idUSer) throws FileNotFoundException {
        if (proxDB.isEmpty()){
            respuesta.addErrorArchivo("No se puede eliminar el componente: "+accion.getParametros().get("id") + ", porque no existe ningun sitio web.");
            throw new FileNotFoundException();
        } else {
            manejador.deleteComponente(proxDB,accion.getParametros().get("id"),
                    manejador.buscarPagina(proxDB,accion.getParametros().get("id")).getID(),respuesta,idUSer);
        }
    }

    public void modificarComponente(DB proxDB, Accion accion, Respuesta respuesta, String idUSer) throws FileNotFoundException {
        if (proxDB.isEmpty()){
            respuesta.addErrorArchivo("No se puede modificar el componente: "+accion.getParametros().get("id") + ", porque no existe ningun sitio web.");
            throw new FileNotFoundException();
        } else {
            Componente componente = accion.getAtributos();
            manejador.deleteComponente(proxDB,accion.getParametros().get("id"),
                    accion.getParametros().get("pagina"),respuesta,idUSer);
            agregarComponente(proxDB,accion,respuesta,idUSer);
        }

    }
}
