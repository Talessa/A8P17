package com.company.manager;

import com.company.model.Equip;


import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class ManagerEquips2 {
    static String directory = "src\\com\\company\\files\\";
    static String ruta = directory + "equipos.txt";
    static String rutaTmp = directory + "equipostmp.txt";


    public static Equip inscriureEquip(String nom){


        // las clases filewriter o fileread necesitan estar dentro de un try para que puedan funcionar
        // y el try a de estar den
        try{
            int id = obtenirUltimIdEquip();
            id=id+1;
            // las clases filewriter o fileread necesitan estar dentro de un try para que puedan funcionar
            // y el try a de estar den
            BufferedWriter file = new BufferedWriter(new FileWriter(ruta, true));
            String line = String.valueOf(id)+":"+nom;
            file.write(line);
            file.newLine();
            file.close();
            Equip equip = new Equip(nom);
            return equip;
        }catch (IOException e){
            System.out.println("El fichero no se a podido abrir.");
        }

        return null;
    }

    public static Equip obtenirEquip(int id){

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (id == Integer.parseInt(partes[0])){
                    Equip equip = new Equip(partes[1]);
                    equip.id = Integer.parseInt(partes[0]);
                    return equip;
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

    public static Equip obtenirEquip(String nom){

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (partes[1].toLowerCase().equals(nom.toLowerCase())){
                    Equip equip = new Equip(partes[1]);
                    equip.id = Integer.parseInt(partes[0]);
                    return equip;
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

    public static String obtenirNomEquip(int id){

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (id == Integer.parseInt(partes[0])){
                    return partes[1];

                }
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }

        return "";
    }

    public static Equip[] obtenirLlistaEquips(){

        Equip[] llistaEquips = new Equip[obtenirNumeroEquips()];

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            int i =0;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                Equip equip= new Equip(partes[1]);
                equip.id= Integer.parseInt(partes[0]);
                llistaEquips[i]=equip;
                i++;
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }

        return llistaEquips;
    }

    public static Equip[] buscarEquipsPerNom(String nom){

        Equip[] llistaEquips = new Equip[obtenirNumeroEquipsPerNom(nom)];

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            int i =0;
            while ((linea= file.readLine()) != null){
                String[] partes = linea.split(":");
                if (partes[1].toLowerCase().contains(nom.toLowerCase())) {
                    Equip equip = new Equip(partes[1]);
                    equip.id = Integer.parseInt(partes[0]);
                    llistaEquips[i] = equip;
                    i++;
                }
            }
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("El fichero no existe");
        }catch (IOException e){
            System.out.println("El fichero no se pudo leer");
        }

        return llistaEquips;
    }

    public static boolean existeixEquip(String nom){

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

    public static void modificarNomEquip(int id, String nouNom){
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

    public static void esborrarEquip(int id){
        try {
        BufferedReader file = new BufferedReader(new FileReader(ruta));
        BufferedWriter Wfile = new BufferedWriter(new FileWriter(rutaTmp,true));

        String linea;
        while ((linea= file.readLine()) != null){
            String[] partes = linea.split(":");
            if (id !=Integer.parseInt(partes[0])){
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

    private static int obtenirUltimIdEquip(){

        int maxId = 0;

        try {
            BufferedReader file = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea= file.readLine()) != null ){
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

    private static int obtenirNumeroEquips(){

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

    private static int obtenirNumeroEquipsPerNom(String nom){

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
