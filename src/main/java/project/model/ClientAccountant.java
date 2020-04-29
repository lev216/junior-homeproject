package project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table
public class ClientAccountant {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    @Size(min = 4, max = 50, message = "Login should be at least 4 characters length")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*",
            message = "Only letters, digits, underscore, minus sign and dots are allowed in login")
    private String login;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false, unique = true)
    @Positive
    //@Size(min = 10)
    private long clientITN;

    @OneToMany(mappedBy = "accountant")
    private List<ClientCreditRequest> clientCreditRequest;

    @Column
    private long liability;

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
