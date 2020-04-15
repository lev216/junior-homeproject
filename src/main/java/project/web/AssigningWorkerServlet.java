package project.web;

import project.db.CreditRequestDAO;
import project.model.ClientCreditRequest;
import project.model.CreditWorker;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/assigningWorker")
public class AssigningWorkerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        CreditRequestDAO requests = new CreditRequestDAO(manager);
        try {

            ArrayList<String> allWorkerLogins = new ArrayList<>(requests.findAllWorkerLogins());
            req.setAttribute("allWorkerLogins", allWorkerLogins);
        } finally {
            manager.close();
        }

        req.getRequestDispatcher("/pages/assigningWorker.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("cancelButton") != null) {
            resp.sendRedirect(req.getContextPath() + "/bossInterface");
        } else {
            EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
            CreditRequestDAO requests = new CreditRequestDAO(manager);
            try {
                int idRequest = Integer.parseInt(req.getParameter("requestIDField"));
                String workerLogin = req.getParameter("workerLogins");
                //ClientCreditRequest request = requests.findRequestById(idRequest);
                CreditWorker worker = requests.findCreditWorkerByLogin(workerLogin);
                if (req.getParameter("assignWorkerButton") != null) {
                    requests.assignCreditWorkerToRequest(idRequest, worker);
                    //request.setWorker(worker);
                    resp.sendRedirect(req.getContextPath() + "/bossInterface");
                }
            } finally {
                manager.close();
            }
        }
    }
}
