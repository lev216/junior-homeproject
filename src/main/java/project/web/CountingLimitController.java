package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.db.CreditRequestDAO;
import project.model.ClientCreditRequest;
import project.model.CreditDecision;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

//@WebServlet(urlPatterns = "/countingLimit")
@Controller
public class CountingLimitController {
    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/countingLimit")
    public String getCountingLimitForm(HttpSession session, ModelMap model) {
        ArrayList<ClientCreditRequest> allWorkerRequests = new ArrayList<>(requests.findRequestsByCreditWorker(requests.findCreditWorkerByLogin((String) session.getAttribute("login"))));
            ArrayList<ClientCreditRequest> duplicate = new ArrayList<>(allWorkerRequests);
            for (ClientCreditRequest request : duplicate) {
                if (request.getDecision() != null) {
                    allWorkerRequests.remove(request);
                }
            }
        model.addAttribute("allWorkerRequests", allWorkerRequests);
        //req.getRequestDispatcher("/pages/makingDecision.jsp").forward(req, resp);
//        req.getRequestDispatcher("/pages/countingLimit.jsp").forward(req, resp);

        return "countingLimit";
    }

    @PostMapping("/countingLimit")
    public String processCountingLimitForm(ModelMap model,
                                           @RequestParam(required = false) String cancelButton,
                                           @RequestParam(name = "idWorkerRequests", required = false) Integer idWorkerRequest,
                                           @RequestParam(required = false) String countLimitButton) {
//        if (req.getParameter("cancelButton") != null) {
//            resp.sendRedirect(req.getContextPath() + "/workerInterface");
//        } else {
//            EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//            CreditRequestDAO requests = new CreditRequestDAO(manager);
//            try {
//                int idWorkerRequest = Integer.parseInt(req.getParameter("idWorkerRequests"));
//                //String workerLogin = (String) req.getAttribute("login");
//                ClientCreditRequest request = requests.findRequestById(idWorkerRequest);
//                //CreditWorker worker = requests.findCreditWorkerByLogin(workerLogin);
//                if (req.getParameter("countLimitButton") != null) {
//                    requests.countLimit(request);
//                    //request.setWorker(worker);
//                    resp.sendRedirect(req.getContextPath() + "/workerInterface");
//                }
//            } finally {
//                manager.close();
//            }
//        }
        if (cancelButton != null) {
            return "redirect:/workerInterface";
        } else if (countLimitButton != null) {
            ClientCreditRequest request = requests.findRequestById(idWorkerRequest);
            requests.countLimit(request);
            return "redirect:/workerInterface";
        } else {
            throw new IllegalStateException("Something's gone wrong");
        }


    }
}
