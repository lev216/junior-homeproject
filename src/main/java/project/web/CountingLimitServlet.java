package project.web;

import project.db.CreditRequestDAO;
import project.model.ClientCreditRequest;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/countingLimit")
public class CountingLimitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MakingDecisionServlet.makingListID(req);

        req.getRequestDispatcher("/pages/countingLimit.jsp").forward(req, resp);
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
                if (req.getParameter("countLimitButton") != null) {
                    requests.countLimit(request);
                    //request.setWorker(worker);
                    resp.sendRedirect(req.getContextPath() + "/workerInterface");
                }
            } finally {
                manager.close();
            }
        }
    }
}
