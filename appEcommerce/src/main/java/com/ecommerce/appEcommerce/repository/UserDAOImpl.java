package com.ecommerce.appEcommerce.repository;

import com.ecommerce.appEcommerce.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public User findByUserName(String theUserName) {
        //retrieve from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName= :uName", User.class);
        theQuery.setParameter("uName",theUserName);

        User theUser = null;
        try{
            theUser = theQuery.getSingleResult();
        } catch (Exception e){
            theUser=null;
        }
        return theUser;
    }

    @Override
    @Transactional
    public void save(User theUser) {
        theUser.setEnabled(true);
        entityManager.merge(theUser);
    }
}
