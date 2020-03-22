package project.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class ClientAccountant {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false, unique = true)
    private long clientITN;

    @OneToMany(mappedBy = "accountant")
    private List<ClientCreditRequest> clientCreditRequest;

    @Column
    private long liability;

    public long getLiability() {
        return liability;
    }

    public void setLiability(long liability) {
        this.liability = liability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public long getClientITN() {
        return clientITN;
    }

    public void setClientITN(long clientITN) {
        this.clientITN = clientITN;
    }

}
