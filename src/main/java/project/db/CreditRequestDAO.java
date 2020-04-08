package project.db;

import project.model.*;

import javax.persistence.*;
import java.util.*;

public class CreditRequestDAO {
    private EntityManager manager;

    public CreditRequestDAO(EntityManager manager) {
        Objects.requireNonNull(manager, "EntityManager shouldn't be null");
        this.manager = manager;
    }

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
        manager.getTransaction().begin();
        try {
            manager.persist(request);
        } catch (Throwable exc) {
            manager.getTransaction().rollback();
            throw exc;
        }
        manager.getTransaction().commit();
        return request;
    }

    public void assignCreditWorkerToRequest(ClientCreditRequest request, CreditWorker worker) {
        request.setWorker(worker);

    }

    public ClientAccountant createClient(String login, String password, String clientName, long clientITN) {
        ClientAccountant accountant = new ClientAccountant();
        accountant.setPassword(password);
        accountant.setClientName(clientName);
        accountant.setLogin(login);
        accountant.setClientITN(clientITN);
        accountant.setLiability(0);
        manager.getTransaction().begin();
        try {
            manager.persist(accountant);
        } catch (Throwable exc) {
            manager.getTransaction().rollback();
            throw exc;
        }
        manager.getTransaction().commit();
        return accountant;
    }

    public CreditWorker createCreditWorker(String login, String password) {
        CreditWorker worker = new CreditWorker();
        worker.setLogin(login);
        worker.setPassword(password);
        manager.getTransaction().begin();
        try {
            manager.persist(worker);
        } catch (Throwable exc) {
            manager.getTransaction().rollback();
            throw exc;
        }
        manager.getTransaction().commit();
        return worker;
    }

    public void makeCreditDecision(CreditWorker worker, ClientCreditRequest request, ClientAccountant accountant) {
        worker.setDecision(request, worker.getLimit(request) > (request.getSum() + accountant.getLiability()) ? CreditDecision.APPROVE : CreditDecision.DENY);

    }

    public void countLimit(CreditWorker worker, ClientCreditRequest request, ClientAccountant accountant) {
        worker.setLimit(request, (int) (request.getCreditType().equals(CreditType.INVESTMENT) ? ((5 * request.getNetAssets() - request.getTotalAssets()) < 2 * request.getProfit() ? (5 * request.getNetAssets() - request.getTotalAssets()) : 2 * request.getProfit()) : (5 * request.getNetAssets() - request.getTotalAssets()) < 2 * request.getProfit() ? (5 * request.getNetAssets() - request.getTotalAssets()) : (request.getRevenue() / 6)));

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

}
