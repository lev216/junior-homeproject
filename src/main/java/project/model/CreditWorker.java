package project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table
public class CreditWorker {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    @Size(min = 4, max = 50, message = "Login should be at least 4 characters length")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*",
    message = "Only letters, digits, underscore, minus sign and dots are allowed in login")
    private String login;

    @OneToMany(mappedBy = "worker")
    private List<ClientCreditRequest> request;

    @Column(nullable = false)
    @JsonIgnore
    @Size(min = 3, max = 70, message = "Password should be at least 3 characters length")
    private String encodedPassword;

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String password) {
        this.encodedPassword = password;
    }


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

    public int getId() {
        return id;
    }
}
