package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.entity.Ledger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void updateLedgers(List<Ledger> ledgers) throws SQLException {
        String sql = "UPDATE ledgers SET bill_id = :billId, bill_id2 = :billId2 WHERE id like :id";
        EntityManager em = null;
        try {
            em =  emf.createEntityManager();
            em.getTransaction().begin();

            Query query = em.createQuery(sql);
            for (Ledger ledger : ledgers) {
                query.setParameter("billId", ledger.getBill_id())
                        .setParameter("billId2", ledger.getBill_id2())
                        .setParameter("id", ledger.getId())
                        .executeUpdate();
            }
            em.getTransaction().commit();
        }catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
