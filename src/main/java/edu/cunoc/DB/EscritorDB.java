package edu.cunoc.DB;

import edu.cunoc.Enlace.Respuesta;
import edu.cunoc.Sitios.Pagina;
import edu.cunoc.Sitios.Sitio;

import java.io.*;
import java.util.Map;

public class EscritorDB {

    private Respuesta respuesta;

    public EscritorDB(Respuesta respuesta) throws IOException {
        this.respuesta = respuesta;
        crearDB(respuesta);
    }

    public void guardarDB(DB db){
        try {
            FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + File.separator + "web.bin",true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(db);
            outputStream.close();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha podido crear el archivo binario de la base de datos.");
            respuesta.addErrorArchivo("No se ha podido crear el archivo binario de la base de datos.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearDB(Respuesta respuesta) throws IOException {
        String localPath = System.getProperty("user.dir");
        String filePath = localPath + File.separator + "web.bin";
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
                boolean creado = file.createNewFile();
                if (creado){
                    System.out.println("Archivo creado en : " + filePath);
                } else {
                    System.out.println("No se puede crear el archivo");
                    respuesta.addErrorArchivo("No se puede crear el archivo");
                }
        }
    }


}
