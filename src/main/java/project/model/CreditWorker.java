package project.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class CreditWorker {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String login;

    @OneToMany(mappedBy = "worker")
    private List<ClientCreditRequest> request;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public CreditDecision getDecision(ClientCreditRequest request) {
        return request.decision;
    }

    public void setDecision(ClientCreditRequest request, CreditDecision decision) {
        request.decision = decision;
    }

    public int getLimit(ClientCreditRequest request) {
        return request.limit;
    }

    public void setLimit(ClientCreditRequest request, int limit) {
        request.limit = limit;
    }

}
