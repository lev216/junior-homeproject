package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import static org.junit.Assert.*;


public class ModelTest {
    private EntityManagerFactory factory;
    private EntityManager manager;

    @Before
    public void connect() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
    }

    @After
    public void close() {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }

    }

    @Test
    public void createClientAccountantTest() {
        ClientAccountant accountant = new ClientAccountant();
        accountant.setLogin("romashka");
        accountant.setClientName("ООО Ромашка");
        accountant.setClientITN(7701573895L);

        ClientCreditRequest request = new ClientCreditRequest();
        request.setCreditType(CreditType.INVESTMENT);
        request.setNetAssets(5075000);
        request.setProfit(1354000);
        request.setTotalAssets(9534000);
        request.setRevenue(145650000);
        request.setSum(2000000);
        request.setTerm(60);
        //5ск - вб = 15841000
        //инвест 2708000
        //оборот 24275000


        CreditWorker worker = new CreditWorker();
        worker.setLogin("terminator");
        worker.setLimit(request, (int) (request.getCreditType().equals(CreditType.INVESTMENT) ? ((5 * request.getNetAssets() - request.getTotalAssets()) < 2 * request.getProfit() ? (5 * request.getNetAssets() - request.getTotalAssets()) : 2 * request.getProfit()) : (5 * request.getNetAssets() - request.getTotalAssets()) < 2 * request.getProfit() ? (5 * request.getNetAssets() - request.getTotalAssets()) : (request.getRevenue() / 6)));
        worker.setDecision(request, worker.getLimit(request) > (request.getSum() + accountant.getLiability()) ? CreditDecision.APPROVE : CreditDecision.DENY);
        accountant.setLiability(worker.getDecision(request).equals(CreditDecision.APPROVE) ? (accountant.getLiability() + request.getSum()) : accountant.getLiability());

        request.setAccountant(accountant);
        request.setWorker(worker);

        manager.getTransaction().begin();
        manager.persist(accountant);
        manager.persist(worker);
        manager.persist(request);



        ClientAccountant foundAccountant = manager.find(ClientAccountant.class, accountant.getId());
        assertNotNull(foundAccountant);
        assertSame(foundAccountant, accountant);
        assertEquals(worker.getLimit(request), 2708000);
        assertEquals(accountant.getLiability(), 2000000);
        manager.getTransaction().commit();
        manager.refresh(foundAccountant);
    }
}
