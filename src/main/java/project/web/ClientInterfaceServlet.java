package project.web;

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

@WebServlet(urlPatterns = "/clientInterface")
public class ClientInterfaceServlet extends HttpServlet {
    ClientAccountant accountant;
    ClientCreditRequest request;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);
        try {
            HttpSession session = req.getSession(true);
            String login = (String) session.getAttribute("login");
            accountant = requests.findClientByLogin(login);
            ArrayList<ClientCreditRequest> clientCreditRequests = new ArrayList<>(requests.findRequestsByClient(accountant));
            req.setAttribute("clientRequests", clientCreditRequests);
        } finally {
            manager.close();
        }

        req.getRequestDispatcher("/pages/clientInterface.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logOutButtonFromClient") != null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        if (req.getParameter("newCreditRequestButton") != null) {
            resp.sendRedirect(req.getContextPath() + "/request");
        }
    }
}
