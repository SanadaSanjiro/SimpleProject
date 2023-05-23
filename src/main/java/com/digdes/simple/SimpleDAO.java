package com.digdes.simple;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SimpleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public SimpleModel getById(Long id) {
        return (SimpleModel) entityManager.createNativeQuery("SELECT * FROM taskmanager_db.simple WHERE id =:id",
                        SimpleModel.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
