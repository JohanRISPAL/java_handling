/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_handling.service;

import file_handling.manager.ClubManager;
import file_handling.manager.ConsoleManager;
import file_handling.manager.FileManager;
import file_handling.model.Member;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johan
 */
public class ClubService {
    
    private boolean done;
    private ClubManager clubManager;

    public ClubService() {
        done = false;
        clubManager = new ClubManager();
    }

    public void run() throws IOException {
        // print the beautiful app title
        printApplicationTitle();

        String answer;

        do {
            // print the menu & get the user's answer back in return
            answer = printMenu();

            // handle the user action
            handleClub(answer);
        } while (!answer.equalsIgnoreCase(ClubActions.EXIT.getValue())); // loop while until user wants to exit

        done = true;
    }

   private void handleClub(String answer) throws IOException {
        if(answer.equals(ClubActions.LIST_CLUB.getValue())){
            listClubs();
        }
        
        if(answer.equals(ClubActions.LIST_MEMBER_OF_CLUB.getValue())){
            listMemberClubs();
        }
        
        if(answer.equals(ClubActions.ADD_MEMBER_IN_CLUB.getValue())){
            addMembersInClub();
        }
        
        if(answer.equals(ClubActions.REMOVE_MEMBER_IN_CLUB.getValue())){
            removeMembersInClub();
        }
    }

    private String printMenu() {
        boolean rightAnswer = false;
        String answer = "";

        do {
            // print the option menu
            ConsoleManager.getInstance().printLine();
            ConsoleManager.getInstance().printToConsole("Current path - "+clubManager.getCurrentPath(), true);
            ConsoleManager.getInstance().printLine();
            ConsoleManager.getInstance().consoleLineBreak();
            ConsoleManager.getInstance().printToConsole("What do you want to do ? ", true);
            ConsoleManager.getInstance().printToConsole(ClubActions.LIST_CLUB.getValue() + " - List clubs ", true);
            ConsoleManager.getInstance().printToConsole(ClubActions.LIST_MEMBER_OF_CLUB.getValue() + " - List members of a club ", true);
            ConsoleManager.getInstance().printToConsole(ClubActions.ADD_MEMBER_IN_CLUB.getValue() + " - Add members in a club ", true);
            ConsoleManager.getInstance().printToConsole(ClubActions.REMOVE_MEMBER_IN_CLUB.getValue() + " - Remove members in a club ", true);
            ConsoleManager.getInstance().printToConsole(ClubActions.EXIT.getValue() + " - Exit", true);

            // ask user answer
            answer = ConsoleManager.getInstance().readUserInput();

            if (UserActions.containsAction(answer)) {
                rightAnswer = true;
            }
        } while (!rightAnswer);

        return answer;
    }

    private int listClubs() {
        printActionTitle("Clubs listing");

        int counter = 0;

        for (File file : clubManager.listClub()) {
            ConsoleManager.getInstance().printToConsole(counter + " - " + file.getName(), true);

            counter++;
        }

        if(counter == 0) {
            ConsoleManager.getInstance().printToConsole("There's no file here", true);
        }

        return counter;
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

    private void listMemberClubs() throws IOException {
        printActionTitle("Member of clubs");
        
        int nbClubs = listClubs();

        int answer;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which club's members are you wanting to see ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbClubs);
        
        clubManager.readMember(answer);

    }

    private void addMembersInClub() throws IOException {
        printActionTitle("Add member in club");
        
        int nbClubs = listClubs();

        int answer;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which club are you wanting to add members ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbClubs);
        
        try {
            clubManager.addMember(answer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeMembersInClub() {
        printActionTitle("Remove member in club");
        
        int nbClubs = listClubs();

        int answer;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which club are you wanting to remove members ? ", true);
            answer = ConsoleManager.getInstance().readUserInputInteger();
        } while(answer < 0 || answer >= nbClubs);
        
        List<Member> members = clubManager.listMember(answer);
        
        int counter = 0;
        
        for(Member member : members){
            System.out.println(counter + " - " + member.toString());
            counter++;
        }
        
        int userDelete;
        
        do {
            ConsoleManager.getInstance().printToConsole("Which member are you wanting to remove ? ", true);
            userDelete = ConsoleManager.getInstance().readUserInputInteger();
        } while(userDelete < 0 || userDelete >= counter);
        
        clubManager.removeMember(answer, userDelete);
    }
}
