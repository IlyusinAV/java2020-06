package ru.ilyusin.entities;

import java.beans.JavaBean;

@JavaBean
public class AuthBaseAbstract {
    private int id;
    private String name;
    private int cliendID;


    public AuthBaseAbstract(int id, String name, int cliendID) {
        this.id = id;
        this.name = name;
        this.cliendID = cliendID;
    }
}