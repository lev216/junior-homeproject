package project.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import project.model.*;
import project.tests.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CreditRequestDAOTest {
//    private EntityManagerFactory factory;
//    private EntityManager manager;
//    private CreditRequestDAO requests;
//
//    @Before
//    public void connect() {
//        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
//        manager = factory.createEntityManager();
//        requests = new CreditRequestDAO(manager);
//
//    }
//
//    @After
//    public void close() {
//        if (manager != null) {
//            manager.close();
//        }
//        if (factory != null) {
//            factory.close();
//        }
//    }
    @Autowired
    public CreditRequestDAO requests;

    @Test
    @Transactional
    public void bigTest() {
        ClientAccountant accountant = requests.createClient("romashka", "123", "ООО Ромашка", 7705002757L);
        ClientCreditRequest request = requests.createCreditRequest(accountant, CreditType.WORKING, 5000000, 12, 200000000, 15000000, 90000000, 150000000);
        CreditWorker worker = requests.createCreditWorker("smartGuy", "123");
        //requests.assignCreditWorkerToRequest(request, worker);
        request.setWorker(worker);
        requests.countLimit(request);
        requests.makeCreditDecision(request, accountant);
        assertNotNull(accountant);
        assertNotNull(request);
        assertNotNull(worker);
        assertEquals("romashka", accountant.getLogin());
        assertEquals(7705002757L, accountant.getClientITN());
        assertEquals(5000000, request.getSum());
        assertNotEquals(CreditType.INVESTMENT, request.getCreditType());
        //5ск-вб = 300000000
        //оборот 33333333
        //инвест 30000000
        assertNotEquals("dumpGuy", worker.getLogin());
        assertEquals(33333333, request.getLimit());
        assertEquals(CreditDecision.APPROVE, worker.getDecision(request));

//        try {
//            requests.createCreditWorker("smartGuy", "123");
//            fail("there is the same login");
//        } catch (PersistenceException exc) {
//
//        }
        //request.getId();
        ClientCreditRequest foundRequest = requests.findRequestById(2);
        assertNotNull(foundRequest);
        assertEquals(request.getId(), foundRequest.getId());

        ClientAccountant foundClient = requests.findClientByITN(7705002757L);
        assertNotNull(foundClient);
        assertEquals(accountant.getClientITN(), foundClient.getClientITN());

        CreditWorker foundWorker = requests.findCreditWorkerByLogin("smartGuy");
        assertNotNull(foundWorker);
        assertEquals(worker.getLogin(), foundWorker.getLogin());

//        ClientAccountant foundClientById = manager.find(ClientAccountant.class, accountant.getId());
//        ClientCreditRequest foundRequestById = manager.find(ClientCreditRequest.class, request.getId());
//        manager.refresh(foundClientById);
//        manager.refresh(foundRequestById);
    }
}
