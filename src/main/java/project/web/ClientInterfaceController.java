package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.ClientCreditRequest;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

//@WebServlet(urlPatterns = "/clientInterface")
@Controller
public class ClientInterfaceController {
    ClientAccountant accountant;
    ClientCreditRequest request;
    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/clientInterface")
    public String getClientInterface(HttpSession session, ModelMap model) {
//        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//        CreditRequestDAO requests = new CreditRequestDAO(manager);
//        try {
//            HttpSession session = req.getSession(true);
//            String login = (String) session.getAttribute("login");
//            accountant = requests.findClientByLogin(login);
//            ArrayList<ClientCreditRequest> clientCreditRequests = new ArrayList<>(requests.findRequestsByClient(accountant));
//            req.setAttribute("clientRequests", clientCreditRequests);
//        } finally {
//            manager.close();
//        }
//
//        req.getRequestDispatcher("/pages/clientInterface.jsp").forward(req, resp);
        String login = (String) session.getAttribute("login");
        accountant = requests.findClientByLogin(login);
        ArrayList<ClientCreditRequest> clientCreditRequests = new ArrayList<>(requests.findRequestsByClient(accountant));
        model.addAttribute("clientRequests", clientCreditRequests);
        return "clientInterface";
    }

    @PostMapping("/clientInterface")
    public String processClientInterface(@RequestParam(required = false) String logOutButtonFromClient,
                                         @RequestParam(required = false) String newCreditRequestButton) {
//        if (req.getParameter("logOutButtonFromClient") != null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//        }
//        if (req.getParameter("newCreditRequestButton") != null) {
//            resp.sendRedirect(req.getContextPath() + "/request");
//        }
        if (logOutButtonFromClient != null) {
            return "redirect:/login";
        } else if (newCreditRequestButton != null) {
            return "redirect:/request";
        } else {
            throw new IllegalStateException("Something's gone wrong");
        }
    }
}
