package project.web;

import project.db.CreditRequestDAO;
import project.model.ClientCreditRequest;
import project.model.CreditDecision;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/makingDecision")
public class MakingDecisionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        makingListID(req);

        req.getRequestDispatcher("/pages/makingDecision.jsp").forward(req, resp);
    }

    static void makingListID(HttpServletRequest req) {
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);
        try {
            HttpSession session = req.getSession(true);
            ArrayList<ClientCreditRequest> allWorkerRequests = new ArrayList<>(requests.findRequestsByCreditWorker(requests.findCreditWorkerByLogin((String) session.getAttribute("login"))));

//            ArrayList<ClientCreditRequest> duplicate = new ArrayList<>(allWorkerRequests);
//            for (ClientCreditRequest request : duplicate) {
//                if (request.getDecision().equals(CreditDecision.APPROVE) || request.getDecision().equals(CreditDecision.DENY)) {
//                    allWorkerRequests.remove(request);
//                }
//            }

            req.setAttribute("allWorkerRequests", allWorkerRequests);
        } finally {
            manager.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("cancelButton") != null) {
            resp.sendRedirect(req.getContextPath() + "/workerInterface");
        } else {
            EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
            CreditRequestDAO requests = new CreditRequestDAO(manager);
            try {
                int idWorkerRequest = Integer.parseInt(req.getParameter("idWorkerRequests"));
                //String workerLogin = (String) req.getAttribute("login");
                ClientCreditRequest request = requests.findRequestById(idWorkerRequest);
                //CreditWorker worker = requests.findCreditWorkerByLogin(workerLogin);
                if (req.getParameter("makingDecisionButton") != null) {
                    requests.makeCreditDecision(request, request.getAccountant());
                    //request.setWorker(worker);
                    resp.sendRedirect(req.getContextPath() + "/workerInterface");
                }
            } finally {
                manager.close();
            }
        }
    }
}
