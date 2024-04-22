package edu.cunoc.Reportes;

import java.util.ArrayList;

public class Query {

    private TipoQuery tipoQuery;
    private ArrayList<ArrayList<String>> paths;
    private String tipoComp;

    public Query(TipoQuery tipoQuery) {
        this.tipoQuery = tipoQuery;
    }

    public String getTipoComp() {
        return tipoComp;
    }

    public void setTipoComp(String tiopoComp) {
        this.tipoComp = tiopoComp;
    }

    public ArrayList<ArrayList<String>> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<ArrayList<String>> paths) {
        this.paths = paths;
    }

    public TipoQuery getTipoQuery() {
        return tipoQuery;
    }

    public void setTipoQuery(TipoQuery tipoQuery) {
        this.tipoQuery = tipoQuery;
    }
}
