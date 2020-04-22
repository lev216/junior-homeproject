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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

//@WebServlet(urlPatterns = "/bossInterface")
@Controller
public class BossInterfaceController {
    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/bossInterface")
    public String getBossInterface(ModelMap model) {
//        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//        CreditRequestDAO requests = new CreditRequestDAO(manager);
//        try {
//
//            ArrayList<ClientCreditRequest> allRequests = new ArrayList<>(requests.findAllRequests());
//            req.setAttribute("allRequests", allRequests);
//        } finally {
//            manager.close();
//        }
//        req.getRequestDispatcher("/pages/bossInterface.jsp").forward(req, resp);
        ArrayList<ClientCreditRequest> allRequests = new ArrayList<>(requests.findAllRequests());
        model.addAttribute("allRequests", allRequests);
        return "bossInterface";
    }

    @PostMapping("/bossInterface")
    public String processBossInterface(@RequestParam(required = false) String logOutButtonFromBoss,
                                       @RequestParam(required = false) String assignCreditWorker) {
//        if (req.getParameter("logOutButtonFromBoss") != null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//        }
//        if (req.getParameter("assignCreditWorker") != null) {
//            resp.sendRedirect(req.getContextPath() + "/assigningWorker");
//        }

        if (logOutButtonFromBoss != null) {
            return "redirect:/login";
        } else if (assignCreditWorker != null) {
            return "redirect:/assigningWorker";
        } else {
            throw new IllegalStateException("Something's gone wrong");
        }
    }
}
