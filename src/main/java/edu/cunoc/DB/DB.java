package edu.cunoc.DB;

import edu.cunoc.Sitios.Sitio;

import java.io.Serializable;
import java.util.HashMap;

public class DB implements Serializable {
    private HashMap<String, Sitio> sitiosDB;
    private boolean empty;

    public DB( DB copia){
        if (copia!=null) {
            this.sitiosDB = copia.getSitiosDB();
            this.empty = copia.isEmpty();
        } else {
            this.sitiosDB = new HashMap<>();
            this.empty = true;
        }
    }

    public DB() {
        this.sitiosDB = new HashMap<>();
        empty = true;
    }

    public HashMap<String, Sitio> getSitiosDB() {
        return sitiosDB;
    }

    public void setSitiosDB(HashMap<String, Sitio> sitiosDB) {
        empty = false;
        this.sitiosDB = sitiosDB;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
