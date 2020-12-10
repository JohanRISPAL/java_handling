package file_handling.service;

import file_handling.manager.ConsoleManager;
import file_handling.manager.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileService {

    private boolean done;
    private FileManager fileManager;

    public FileService() {
        done = false;
        fileManager = new FileManager();
    }

    public void run() throws IOException {
        // print the beautiful app title
        printApplicationTitle();

        String answer;

        do {
            // print the menu & get the user's answer back in return
            answer = printMenu();

            // handle the user action
            handleAction(answer);
        } while (!answer.equalsIgnoreCase(UserActions.EXIT.getValue())); // loop while until user wants to exit

        done = true;
    }

    private void handleAction(String action) throws IOException {
        if (action.equalsIgnoreCase(UserActions.LIST_FILES.getValue())) {
            listFiles();
        }

        if (action.equalsIgnoreCase(UserActions.CREATE_FILE.getValue())) {
            createFile();
        }

        if (action.equalsIgnoreCase(UserActions.CREATE_FOLDER.getValue())) {
            createFolder();
        }

        if (action.equalsIgnoreCase(UserActions.EDIT_FILE.getValue())) {
            editFile();
        }

        if (action.equalsIgnoreCase(UserActions.DELETE_FILE.getValue())) {
            deleteAFile();
        }

        if (action.equalsIgnoreCase(UserActions.GO_IN_FOLDER.getValue())) {
            moveInto();
        }
        
        if (action.equalsIgnoreCase(UserActions.READ_TXT_FILE.getValue())) {
            readFile();
        }
        
        if (action.equalsIgnoreCase(UserActions.WRITE_TXT_FILE.getValue())) {
            writeFile();
        }
        
        if (action.equalsIgnoreCase(UserActions.COPY_FILE.getValue())) {
            copyFile();
        }
        
        if (action.equalsIgnoreCase(UserActions.TEST_PERF.getValue())) {
            testPerf();
        }

        if (action.equalsIgnoreCase(UserActions.BACK_FOLDER.getValue())) {
            back();
        }
    }

    /**
     * Print the menu
     * @return the action answer number
     */
    private String printMenu() {
        boolean rightAnswer = false;
        String answer = "";

        do {
            // print the option menu
            ConsoleManager.getInstance().printLine();
            ConsoleManager.getInstance().printToConsole("Current path - "+fileManager.getCurrentPath(), true);
            ConsoleManager.getInstance().printLine();
            ConsoleManager.getInstance().consoleLineBreak();
            ConsoleManager.getInstance().printToConsole("What do you want to do ? ", true);
            ConsoleManager.getInstance().printToConsole(UserActions.LIST_FILES.getValue() + " - List files ", true);
            ConsoleManager.getInstance().printToConsole(UserActions.CREATE_FILE.getValue()+" - Create a file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.CREATE_FOLDER.getValue() + " - Create a folder", true);
            ConsoleManager.getInstance().printToConsole(UserActions.EDIT_FILE.getValue() + " - Edit a file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.DELETE_FILE.getValue() + " - Delete a file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.GO_IN_FOLDER.getValue() + " - Go into folder", true);
            ConsoleManager.getInstance().printToConsole(UserActions.BACK_FOLDER.getValue() + " - Move back one folder", true);
            ConsoleManager.getInstance().printToConsole(UserActions.READ_TXT_FILE.getValue() + " - Read a txt file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.WRITE_TXT_FILE.getValue() + " - Write in a txt file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.COPY_FILE.getValue() + " - Copy a file in another file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.TEST_PERF.getValue() + " - Test the performance of reading a file", true);
            ConsoleManager.getInstance().printToConsole(UserActions.EXIT.getValue() + " - Exit", true);

            // ask user answer
            answer = ConsoleManager.getInstance().readUserInput();

            if (UserActions.containsAction(answer)) {
                rightAnswer = true;
            }
        } while (!rightAnswer);

        return answer;
    }

    private Integer listFiles() {
        printActionTitle("Files listing");

        int counter = 0;

        for (File file : fileManager.listFiles()) {
            ConsoleManager.getInstance().printToConsole(counter + " - " + file.getName(), true);

            counter++;
        }

        if(counter == 0) {
            ConsoleManager.getInstance().printToConsole("There's no file here", true);
        }

        return counter;
    }

    private Integer listFolders() {
        int counter = 0;

        for (File file : fileManager.listFiles()) {
            // test if its a folder
            if(!file.isFile()) {
                ConsoleManager.getInstance().printToConsole(counter + " - " + file.getName(), true);

                counter++;
            }
        }

        if(counter == 0) {
            ConsoleManager.getInstance().printToConsole("There's no folder here", true);
        }

        return counter;
    }

    private void createFile() {
        printActionTitle("File creation");

        ConsoleManager.getInstance().printToConsole("Type file name to create : ", true);

        ConsoleManager.getInstance().consoleLineBreak();

        String name = ConsoleManager.getInstance().readUserInput();
        File file = fileManager.createTxtFile(name);

        if (file == null) {
            ConsoleManager.getInstance().printToConsole("Une erreur est survenue lors de la création...", true);
        } else {
            ConsoleManager.getInstance().printToConsole("Fichier créé", true);
        }
    }

    private void createFolder() {
        printActionTitle("Folder creation");

        ConsoleManager.getInstance().printToConsole("Type folder name to create : ", true);

        String name = ConsoleManager.getInstance().readUserInput();
        fileManager.createFolder(name);

        ConsoleManager.getInstance().printToConsole("Dossier créé", true);
    }

    private void deleteAFile() {
        printActionTitle("File deletion");

        // list files for user to choose
        int nbFiles = listFiles();

        int answer;

        do {
            ConsoleManager.getInstance().printToConsole("Which file do you want to delete ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFiles);

        fileManager.deleteFileFromList(answer);
    }

    private void back() {
		fileManager.backOneFolder();
	}

	private void moveInto() {
		printActionTitle("List Folders");

        // list folders for user to choose
        int nbFolders = listFolders();

        int answer;

        do {
            ConsoleManager.getInstance().printToConsole("Which folder do you want to enter ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFolders);

        fileManager.enterFolder(answer);
	}

	private void editFile() {
		// TODO Auto-generated method stub

	}

    private void printApplicationTitle() {
        ConsoleManager.getInstance().consoleLineBreak();
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().printToConsole("File System Manager", true);
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().consoleLineBreak();
    }

    private void printActionTitle(String title) {
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().printToConsole(title, true);
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().consoleLineBreak();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    private void readFile() {
        printActionTitle("Read file");
        
        int nbFiles = listFiles();
        
        int answer;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which file do you want to read ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFiles);
        
        try {
            fileManager.readFile(answer);
        } catch (IOException ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeFile() throws IOException {
        printActionTitle("Write file");
        
        int nbFiles = listFiles();
        
        int answer;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which file do you want to write ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFiles);
        
        fileManager.writeFile(answer);
    }

    private void copyFile() throws IOException {
        printActionTitle("Copy file");
        
        int nbFiles = listFiles();
        
        int answer;
        int answer2;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which file do you want to copy ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
            ConsoleManager.getInstance().printToConsole("Which file do you want to paste ? ", true);
            answer2 = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbFiles);
        
        fileManager.copyFile(answer, answer2);
    }

    private void testPerf() {
        printActionTitle("Time reading file");
        
        int nbFiles = listFiles();
        
        int answer;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which file do you want to compare ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();;
        } while(answer < 0 || answer >= nbFiles);
        
        fileManager.testPerf(answer);
    }
}
