package edu.cunoc.DB;

import edu.cunoc.Enlace.Respuesta;

import java.io.*;

public class LectorDB {

    public LectorDB() {
    }

    public DB readBinDB(Respuesta respuesta){
        if (checkDB()){
            try {
                FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "web.bin");
                ObjectInputStream ois = new ObjectInputStream(fis);
                return (DB) ois.readObject();
            } catch (FileNotFoundException e) {
                respuesta.addErrorArchivo("No se encontro el archivo.");
                System.out.println("No se encontro el archivo.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error al leer el archivo.");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("El archivo no posee el formato correcto.");
                respuesta.addErrorArchivo("El archivo no posee el formato correcto.");
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean checkDB(){
        String localPath = System.getProperty("user.dir");
        String filePath = localPath + File.separator + "web.bin";
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

}
