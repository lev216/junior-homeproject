package project.web;


import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.ClientCreditRequest;
import project.model.CreditType;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/request")
public class CreditRequestCreationServlet extends HttpServlet {
    ClientAccountant accountant;
    ClientCreditRequest request;
    CreditType creditType;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/creditRequestCreation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);

        try {
            if (req.getParameter("cancelClientButton") != null) {
                resp.sendRedirect(req.getContextPath() + "/clientInterface");
            } else {
                HttpSession session = req.getSession(true);
                String typeCredit = req.getParameter("typeCreditField");
                long sum = Long.parseLong(req.getParameter("sumClientField"));
                int term = Integer.parseInt(req.getParameter("termClientField"));
                long revenue = Long.parseLong(req.getParameter("revenueClientField"));
                int profit = Integer.parseInt(req.getParameter("profitClientField"));
                int netAssets = Integer.parseInt(req.getParameter("netAssetsClientField"));
                int totalAssets = Integer.parseInt(req.getParameter("totalAssetsClientField"));
                if (typeCredit.equals("Working")) {
                    creditType = CreditType.WORKING;
                } else {
                    creditType = CreditType.INVESTMENT;
                }
                String login = (String) session.getAttribute("login");
                accountant = requests.findClientByLogin(login);
                if (accountant != null) {
                    request = requests.createCreditRequest(accountant, creditType, sum, term, revenue, profit, netAssets, totalAssets);
                } else {
                    resp.sendError(400, "You've just fucked up");
                }
                if (req.getParameter("sendRequestButton") != null) {
                    resp.sendRedirect(req.getContextPath() + "/clientInterface");
                }
            }

        //} catch (Exception exc) {
        //    resp.sendError(400, "You've just fucked up");
        } finally {
            manager.close();
        }
    }
}
