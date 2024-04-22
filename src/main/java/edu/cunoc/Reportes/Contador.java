package edu.cunoc.Reportes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Contador {

    public Contador() {
    }

    public String getLogFile(){



        String[] cmd = {"/bin/bash","-c","echo password| sudo -S ls"};
        try {
        Process pb = Runtime.getRuntime().exec(cmd);
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
