/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_handling.service;

/**
 *
 * @author Johan
 */
public enum ChoiceApplication {
    CONSOLE_APPLICATION("1"),
    CLUBS_APPLICATION("2"),
    EXIT("3");

    private String value;

    ChoiceApplication(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean containsChoice(String value) {
        ChoiceApplication[] actions = ChoiceApplication.values();

        for (ChoiceApplication action : actions) {
            if (action.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}
