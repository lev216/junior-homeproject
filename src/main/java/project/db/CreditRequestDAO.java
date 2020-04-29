package project.db;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.model.*;

import javax.persistence.*;
import java.util.*;
@Repository
public class CreditRequestDAO {
    @PersistenceContext
    private EntityManager manager;

    //@Autowired
//    public CreditRequestDAO(EntityManager manager) {
//        Objects.requireNonNull(manager, "EntityManager shouldn't be null");
//        this.manager = manager;
//    }
    @Transactional
    public ClientCreditRequest createCreditRequest(ClientAccountant accountant, CreditType creditType, long sum, int term, long revenue, int profit, int netAssets, int totalAssets) {
        ClientCreditRequest request = new ClientCreditRequest();
        request.setAccountant(accountant);
        request.setTerm(term);
        request.setCreditType(creditType);
        request.setSum(sum);
        request.setRevenue(revenue);
        request.setProfit(profit);
        request.setNetAssets(netAssets);
        request.setTotalAssets(totalAssets);
//        manager.getTransaction().begin();
//        try {
        manager.persist(request);
//        } catch (Throwable exc) {
//            manager.getTransaction().rollback();
//            throw exc;
//        }
//        manager.getTransaction().commit();
        return request;
    }
    @Transactional
    public void assignCreditWorkerToRequest(int id, CreditWorker worker) {
        //request.setWorker(worker);
//        try {
//            manager.getTransaction().begin();
            manager.createQuery("UPDATE ClientCreditRequest c SET c.worker = :workerToUpdate WHERE c.id = :idToSearch")
                    .setParameter("idToSearch", id)
                    .setParameter("workerToUpdate", worker)
                    .executeUpdate();
//        } catch (Throwable exc) {
//            manager.getTransaction().rollback();
//            throw exc;
//        }
//        manager.getTransaction().commit();
    }
    @Transactional
    public ClientAccountant createClient(String login, String password, String clientName, long clientITN) {
        ClientAccountant accountant = new ClientAccountant();
        accountant.setEncodedPassword(password);
        accountant.setClientName(clientName);
        accountant.setLogin(login);
        accountant.setClientITN(clientITN);
        accountant.setLiability(0);
//        manager.getTransaction().begin();
//        try {
        manager.persist(accountant);
//        } catch (Throwable exc) {
//            manager.getTransaction().rollback();
//            throw exc;
//        }
//        manager.getTransaction().commit();
        return accountant;
    }
    @Transactional
    public CreditWorker createCreditWorker(String login, String password) {
        CreditWorker worker = new CreditWorker();
        worker.setLogin(login);
        worker.setEncodedPassword(password);
//        manager.getTransaction().begin();
//        try {
        manager.persist(worker);
//        } catch (Throwable exc) {
//            manager.getTransaction().rollback();
//            throw exc;
//        }
//        manager.getTransaction().commit();
        return worker;
    }
    @Transactional
    public void makeCreditDecision(ClientCreditRequest request, ClientAccountant accountant) {
        //worker.setDecision(request, worker.getLimit(request) > (request.getSum() + accountant.getLiability()) ? CreditDecision.APPROVE : CreditDecision.DENY);
        CreditDecision decision;
        int id = request.getId();
        if (request.getLimit() > (request.getSum() + accountant.getLiability())) {
            decision = CreditDecision.APPROVE;
        } else {
            decision = CreditDecision.DENY;
        }
//        try {
//            manager.getTransaction().begin();
            manager.createQuery("UPDATE ClientCreditRequest c SET c.decision = :decisionToUpdate WHERE c.id = :idToSearch")
                    .setParameter("idToSearch", id)
                    .setParameter("decisionToUpdate", decision)
                    .executeUpdate();
//        } catch (Throwable exc) {
//            manager.getTransaction().rollback();
//            throw exc;
//        }
//        manager.getTransaction().commit();
        request.setDecision(decision);
        if (decision.equals(CreditDecision.APPROVE)) {
            long presentLiability = accountant.getLiability() + request.getSum();
            int idAcc = accountant.getId();
//            try {
//                manager.getTransaction().begin();
                manager.createQuery("UPDATE ClientAccountant c SET c.liability = :liabilityToUpdate WHERE c.id = :idToSearch")
                        .setParameter("idToSearch", idAcc)
                        .setParameter("liabilityToUpdate", presentLiability)
                        .executeUpdate();
//            } catch (Throwable exc) {
//                manager.getTransaction().rollback();
//                throw exc;
//            }
//            manager.getTransaction().commit();
            accountant.setLiability(presentLiability);
        }

    }
    @Transactional
    public void countLimit(ClientCreditRequest request) {
        //worker.setLimit(request, (int) (request.getCreditType().equals(CreditType.INVESTMENT) ? ((5 * request.getNetAssets() - request.getTotalAssets()) < 2 * request.getProfit() ? (5 * request.getNetAssets() - request.getTotalAssets()) : 2 * request.getProfit()) : (5 * request.getNetAssets() - request.getTotalAssets()) < 2 * request.getProfit() ? (5 * request.getNetAssets() - request.getTotalAssets()) : (request.getRevenue() / 6)));
        int limit;
        int id = request.getId();
        int restriction = 5 * request.getNetAssets() - request.getTotalAssets();
        if (request.getCreditType().equals(CreditType.INVESTMENT)) {
            limit = Math.min(restriction, 2 * request.getProfit());

        } else {
            limit = Math.min(restriction, (int) (request.getRevenue() / 6));
        }
//        try {
//            manager.getTransaction().begin();
            manager.createQuery("UPDATE ClientCreditRequest c SET c.limit = :limitToUpdate WHERE c.id = :idToSearch")
                    .setParameter("idToSearch", id)
                    .setParameter("limitToUpdate", limit)
                    .executeUpdate();
//        } catch (Throwable exc) {
//            manager.getTransaction().rollback();
//            throw exc;
//        }
//        manager.getTransaction().commit();
        request.setLimit(limit);
    }

