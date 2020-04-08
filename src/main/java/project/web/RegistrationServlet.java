package project.web;

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

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    ClientAccountant client;
    CreditWorker worker;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ServletContext context = getServletContext();
        session.setAttribute("login", "user0");
        context.setAttribute("login", "user1");
        req.setAttribute("login", "user2");
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);
        String type = req.getParameter("typeField");
//        try {


            String login = req.getParameter("loginField");
            String password = req.getParameter("passwordField");
            //type = req.getParameter("typeField");
            HttpSession session = req.getSession(true);
            session.setAttribute("auth", login);
            long clientITN;
            String clientName;


            if (type.equals("Client")) {
                try {
                    clientITN = Long.parseLong(req.getParameter("clientITNField"));
                    clientName = req.getParameter("clientNameField");
                    client = requests.createClient(login, password, clientName, clientITN);
                } catch (NumberFormatException exc) {

                }


            } else if (type.equals("Worker"))  {
                worker = requests.createCreditWorker(login, password);
            }



            if (req.getParameter("registrationButton") != null && type.equals("Client")) {
                resp.sendRedirect(req.getContextPath() + "/clientInterface.jsp");
            } else if (req.getParameter("registrationButton") != null && type.equals("Worker")) {
                resp.sendRedirect(req.getContextPath() + "/workerInterface.jsp");
            } else if (req.getParameter("toLoginButton") != null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            } else {
                resp.sendError(400, "Something's wrong");
            }
//        } catch (Exception exc) {
//            resp.sendError(400, "You've just fucked up");
//        } finally {
//            manager.close();
//        }

    }
}
