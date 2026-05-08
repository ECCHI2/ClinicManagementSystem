package com.clinic.bean;

import com.clinic.entity.Users;
import com.clinic.facadeLocal.UserFacadeLocal;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    private String username;
    private String password;
    private Users currentUser;

    public String login() {
        Users user = userFacade.login(username, password);
        if (user != null) {
            currentUser = user;
            // نزرع العلامة في السيشين مباشرة
            jakarta.faces.context.FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userRole", user.getRole());
            return "index?faces-redirect=true";
        } else {
            // رسالة الخطأ
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Users getCurrentUser() { return currentUser; }
}