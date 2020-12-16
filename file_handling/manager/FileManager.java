package file_handling.manager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class FileManager {

	private String currentPath;
	private StringBuilder sb;

	public FileManager() {
		currentPath = "C:\\test\\";
		this.sb = new StringBuilder();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public List<File> listFiles() {
		File currentFolder = new File(currentPath);

		File[] files = currentFolder.listFiles();

		return files != null ? Arrays.asList(files) : new LinkedList<>();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public void deleteFileFromList(int index) {
		File currentFolder = new File(currentPath);

		currentFolder.listFiles()[index].delete();
	}

	/**
	 * Create a .txt file
	 * 
	 * @param name the name of the file to create (with no extension)
	 * @return
	 */
	public File createTxtFile(String name) {
		File file = new File(currentPath + name + ".txt");

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}


	/**
	 * Move the path to a folder
	 * 
	 * @param index of the folder to move into
	 */
	public void enterFolder(int index) {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			if (!file.isFile()) {
				folders.add(file);
			}
		}

		currentPath += folders.get(index).getName() + "\\";
	}

	/**
	 * Go back one folder from the current path
	 */
	public void backOneFolder() {
		List<String> paths = Arrays.asList(currentPath.split(Pattern.quote("\\")));

		if(paths.size() > 0) {
			ConsoleManager.getInstance().printToConsole(""+paths.size(), true);
			paths = paths.subList(0, paths.size() - 1);

			currentPath = String.join("\\", paths);
                        currentPath += "\\";
		}

		if (currentPath.isEmpty()) {
			currentPath = "\\";
		}
	}

	public void createFolder(String name) {
		File file = new File(currentPath + name);

		file.mkdir();
	}


	/**
	 * Get the current path
	 *
	 * @return
	 */
	public String getCurrentPath() {
		return currentPath;
	}
        
        public void readFile(int index) throws IOException{
            File currentFolder = new File(currentPath);
            List<File> folders = new LinkedList<>();

            for (File file : currentFolder.listFiles()) {
                            folders.add(file);
            }
            
            String content = "";

            File fileToRead = new File(currentPath + folders.get(index).getName());
            
            if(fileToRead.isFile()){
                try(FileInputStream fis = new FileInputStream(fileToRead)) {
                    int data;

                    if((data = fis.read()) == 0){
                        content += "Le fichier est vide !";
                    }else{
                        while ((data = fis.read()) >= 0){
                            content += (char)data; 
                        }
                    }

                    ConsoleManager.getInstance().printLine();
                    ConsoleManager.getInstance().printToConsole("Contenu du fichier", true);
                    ConsoleManager.getInstance().printLine();

                    ConsoleManager.getInstance().printToConsole(content, true);

                    ConsoleManager.getInstance().consoleLineBreak();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
            }
            
        }

    public void writeFile(int index) throws IOException {
            File currentFolder = new File(currentPath);
            List<File> folders = new LinkedList<>();

            for (File file : currentFolder.listFiles()) {
                            folders.add(file);
            }
            
            ConsoleManager.getInstance().printToConsole("What do you want to write ?", true);
            String content = ConsoleManager.getInstance().readUserInput();
            

            File fileToWrite = new File(currentPath + folders.get(index).getName());
            
            if(fileToWrite.isFile()){
                try(FileOutputStream fop = new FileOutputStream(fileToWrite)) {
                    byte[] contentInBytes = content.getBytes();
                    
                    fop.write(contentInBytes);
                    fop.flush();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
            }
    }

    public void copyFile(int index, int index2) throws IOException {
        File currentFolder = new File(currentPath);
            List<File> folders = new LinkedList<>();

            for (File file : currentFolder.listFiles()) {
                            folders.add(file);
            }
            
            String content = "";

            File fileToRead = new File(currentPath + folders.get(index).getName());
            File fileToCopy = new File(currentPath + folders.get(index2).getName());
            
            FileOutputStream fop = new FileOutputStream(fileToCopy);
            
            if(fileToRead.isFile()){
                try(FileInputStream fis = new FileInputStream(fileToRead)) {
                    int data;

                    
                    while ((data = fis.read()) >= 0){
                        content += (char)data; 
                    }
                    
                    
                    byte[] contentInBytes = content.getBytes();
                    
                    fop.write(contentInBytes);
                    fop.flush();

                    ConsoleManager.getInstance().printLine();
                    ConsoleManager.getInstance().printToConsole("Fichier copié", true);
                    ConsoleManager.getInstance().printLine();


                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    try{
                        if (fop != null){
                            fop.close();
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }   
                }
            }else{
                ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
            }
    }

    public void testPerf(int index) throws IOException {
            File currentFolder = new File(currentPath);
            List<File> folders = new LinkedList<>();

            for (File file : currentFolder.listFiles()) {
                            folders.add(file);
            }

            File fileToRead = new File(currentPath + folders.get(index).getName());
            
            Date startFis = new Date();
            if(fileToRead.isFile()){
                try(FileInputStream fis = new FileInputStream(fileToRead)) {
                    int data;

                    while ((data = fis.read()) >= 0){
                        fis.read();
                    }
  
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
            }
            Date stopFis = new Date();
            
            Date startBis = new Date();
            if(fileToRead.isFile()){
                try(FileInputStream fis = new FileInputStream(fileToRead)) {
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    int data;

                    while ((data = bis.read()) >= 0){
                        bis.read();
                    }
  
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
            }
            Date stopBis = new Date();
            
            long timeDiffFis = stopFis.getTime() - startFis.getTime();
            long timeDiffBis = stopBis.getTime() - startBis.getTime();
            
            ConsoleManager.getInstance().printToConsole("Durée InputString : " + Long.toString(timeDiffFis), true);
            ConsoleManager.getInstance().printToConsole("Durée BufferedReader : " + Long.toString(timeDiffBis), true);
    }

    public void writeType(int index) throws IOException {
        File currentFolder = new File(currentPath);
            List<File> folders = new LinkedList<>();

            for (File file : currentFolder.listFiles()) {
                            folders.add(file);
            }

            File file = new File(currentPath + folders.get(index).getName());
            
        if(file.isFile()){
             try(FileInputStream fis = new FileInputStream(file);
                     FileOutputStream fop = new FileOutputStream(file);
                     DataInputStream ois = new DataInputStream(fis); 
                     DataOutputStream oop = new DataOutputStream(fop)
                     ) {
                 
                 
                char myChar = 'a';
                double myDouble = 42.42;
                int myInt = 5;
                boolean myBool = true;
                
                oop.writeChar(myChar);
                oop.writeDouble(myDouble);
                oop.writeInt(myInt);
                oop.writeBoolean(myBool);
                
                oop.flush();
                
                int data;
                String content = "";
                
                char myCharRead = ois.readChar();
                double myDoubleRead = ois.readDouble();
                int myIntRead = ois.readInt();
                boolean myBoolRead = ois.readBoolean();

                ConsoleManager.getInstance().printToConsole("Contenu du fichier : " + myCharRead + myDoubleRead + myIntRead + myBoolRead, true);

             } catch (FileNotFoundException ex) {
                 Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
             }
         }else{
             ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
         } 
    }

}
