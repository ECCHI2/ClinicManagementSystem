package com.clinic.facade;

import com.clinic.entity.Users;
import com.clinic.facadeLocal.UserFacadeLocal;
import jakarta.ejb.Stateless;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Stateless
public class UserFacade extends AbstractFacade implements UserFacadeLocal {

    @Override
    public void create(Users entity) {
        entityManager.persist(entity);
    }

    @Override
    public void edit(Users entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(Users entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    @Override
    public Users find(Object id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public List<Users> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
        Root<Users> root = cq.from(Users.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Users login(String username, String password) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Users> cq = cb.createQuery(Users.class);
            Root<Users> root = cq.from(Users.class);
            // استخدام CriteriaBuilder للبحث عن المستخدم وكلمة المرور
            cq.select(root).where(
                    cb.and(
                            cb.equal(root.get("username"), username),
                            cb.equal(root.get("password"), password)
                    )
            );
            return entityManager.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            return null; // في حال عدم وجود المستخدم أو خطأ بالبيانات
        }
    }
}