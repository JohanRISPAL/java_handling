/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_handling.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Johan
 */
public class Member implements Serializable{
    
    private static final long serialVersionUID = 3;
    private String name;
    private String firstName;
    private String age;
    private String licence;

    public Member(String name, String firstName, String age, String licence) {
        this.name = name;
        this.firstName = firstName;
        this.age = age;
        this.licence = licence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "Member{" + "name=" + name + ", firstName=" + firstName + ", age=" + age + ", licence=" + licence + '}';
    }
    
    
}
