package edu.cunoc.Enlace;

public class Respuesta {
    private String respuesta;
    private boolean erronea;

    public Respuesta() {
        this.respuesta = "";
        this.erronea = false;
    }

    public void addErrorLexico(String error){
        erronea = true;
        this.respuesta += "<LEXICO>\n[\"" + error + "\"]\n</LEXICO>\n";
    }

    public void addErrorSintactico(String error){
        erronea = true;
        this.respuesta += "<SINTACTICO>\n[\"" + error + "\"]\n</SINTACTICO>\n";
    }

    public void addErrorArchivo(String error){
        erronea = true;
        this.respuesta += "<ARCHIVO>\n[\"" + error + "\"]\n</ARCHIVO>\n";
    }

    public void addExito(String exito) throws IllegalArgumentException {
            respuesta += "<EXITO>\n[\""+exito+"\"]\n</EXITO>\n";

    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isErronea() {
        return erronea;
    }

    public void setErronea(boolean erronea) {
        this.erronea = erronea;
    }
}
