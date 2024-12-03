package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.MainApplicationApp;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportDAO {
    EntityManagerFactory emf = (EntityManagerFactory)
            MainApplicationApp.context.getBean("entityManagerFactory", jakarta.persistence.EntityManagerFactory.class);

    public List<Object[]> findByWhatEver(String qry){
        Query q = emf.createEntityManager().createNativeQuery(qry);
        return q.getResultList();
    }
}
