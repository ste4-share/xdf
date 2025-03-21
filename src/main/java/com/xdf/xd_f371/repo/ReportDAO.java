package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.MainApplicationApp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportDAO {
    EntityManagerFactory emf = (EntityManagerFactory)
            MainApplicationApp.context.getBean("entityManagerFactory", jakarta.persistence.EntityManagerFactory.class);

    public List<Object[]> findByWhatEver(String qry){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery(qry);
            em.getTransaction().commit();
            return q.getResultList();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }

    }public void updateDataDAO(String qry){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery(qry).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }

    }
}
