package com.example.photoapp160.helpers;

import java.util.Objects;

public class UserBody {
    private String name;
    private String lastName;
    private String login;
    private String password;
    private String disc;
    private String mainPhoto;



    public UserBody(String name, String lastName, String login, String disc, String mainPhoto) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.disc = disc;
        this.mainPhoto = mainPhoto;
    }

    public UserBody(String name, String lastName, String login, String password) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public UserBody(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBody userBody = (UserBody) o;
        return Objects.equals(name, userBody.name) && Objects.equals(lastName, userBody.lastName) && Objects.equals(login, userBody.login) && Objects.equals(password, userBody.password) && Objects.equals(disc, userBody.disc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, login, password, disc);
    }
}
