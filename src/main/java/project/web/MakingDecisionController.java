package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.db.CreditRequestDAO;
import project.model.ClientCreditRequest;
import project.model.CreditDecision;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

//@WebServlet(urlPatterns = "/makingDecision")
@Controller
public class MakingDecisionController {
    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/makingDecision")
    public String getMakingDecisionFrom(HttpSession session, ModelMap model) {

        ArrayList<ClientCreditRequest> allWorkerRequests = new ArrayList<>(requests.findRequestsByCreditWorker(requests.findCreditWorkerByLogin((String) session.getAttribute("login"))));
            ArrayList<ClientCreditRequest> duplicate = new ArrayList<>(allWorkerRequests);
            for (ClientCreditRequest request : duplicate) {
                if (request.getDecision() != null) {
                    allWorkerRequests.remove(request);
                }
            }
        model.addAttribute("allWorkerRequests", allWorkerRequests);
        //req.getRequestDispatcher("/pages/makingDecision.jsp").forward(req, resp);
        return "makingDecision";
    }

    @PostMapping("/makingDecision")
    public String processMakingDecisionForm(@RequestParam(required = false) String cancelButton,
                                            @RequestParam(required = false) String makingDecisionButton,
                                            @RequestParam(name = "idWorkerRequests", required = false) Integer idWorkerRequest) {
//        if (req.getParameter("cancelButton") != null) {
//            resp.sendRedirect(req.getContextPath() + "/workerInterface");
//        } else {
//            EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//            CreditRequestDAO requests = new CreditRequestDAO(manager);
//            try {
//                int idWorkerRequest = Integer.parseInt(req.getParameter("idWorkerRequests"));
//                ClientCreditRequest request = requests.findRequestById(idWorkerRequest);
//                if (req.getParameter("makingDecisionButton") != null) {
//                    requests.makeCreditDecision(request, request.getAccountant());
//                    resp.sendRedirect(req.getContextPath() + "/workerInterface");
//                }
//            } finally {
//                manager.close();
//            }
//        }
        if (cancelButton != null) {
            return "redirect:/workerInterface";
        } else if (makingDecisionButton != null) {
            ClientCreditRequest request = requests.findRequestById(idWorkerRequest);
            requests.makeCreditDecision(request, request.getAccountant());
            return "redirect:/workerInterface";
        } else {
            throw new IllegalStateException("Something's gone wrong");
        }
    }
}
