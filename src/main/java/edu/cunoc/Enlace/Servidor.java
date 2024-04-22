package edu.cunoc.Enlace;

import edu.cunoc.DB.DB;
import edu.cunoc.DB.EscritorDB;
import edu.cunoc.DB.LectorDB;
import edu.cunoc.Nucleo.Actor;
import edu.cunoc.Nucleo.CreadorWebLexer;
import edu.cunoc.Nucleo.CreadorWebParser;
import edu.cunoc.Nucleo.Nucleo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private ServerSocket serverSocket;

    public Servidor() {
        recibir();
    }

    public void recibir(){
        try {
            serverSocket = new ServerSocket(81);
            System.out.println("Servidor creado con puerto 81");
            while (true) {
                Socket cliente = serverSocket.accept();
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                System.out.println("Nueva conexion: " + cliente.getInetAddress());
                String mensaje = in.readLine();
                String line;
                while ((line = in.readLine()) != null) {
                    mensaje += "\n"+line ;
                }
                cliente.shutdownInput();
                Respuesta respuesta = new Respuesta();
                int tipo = Integer.parseInt(String.valueOf(mensaje.charAt(0)));
                StringBuilder sb = new StringBuilder(mensaje);
                sb.deleteCharAt(0);
                String[] particion = mensaje.split("\n", 2);
                String userID = particion[0];
                if (tipo == 0) {

                } else {
                    try {
                        System.out.println(particion[1]);
                        respuesta = procesarTexto(particion[1], userID);
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.println(respuesta.getRespuesta());
                    }
                }
                System.out.println(respuesta.getRespuesta());
                out.println(respuesta.getRespuesta());
                out.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cerrar(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("No se pudo cerrar el servidor");
        }
    }

    public Respuesta procesarTexto(String texto, String userID) throws Exception {
        LectorDB lectorDB = new LectorDB();
        StringReader reader = new StringReader(texto);
        CreadorWebLexer creadorWebLexer = new CreadorWebLexer(reader);
        CreadorWebParser creadorWebParser = new CreadorWebParser(creadorWebLexer);
        creadorWebParser.parse();
        Nucleo nucleo = creadorWebParser.getNucleo();
        Respuesta respuesta = creadorWebParser.getRespuesta();
        DB dbPrevia = lectorDB.readBinDB(respuesta);
        DB db = new DB(dbPrevia);
        Actor actor = new Actor();
        CreadorWeb creadorWeb = new CreadorWeb();
        EscritorDB escritorDB = new EscritorDB(respuesta);
        try {
            db = actor.correrAcciones(db, nucleo.getAcciones(), respuesta, userID);
            if (!respuesta.isErronea()) {
                creadorWeb.buildHtml(db, respuesta);

            }
            if (respuesta.isErronea()) {
                creadorWeb.buildHtml(dbPrevia, respuesta);
            } else {
                escritorDB.guardarDB(db);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            if (lectorDB.readBinDB(respuesta)!=null){
                escritorDB.guardarDB(dbPrevia);
                creadorWeb.buildHtml(lectorDB.readBinDB(respuesta), respuesta);
            }

        }
        return respuesta;
    }
}
