package com.clinic.facadeLocal; // تأكد إن الـ f صغيرة مثل اسم المجلد

import com.clinic.entity.Users;
import java.util.List;
import jakarta.ejb.Local;

@Local
public interface UserFacadeLocal {
    void create(Users entity);
    void edit(Users entity);
    void remove(Users entity);
    List<Users> findAll();
    // السطر اللي كان ناقصك:
    Users login(String username, String password);
    Users find(Object id);
}