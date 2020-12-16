/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_handling.manager;

import file_handling.service.ChoiceApplication;
import file_handling.service.ClubActions;
import file_handling.service.ClubService;
import file_handling.service.FileService;
import file_handling.service.UserActions;
import java.io.IOException;

/**
 *
 * @author Johan
 */
public class ApplicationManager {
    
    private boolean done;
    private FileManager fileManager;


    public ApplicationManager(){
        done = false;
        fileManager = new FileManager();
    }

    public void run() throws IOException {

        String choiceAppli;
        do {
            choiceAppli = printChoiceAppliMenu();  
        
        
            if(choiceAppli.equals(ChoiceApplication.CONSOLE_APPLICATION.getValue())){
                // instantiate application
                FileService applicationService = new FileService();

                // launch application
                applicationService.run();
            }

            if(choiceAppli.equals(ChoiceApplication.CLUBS_APPLICATION.getValue())){
                // instantiate application
                ClubService applicationClub = new ClubService();

                // launch application
                applicationClub.run();

            }
            
        } while (!choiceAppli.equalsIgnoreCase(ChoiceApplication.EXIT.getValue())); // loop while until user wants to exit
 
        done = true;
    }

    private String printChoiceAppliMenu(){
        boolean rightAnswer = false;
        String answer = "";
        
        do{
            printActionTitle("Application Manager");
            ConsoleManager.getInstance().printToConsole(ChoiceApplication.CONSOLE_APPLICATION.getValue() + " - Console applications ", true);
            ConsoleManager.getInstance().printToConsole(ChoiceApplication.CLUBS_APPLICATION.getValue()+" - Club applications", true);
            ConsoleManager.getInstance().printToConsole(ChoiceApplication.EXIT.getValue()+" - Exit", true);

            answer = ConsoleManager.getInstance().readUserInput();

            if (ChoiceApplication.containsChoice(answer)) {
                rightAnswer = true;
            }
        }while(!rightAnswer);
        
        return answer;
        
    }
    
    private void printActionTitle(String title) {
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().printToConsole(title, true);
        ConsoleManager.getInstance().printLine();
        ConsoleManager.getInstance().consoleLineBreak();
    }
 
}
