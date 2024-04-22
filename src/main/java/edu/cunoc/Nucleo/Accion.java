package edu.cunoc.Nucleo;

import edu.cunoc.Sitios.Componente;

import java.util.ArrayList;
import java.util.HashMap;

public class Accion {
     private HashMap<String, String> parametros;
     private Componente atributos;
     private ArrayList<String> etiquetasList;
     private TipoAccion tipoAccion;

    public Accion() {
        this.parametros = new HashMap<>();
    }

    public Componente getAtributos() {
        return atributos;
    }

    public void setAtributos(Componente atributos) {
        this.atributos = atributos;
    }

    public Accion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
        this.parametros = new HashMap<>();
    }

    public ArrayList<String> getetiquetasList() {
        return etiquetasList;
    }

    public void setetiquetasList(ArrayList<String> etiquetasList) {
        if (etiquetasList != null) {
            if (etiquetasList.size() > 0) {
                this.etiquetasList = etiquetasList;
            }
        }
    }

    public HashMap<String, String> getParametros() {
        return parametros;
    }

    public void addParametro(String key, String value) {
        if (value!=null) {
            this.parametros.put(key, value);
        }
    }

    public void setParametros(HashMap<String, String> parametros) {
        this.parametros = parametros;
    }

    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }
}
