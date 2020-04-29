package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.db.CreditRequestDAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Component
public class StartupListener {
    @Autowired
    private CreditRequestDAO requests;

    @EventListener
    @Transactional
    public void applicationStarted(ContextRefreshedEvent event) {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProdPersistenceUnit");
//        sce.getServletContext().setAttribute("factory", factory);


    }
}

