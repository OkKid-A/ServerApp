package edu.cunoc.Reportes;

import edu.cunoc.DB.DB;
import edu.cunoc.DB.Manejador;
import edu.cunoc.Enlace.Respuesta;
import edu.cunoc.Sitios.Componente;
import edu.cunoc.Sitios.Pagina;

import java.util.ArrayList;
import java.util.Objects;

public class Reportador {

    private Manejador manejador;

    public Reportador() {
        this.manejador = new Manejador();
    }

    public Respuesta crearReportes(ArrayList<Query> queries, Respuesta respuesta, DB proxDB){
        for (Query query : queries) {
            switch (query.getTipoQuery()){
                case POP_COMP:
                    contarComponentes(query,proxDB,respuesta);
                    break;
            }
        }
        return respuesta;
    }

    public void contarComponentes(Query query, DB proxDB, Respuesta respuesta){
        TipoQuery tipoQuery = query.getTipoQuery();
        String sitio = query.getPaths().get(0).get(0);
        String pagina = "";
        for (int i = 1; i < query.getPaths().get(0).size(); i++){
            pagina = query.getPaths().get(0).get(i);
        }
        Pagina paginaDB = null;
        if (Objects.equals(pagina, "")){
            paginaDB = manejador.buscarPagina(proxDB, sitio);
        } else  {
            paginaDB = manejador.buscarPagina(proxDB, pagina);
        }
        int contador = 0;
        if (paginaDB != null){
            if (Objects.equals(query.getTipoComp(), "TODOS")){
                contador = paginaDB.getComponentes().size();
            } else {
                for (Componente componente : paginaDB.getComponentes()) {
                    if (Objects.equals(componente.getTipo().toString(), query.getTipoComp())){
                        contador++;
                    }
                }
            }
            respuesta.addExito("Consulta de uso de componentes:\nSitio: " +sitio+" Pagina: "+pagina+" Tipo de Componente: "+query.getTipoComp() + "Cantidad: "+contador +"\n");
        } else {
            respuesta.addErrorArchivo("No existe la pagina: "+pagina+"\n");
        }
    }
}