    public ClientCreditRequest findRequestById(int id) {
        try {
            return manager.createQuery("SELECT r from ClientCreditRequest r WHERE r.id = :idToSearch", ClientCreditRequest.class)
                    .setParameter("idToSearch", id)
                    .getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }

    }

    public ClientAccountant findClientByITN(long clientITN) {
        try {
            return manager.createQuery("SELECT c from ClientAccountant c WHERE c.clientITN = :ITNToSearch", ClientAccountant.class)
                    .setParameter("ITNToSearch", clientITN)
                    .getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }

    }

    public ClientAccountant findClientByLogin(String login) {
        try {
            return manager.createQuery("SELECT c from ClientAccountant c WHERE c.login = :clientLoginToSearch", ClientAccountant.class)
                    .setParameter("clientLoginToSearch", login)
                    .getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }

    }

    public CreditWorker findCreditWorkerByLogin(String login) {
        try {
            return manager.createQuery("SELECT c from CreditWorker c WHERE c.login = :loginToSearch", CreditWorker.class)
                    .setParameter("loginToSearch", login)
                    .getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }

    }

    public List<ClientCreditRequest> findRequestsByCreditWorker(CreditWorker creditWorker) {
        try {
            return manager.createQuery("SELECT r from ClientCreditRequest  r WHERE r.worker = :worker", ClientCreditRequest.class)
                    .setParameter("worker", creditWorker)
                    .getResultList();

        } catch (NoResultException exc) {
            return null;
        }
    }

    public List<ClientCreditRequest> findRequestsByClient(ClientAccountant client) {
        try {
            return manager.createQuery("SELECT r from ClientCreditRequest  r WHERE r.accountant = :accountant", ClientCreditRequest.class)
                    .setParameter("accountant", client)
                    .getResultList();

        } catch (NoResultException exc) {
            return null;
        }
    }

    public List<ClientCreditRequest> findAllRequests() {
        try {
            return manager.createQuery("SELECT r from ClientCreditRequest  r", ClientCreditRequest.class)

                    .getResultList();

        } catch (NoResultException exc) {
            return null;
        }
    }

    public  List<ClientAccountant> findAllClients() {
        try {
            return manager.createQuery("SELECT a from ClientAccountant  a", ClientAccountant.class)

                    .getResultList();

        } catch (NoResultException exc) {
            return null;
        }
    }

    public List<String> findAllWorkerLogins() {
        try {
            return manager.createQuery("SELECT w.login from CreditWorker w", String.class).getResultList();



        } catch (NoResultException exc) {
            return null;
        }
    }

}
