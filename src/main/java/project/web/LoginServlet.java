package project.web;

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

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    ClientAccountant client;
    CreditWorker worker;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);
        try {
            String login = req.getParameter("loginField");
            String password = req.getParameter("passwordField");


            client = requests.findClientByLogin(login);
            worker = requests.findCreditWorkerByLogin(login);
            HttpSession session = req.getSession(true);
            session.setAttribute("auth", login);
            if (client != null && password.equals(client.getPassword())) {
                resp.sendRedirect(req.getContextPath() + "/clientInterface.jsp");
            } else if (worker != null && password.equals(worker.getPassword())) {
                resp.sendRedirect(req.getContextPath() + "/workerInterface.jsp");
            } else if (req.getParameter("registrationButton") != null) {
                resp.sendRedirect(req.getContextPath() + "/registration.jsp");

            } else {
                resp.sendError(400, "Invalid login or password");
            }
        } catch (Exception exc) {
            resp.sendError(400, "You've just fucked up");
        } finally {
            manager.close();
        }

    }
}
