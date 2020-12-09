package file_handling.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
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

                    while ((data = fis.read()) >= 0){
                        content += (char)data; 
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

}
