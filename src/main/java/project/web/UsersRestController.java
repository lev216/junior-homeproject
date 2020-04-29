package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.ClientCreditRequest;

import java.util.List;

@RestController
public class UsersRestController {
    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/api/clients")
    public List<ClientAccountant> findAllClientAccountants() {
        return requests.findAllClients();
    }

    @GetMapping("/api/requests")
    public List<ClientCreditRequest> findAllCreditRequests() {
        return requests.findAllRequests();
    }
}
