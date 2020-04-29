package project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table
public class ClientCreditRequest {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ClientAccountant accountant;

    @ManyToOne(fetch = FetchType.EAGER)
    private CreditWorker worker;

    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    public ClientAccountant getAccountant() {
        return accountant;
    }

    public void setAccountant(ClientAccountant accountant) {
        this.accountant = accountant;
    }

    public CreditWorker getWorker() {
        return worker;
    }

    public void setWorker(CreditWorker worker) {
        this.worker = worker;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    @Column
    @Positive
    @NotNull

    private long sum;

    @Column
    @Positive
    @NotNull
    private int term;

    @Column
    @Positive
    @NotNull
    private long revenue;

    @Column
    @NotNull
    private int profit;

    @Column
    @NotNull
    private int netAssets;

    @Column
    @Positive
    @NotNull
    private int totalAssets;

    @Column(name = "maxLimit")

    public int limit;

    @Enumerated(EnumType.STRING)
    public CreditDecision decision;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getNetAssets() {
        return netAssets;
    }

    public void setNetAssets(int netAssets) {
        this.netAssets = netAssets;
    }

    public int getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(int totalAssets) {
        this.totalAssets = totalAssets;
    }

    public CreditDecision getDecision() {
        return decision;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setDecision(CreditDecision decision) {
        this.decision = decision;
    }
}
