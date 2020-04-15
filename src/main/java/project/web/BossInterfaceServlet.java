package project.web;

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

@WebServlet(urlPatterns = "/bossInterface")
public class BossInterfaceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);
        try {

            ArrayList<ClientCreditRequest> allRequests = new ArrayList<>(requests.findAllRequests());
            req.setAttribute("allRequests", allRequests);
        } finally {
            manager.close();
        }
        req.getRequestDispatcher("/pages/bossInterface.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logOutButtonFromBoss") != null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        if (req.getParameter("assignCreditWorker") != null) {
            resp.sendRedirect(req.getContextPath() + "/assigningWorker");
        }
    }
}
