package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

//@WebServlet(urlPatterns = "/assigningWorker")
@Controller
public class AssigningWorkerController {

    @Autowired
    private CreditRequestDAO requests;

    @GetMapping("/assigningWorker")
    public String getAssigningWorkerForm(ModelMap map) {
//        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
//        CreditRequestDAO requests = new CreditRequestDAO(manager);
//        try {
//
//            ArrayList<String> allWorkerLogins = new ArrayList<>(requests.findAllWorkerLogins());
//            req.setAttribute("allWorkerLogins", allWorkerLogins);
//        } finally {
//            manager.close();
//        }
//
//        req.getRequestDispatcher("/pages/assigningWorker.jsp").forward(req, resp);
        ArrayList<String> allWorkerLogins = new ArrayList<>(requests.findAllWorkerLogins());
        map.addAttribute("allWorkerLogins", allWorkerLogins);
        return "assigningWorker";
    }

    @PostMapping("/assigningWorker")
    public String processAssigningWorkerForm(@RequestParam(required = false) String cancelButton,
                                             @RequestParam(name = "requestIDField") Integer idRequest,
                                             @RequestParam(name = "workerLogins") String workerLogin,
                                             @RequestParam(required = false) String assignWorkerButton) {
//        if (req.getParameter("cancelButton") != null) {
////            resp.sendRedirect(req.getContextPath() + "/bossInterface");
////        } else {
////            EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
////            CreditRequestDAO requests = new CreditRequestDAO(manager);
////            try {
////                int idRequest = Integer.parseInt(req.getParameter("requestIDField"));
////                String workerLogin = req.getParameter("workerLogins");
////                //ClientCreditRequest request = requests.findRequestById(idRequest);
////                CreditWorker worker = requests.findCreditWorkerByLogin(workerLogin);
////                if (req.getParameter("assignWorkerButton") != null) {
////                    requests.assignCreditWorkerToRequest(idRequest, worker);
////                    //request.setWorker(worker);
////                    resp.sendRedirect(req.getContextPath() + "/bossInterface");
////                }
////            } finally {
////                manager.close();
////            }
////        }
        if (cancelButton != null) {
            return "redirect:/bossInterface";
        } else if (assignWorkerButton != null) {
            CreditWorker worker = requests.findCreditWorkerByLogin(workerLogin);

            requests.assignCreditWorkerToRequest(idRequest, worker);
            return "redirect:/bossInterface";

        } else {
            throw new IllegalStateException("Something's gone wrong");
        }

    }
}
