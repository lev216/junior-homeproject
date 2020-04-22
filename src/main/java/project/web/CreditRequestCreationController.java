package project.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.ClientCreditRequest;
import project.model.CreditType;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(urlPatterns = "/request")
@Controller
public class CreditRequestCreationController {
    ClientAccountant accountant;
    ClientCreditRequest request;
    CreditType creditType;
    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/request")
    public String getCreditRequestCreationForm() {
        //req.getRequestDispatcher("/pages/creditRequestCreation.jsp").forward(req, resp);
        return "creditRequestCreation";
    }

    @PostMapping("/request")
    public String processCreditRequestCreationForm(HttpSession session,
                                                   @RequestParam(name = "typeCreditField") String typeCredit,
                                                   @RequestParam(name = "sumClientField") Long sum,
                                                   @RequestParam(name = "termClientField") Integer term,
                                                   @RequestParam(name = "revenueClientField") Long revenue,
                                                   @RequestParam(name = "profitClientField") Integer profit,
                                                   @RequestParam(name = "netAssetsClientField") Integer netAssets,
                                                   @RequestParam(name = "totalAssetsClientField") Integer totalAssets,
                                                   @RequestParam(required = false) String cancelClientButton,
                                                   @RequestParam(required = false) String sendRequestButton) {
//        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//        CreditRequestDAO requests = new CreditRequestDAO(manager);
//
//        try {
//            if (req.getParameter("cancelClientButton") != null) {
//                resp.sendRedirect(req.getContextPath() + "/clientInterface");
//            } else {
//                HttpSession session = req.getSession(true);
//                String typeCredit = req.getParameter("typeCreditField");
//                long sum = Long.parseLong(req.getParameter("sumClientField"));
//                int term = Integer.parseInt(req.getParameter("termClientField"));
//                long revenue = Long.parseLong(req.getParameter("revenueClientField"));
//                int profit = Integer.parseInt(req.getParameter("profitClientField"));
//                int netAssets = Integer.parseInt(req.getParameter("netAssetsClientField"));
//                int totalAssets = Integer.parseInt(req.getParameter("totalAssetsClientField"));
//                if (typeCredit.equals("Working")) {
//                    creditType = CreditType.WORKING;
//                } else {
//                    creditType = CreditType.INVESTMENT;
//                }
//                String login = (String) session.getAttribute("login");
//                accountant = requests.findClientByLogin(login);
//                if (accountant != null) {
//                    request = requests.createCreditRequest(accountant, creditType, sum, term, revenue, profit, netAssets, totalAssets);
//                } else {
//                    resp.sendError(400, "You've just fucked up");
//                }
//                if (req.getParameter("sendRequestButton") != null) {
//                    resp.sendRedirect(req.getContextPath() + "/clientInterface");
//                }
//            }
//        } finally {
//            manager.close();
//        }
        if (cancelClientButton != null) {
            return "redirect:/clientInterface";
        } else if (sendRequestButton != null) {
            creditType = typeCredit.equals("Working") ? CreditType.WORKING : CreditType.INVESTMENT;
            String login = (String) session.getAttribute("login");
            accountant = requests.findClientByLogin(login);
            if (accountant != null) {
                request = requests.createCreditRequest(accountant, creditType, sum, term, revenue, profit, netAssets, totalAssets);
            }
            return "redirect:/clientInterface";


        } else {
            throw new IllegalStateException("Something's gone wrong");
        }
    }
}
