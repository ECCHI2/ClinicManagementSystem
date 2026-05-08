package com.clinic.bean;

import com.clinic.entity.Users;
import com.clinic.facade.UserFacade;
import com.clinic.facadeLocal.UserFacadeLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    @EJB // السيرفر سيتكفل بحقن الـ Facade هنا تلقائياً
    private UserFacadeLocal userFacade;

    @PostConstruct
    public void init() {
        try {
            // استدعاء تجريبي لإنشاء الجداول في قاعدة البيانات فور تشغيل التطبيق
            userFacade.findAll();
            System.out.println("--- Database Tables Initialized Successfully ---");
        } catch (Exception e) {
            System.err.println("--- Initialization Error: " + e.getMessage());
        }
    }

    public List<Users> getUsersList() {
        return userFacade.findAll();
    }
}