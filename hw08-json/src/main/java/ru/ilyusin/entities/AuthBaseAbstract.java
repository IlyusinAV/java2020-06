package ru.ilyusin.entities;

import java.beans.JavaBean;
import java.util.Objects;

public class AuthBaseAbstract {
    private int id;
    private String name;
    private int cliendID;


    public AuthBaseAbstract(int id, String name, int cliendID) {
        this.id = id;
        this.name = name;
        this.cliendID = cliendID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCliendID() {
        return cliendID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthBaseAbstract that = (AuthBaseAbstract) o;
        return id == that.id && cliendID == that.cliendID && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cliendID);
    }

    @Override
    public String toString() {
        return "AuthBaseAbstract{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cliendID=" + cliendID +
                '}';
    }
}