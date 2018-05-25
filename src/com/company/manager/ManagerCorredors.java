package com.company.manager;

import com.company.model.Corredor;
import com.company.model.Equip;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ManagerCorredors {

    static String directory = "src\\com\\company\\files\\";
    static String ruta = directory + "corredores.txt";
    static String rutaTmp = directory + "corredorestmp.txt";

    public static Corredor inscriureCorredor(String nom, Equip equip){

        if(equip == null){
            return null;
        }
        int id = obtenirUltimIdCorredor();
        id=id+1;
        // las clases filewriter o fileread necesitan estar dentro de un try para que puedan funcionar
        // y el try a de estar den
        try{
            BufferedWriter file = new BufferedWriter(new FileWriter(ruta, true));
            String line = String.valueOf(id)+":"+nom+":"+String.valueOf(equip.id);
            file.write(line);
            file.newLine();
            file.close();
        }catch (IOException e){
            System.out.println("No se a podido inscribir el corredor.");
        }

        return null;
    }

    public static Corredor obtenirCorredor(int id){

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (id == Integer.parseInt(partes[0])){
                    Corredor corredor= new Corredor(partes[1],Integer.parseInt(partes[2]));
                    corredor.id= Integer.parseInt(partes[0]);
                    return corredor;

                }
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }
        return null;
    }

    public static Corredor[] obtenirLlistaCorredors(){

        Corredor[] llistaCorredors = new Corredor[obtenirNumeroCorredors()];

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            int i =0;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                Corredor corredor= new Corredor(partes[1],Integer.parseInt(partes[2]));
                corredor.id= Integer.parseInt(partes[0]);
                llistaCorredors[i]=corredor;
                i++;
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }


        return llistaCorredors;
    }

    public static Corredor[] buscarCorredorsPerNom(String nom){
        Corredor[] llistaCorredors = new Corredor[obtenirNumeroCorredorsPerNom(nom)];

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            int i =0;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (partes[1].toLowerCase().contains(nom.toLowerCase())) {
                    Corredor corredor = new Corredor(partes[1], Integer.parseInt(partes[2]));
                    corredor.id = Integer.parseInt(partes[0]);
                    llistaCorredors[i] = corredor;
                    i++;
                }
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }

        return llistaCorredors;
    }

    public static boolean existeixCorredor(String nom){
        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (nom.toLowerCase().equals(partes[1].toLowerCase())){
                    return true;

                }
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }
        return false;
    }

    public static void modificarNomCorredor(int id, String nouNom){

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            BufferedWriter Wfile = new BufferedWriter(new FileWriter(rutaTmp,true));

            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (id==Integer.parseInt(partes[0])){
                    String N_linea = partes[0]+":"+nouNom+":"+partes[2];
                    Wfile.write(N_linea);
                    Wfile.newLine();
                }else {
                    Wfile.write(linea);
                    Wfile.newLine();
                }
            }
            file.close();
            Wfile.close();
            Files.move(FileSystems.getDefault().getPath(rutaTmp),
                    FileSystems.getDefault().getPath(ruta),REPLACE_EXISTING);

        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }
    }

    public static void modificarEquipCorredor(int id, Equip nouEquip){
        if(nouEquip == null){
            return;
        }
        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            BufferedWriter Wfile = new BufferedWriter(new FileWriter(rutaTmp,true));

            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (id==Integer.parseInt(partes[2])){
                    String N_linea = partes[0]+":"+partes[1]+":"+nouEquip.id;
                    Wfile.write(N_linea);
                    Wfile.newLine();
                }else {
                    Wfile.write(linea);
                    Wfile.newLine();
                }
            }
            file.close();
            Wfile.close();
            Files.move(FileSystems.getDefault().getPath("corredorestmp.txt"),
                    FileSystems.getDefault().getPath(ruta),REPLACE_EXISTING);

        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }
//
    }

    public static void esborrarCorredor(int id){
        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            BufferedWriter Wfile = new BufferedWriter(new FileWriter(rutaTmp,true));

            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (id != Integer.parseInt(partes[0])) {
                    Wfile.write(linea);
                    Wfile.newLine();
                }
            }
            file.close();
            Wfile.close();
            Files.move(FileSystems.getDefault().getPath("corredorestmp.txt"),
                    FileSystems.getDefault().getPath(ruta),REPLACE_EXISTING);

        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }
    }

    private static int obtenirUltimIdCorredor(){
        int maxId = 0;
        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                maxId = Integer.parseInt(partes[0]);
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }

        return maxId;
    }
    private static int obtenirNumeroCorredors(){
        int count = 0;
        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            while ((file.readLine()) != null ){
                count++;
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }

        return count;
    }

    private static int obtenirNumeroCorredorsPerNom(String nom){
        int count = 0;

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (partes[1].toLowerCase().contains(nom.toLowerCase())){
                    count++;
                }
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }

        return count;
    }
}