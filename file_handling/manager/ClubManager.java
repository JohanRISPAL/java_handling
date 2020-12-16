/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_handling.manager;

import file_handling.model.Member;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johan
 */
public class ClubManager {

    private String currentPath;
    private StringBuilder sb;

    public ClubManager() {
        currentPath = "C:\\test\\club\\";
        this.sb = new StringBuilder();
    }

    /**
     * Listing folder files
     *
     * @return
     */
    public List<File> listClub() {
        File currentFolder = new File(currentPath);

        File[] files = currentFolder.listFiles();

        return files != null ? Arrays.asList(files) : new LinkedList<>();
    }
    
    public List<Member> listMember(int index){
        List<Member> members = new LinkedList<>();
        
        File currentFolder = new File(currentPath);
        List<File> folders = new LinkedList<>();

        for (File file : currentFolder.listFiles()) {
            folders.add(file);
        }

        File fileToRead = new File(currentPath + folders.get(index).getName());

        if (fileToRead.isFile() && fileToRead.length() > 0) {
            try (FileInputStream fis = new FileInputStream(fileToRead);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {

                while(fis.available() > 0){
                    Member memberExisting = (Member)ois.readObject();
                    members.add(memberExisting);
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClubManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ClubManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClubManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return members;
    }

    /**
     * Get the current path
     *
     * @return
     */
    public String getCurrentPath() {
        return currentPath;
    }

    public List<Member> readMember(int index) {
        List<Member> members = new LinkedList<>();

        File currentFolder = new File(currentPath);
        List<File> folders = new LinkedList<>();

        for (File file : currentFolder.listFiles()) {
            folders.add(file);
        }

        File fileToRead = new File(currentPath + folders.get(index).getName());

        if (fileToRead.isFile() && fileToRead.length() > 0) {
            try (FileInputStream fis = new FileInputStream(fileToRead);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {

                while(fis.available() > 0){
                    Member memberExisting = (Member)ois.readObject();
                    members.add(memberExisting);
                }

                if (members.size() > 0) {
                    for (Member member : members) {
                        ConsoleManager.getInstance().printToConsole(member.toString(), true);
                    }
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClubManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ClubManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClubManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(fileToRead.length() == 0) {
            ConsoleManager.getInstance().printToConsole("Il n'y a pas encore de membres dans ce club", true);
            
        }else{
            ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
        }

        return members;

    }

    public void addMember(int index) throws FileNotFoundException, IOException, ClassNotFoundException {
        List<Member> members = listMember(index);

        File currentFolder = new File(currentPath);
        List<File> folders = new LinkedList<>();

        for (File file : currentFolder.listFiles()) {
            folders.add(file);
        }

        File clubToAdd = new File(currentPath + folders.get(index).getName());

        ConsoleManager.getInstance().printToConsole("Quel est le nom du joueur ?", true);
        String name = ConsoleManager.getInstance().readUserInput();

        ConsoleManager.getInstance().printToConsole("Quel est le prenom du joueur ?", true);
        String firstName = ConsoleManager.getInstance().readUserInput();

        ConsoleManager.getInstance().printToConsole("Quel est la date de naissance du joueur ?", true);
        String age = ConsoleManager.getInstance().readUserInput();

        ConsoleManager.getInstance().printToConsole("Quel est le numéro de licence du joueur ?", true);
        String licence = ConsoleManager.getInstance().readUserInput();

        Member member = new Member(name, firstName, age, licence);

        try (FileOutputStream fop = new FileOutputStream(clubToAdd);
                ObjectOutputStream oop = new ObjectOutputStream(fop)) {

            for (Member memberExisting : members) {
                oop.writeObject(memberExisting);
            }
            oop.writeObject(member);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        ConsoleManager.getInstance().printToConsole("Joueur ajouté !", true);
    }

    public void removeMember(int index, int userDelete) {
        List<Member> members = listMember(index);
        
        members.remove(userDelete);
        
        File currentFolder = new File(currentPath);
        List<File> folders = new LinkedList<>();

        for (File file : currentFolder.listFiles()) {
            folders.add(file);
        }
        
        File clubToRemove = new File(currentPath + folders.get(index).getName());
        
        try (FileOutputStream fop = new FileOutputStream(clubToRemove);
                ObjectOutputStream oop = new ObjectOutputStream(fop)) {

            for (Member memberExisting : members) {
                oop.writeObject(memberExisting);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClubManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        ConsoleManager.getInstance().printToConsole("Joueur supprimé !", true);
    }


}
