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
public enum ClubActions {
    LIST_CLUB("1"),
    LIST_MEMBER_OF_CLUB("2"),
    ADD_MEMBER_IN_CLUB("3"),
    REMOVE_MEMBER_IN_CLUB("4"),
    EXIT("5");

    private String value;

    ClubActions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean containsAction(String value) {
        ClubActions[] actions = ClubActions.values();

        for (ClubActions action : actions) {
            if (action.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}
