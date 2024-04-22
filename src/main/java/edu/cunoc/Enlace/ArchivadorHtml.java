package edu.cunoc.Enlace;

import edu.cunoc.Sitios.Pagina;
import edu.cunoc.Sitios.Sitio;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArchivadorHtml {

    private String path = "/var/www/html/";

    public ArchivadorHtml() {
    }

    public void guardarHtml(String pagina, String sitio,String archivo, Respuesta respuesta){
        File htmlFile = new File(path +sitio+File.separator+pagina+ ".html");
        if (!htmlFile.exists()) {
            try {
                htmlFile.createNewFile();
                FileWriter fw = new FileWriter(htmlFile);
                fw.write(archivo);
                fw.close();
                htmlFile.setReadable(true,false);
                htmlFile.setWritable(true,false);
                htmlFile.setExecutable(true,false);
            } catch (IOException e) {
                respuesta.addErrorArchivo("No se tienen permisos para crear el archivo");
                System.out.println("No se tienen permisos para crear el archivo");
                e.printStackTrace();
            }
        }

/*        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sudo", "touch", path  + nombreArchivo + ".html");
            Process process = processBuilder.start();
            int cerrado = process.waitFor();
            if (cerrado == 0) {

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public void crearSitioDir(String sitio, Respuesta respuesta){
        File dir = new File(path +sitio);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            try {
                deleteSitio(sitio,respuesta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String leerHtml(String nombreArchivo, Respuesta respuesta, String sitio){
        String content = null;
            try {
                Path ruta = Path.of(path + sitio + File.separator +nombreArchivo+ ".html");
                content = Files.readString(ruta);

            } catch (IOException e) {
                respuesta.addErrorArchivo("La pagina:"+nombreArchivo+" no existe");
                System.out.println("Archivo no encontrado");
                e.printStackTrace();
            }
            return content;
    }

    public void deleteSitio(String sitio, Respuesta respuesta) throws IOException {
        File sitioDir = new File(path  + sitio);
        if (sitioDir.exists()) {
            FileUtils.deleteDirectory(sitioDir);
        } else {
            respuesta.addErrorArchivo("El sitio:"+ sitio+" no fue encontrado");
            System.out.println("El sitio:"+ sitio+" no fue encontrado");
            throw new IOException("No se encontro el sitio");
        }
    }

    public void deletePagina(String sitio, String pagina, Respuesta respuesta) throws IOException {
        File pageFile = new File(path+ sitio + File.separator + pagina + ".html");
        if (pageFile.exists()){
            FileUtils.delete(pageFile);
        } else {
            respuesta.addErrorArchivo("La pagina:" + pagina + " no existe.");
            System.out.println("La pagina:" + pagina + " no existe.");
            throw new IOException("No se encontro la pagina");
        }
    }

    public void deleteComp(String sitio, String pagina, String componente, Respuesta respuesta) throws IOException {
        File pageFile =  new File(path + sitio + File.separator + pagina + ".html");
        if (pageFile.exists()){
            String html = leerHtml(pagina,respuesta, sitio);

        } else {
            respuesta.addErrorArchivo("La pagina:" + pagina + " no existe.");
            System.out.println("La pagina:" + pagina + " no existe.");
            throw new IOException("No se encontro la pagina");
        }
    }
}
