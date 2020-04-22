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
import project.model.CreditWorker;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet(urlPatterns = "/")
@Controller
public class RegistrationController {

    @Autowired
    private CreditRequestDAO requests;

    ClientAccountant client;
    CreditWorker worker;

    @GetMapping(path = "/")
    public String getRegistrationForm() {
//        req.getRequestDispatcher("/pages/registration.jsp").forward(req, resp);
        return "registration";
    }

    @PostMapping("registration")
    public String processRegistrationForm(HttpSession session,
                         @RequestParam(name = "typeField") String type,
                         @RequestParam(name = "loginField") String login,
                         @RequestParam(name = "passwordField") String password,
                         @RequestParam(name = "clientITNField") Long clientITN,
                         @RequestParam(name = "clientNameField") String clientName,
                         @RequestParam(required = false) String registrationButton,
                         @RequestParam(required = false) String toLoginButton) {

            session.setAttribute("login", login);
            String loginBoss = "boss";
            if (requests.findCreditWorkerByLogin(loginBoss) == null) {
                CreditWorker boss = requests.createCreditWorker(loginBoss, "123");
                session.setAttribute("boss", loginBoss);
            }

            if (registrationButton != null && type.equals("Client")) {
                client = requests.createClient(login, password, clientName, clientITN);
                return "redirect:/clientInterface";

            } else if (registrationButton != null && type.equals("Worker")) {
                worker = requests.createCreditWorker(login, password);
                return "redirect:/workerInterface";

            } else if (toLoginButton != null) {
                return "redirect:/login";
            } else {
                throw new IllegalStateException("Something's gone wrong");
            }



    }
}
