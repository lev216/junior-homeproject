package project.web;

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

@WebServlet(urlPatterns = "/workerInterface")
public class WorkerInterfaceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);

        try {
            HttpSession session = req.getSession(true);
            ArrayList<ClientCreditRequest> workerRequests = new ArrayList<>(requests.findRequestsByCreditWorker(requests.findCreditWorkerByLogin((String) session.getAttribute("login"))));
            req.setAttribute("workerRequests", workerRequests);
        } finally {
            manager.close();
        }
        req.getRequestDispatcher("/pages/workerInterface.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("countLimitButton") !=  null) {
            resp.sendRedirect(req.getContextPath() + "/countingLimit");
        }
        if (req.getParameter("logOutButtonFromWorker") != null) {
            resp.sendRedirect(req.getContextPath() + "/login");


        }

        if (req.getParameter("makeDecisionButton") != null) {
            resp.sendRedirect(req.getContextPath() + "/makingDecision");
        }
    }

}
