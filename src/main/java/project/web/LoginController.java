package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.CreditWorker;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(urlPatterns = "/login")
@Controller
public class LoginController {
    ClientAccountant client;
    CreditWorker worker;

    @Autowired
    private CreditRequestDAO requests;

    @GetMapping(path = "/login")
    public String getLoginForm() {
        //req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
        return "login";
    }

    @PostMapping(path = "/login")
    public String processLoginForm(HttpSession session,
                                   @RequestParam("loginField") String login,
                                   @RequestParam("passwordField") String password,
                                   @RequestParam(required = false) String loginButton,
                                   @RequestParam(required = false) String registrationButton) {
//        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//        CreditRequestDAO requests = new CreditRequestDAO(manager);
//        try {
//            String login = req.getParameter("loginField");
//            String password = req.getParameter("passwordField");
//            if (login.equals("boss") && password.equals("123")) {
//                resp.sendRedirect(req.getContextPath() + "/bossInterface");
//            } else {
//
//                client = requests.findClientByLogin(login);
//                worker = requests.findCreditWorkerByLogin(login);
//                HttpSession session = req.getSession(true);
//                session.setAttribute("login", login);
//
//                if (client != null && password.equals(client.getPassword())) {
//                    resp.sendRedirect(req.getContextPath() + "/clientInterface");
//                } else if (worker != null && password.equals(worker.getPassword())) {
//                    resp.sendRedirect(req.getContextPath() + "/workerInterface");
//                } else if (req.getParameter("registrationButton") != null) {
//                    resp.sendRedirect(req.getContextPath() + "/registration");
//
//                } else {
//                    resp.sendError(400, "Invalid login or password");
//                }
//            }
//        } catch (Exception exc) {
//            resp.sendError(400, "You've just fucked up");
//        } finally {
//            manager.close();
//        }
        if (login.equals("boss") && password.equals("123")) {
            session.setAttribute("boss", login);
            return "redirect:/bossInterface";
        } else {
            client = requests.findClientByLogin(login);
            worker = requests.findCreditWorkerByLogin(login);
            session.setAttribute("login", login);
            if (client != null && password.equals(client.getPassword()) && loginButton != null) {
                return "redirect:/clientInterface";
            } else if (worker != null && password.equals(worker.getPassword()) && loginButton != null) {
                return "redirect:/workerInterface";
            } else if (registrationButton != null) {
                return "redirect:/";
            } else {
                throw new IllegalStateException("Invalid login or password");
            }
        }
    }
}
