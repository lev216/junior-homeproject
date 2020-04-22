package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.db.CreditRequestDAO;
import project.model.ClientCreditRequest;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

//@WebServlet(urlPatterns = "/workerInterface")
@Controller
public class WorkerInterfaceController {

    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/workerInterface")
    public String getWorkerInterface(HttpSession session, ModelMap model) {
//        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//        CreditRequestDAO requests = new CreditRequestDAO(manager);
//
//        try {
//            HttpSession session = req.getSession(true);
//            ArrayList<ClientCreditRequest> workerRequests = new ArrayList<>(requests.findRequestsByCreditWorker(requests.findCreditWorkerByLogin((String) session.getAttribute("login"))));
//            req.setAttribute("workerRequests", workerRequests);
//        } finally {
//            manager.close();
//        }
//        req.getRequestDispatcher("/pages/workerInterface.jsp").forward(req, resp);
        ArrayList<ClientCreditRequest> workerRequests = new ArrayList<>(requests.findRequestsByCreditWorker(requests.findCreditWorkerByLogin((String) session.getAttribute("login"))));
        model.addAttribute("workerRequests", workerRequests);
        return "workerInterface";
    }

    @PostMapping("/workerInterface")
    public String processWorkerInterface(@RequestParam(required = false) String countLimitButton,
                                         @RequestParam(required = false) String logOutButtonFromWorker,
                                         @RequestParam(required = false) String makeDecisionButton) {

//        if (req.getParameter("countLimitButton") !=  null) {
//            resp.sendRedirect(req.getContextPath() + "/countingLimit");
//        }
//        if (req.getParameter("logOutButtonFromWorker") != null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//
//
//        }
//
//        if (req.getParameter("makeDecisionButton") != null) {
//            resp.sendRedirect(req.getContextPath() + "/makingDecision");
//        }
        if (countLimitButton != null) {
            return "redirect:/countingLimit";
        } else if (makeDecisionButton != null) {
            return "redirect:/makingDecision";
        } else if (logOutButtonFromWorker != null) {
            return "redirect:/login";
        } else {
            throw new IllegalStateException("Something's gone wrong");
        }
    }

}
