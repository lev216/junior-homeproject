package project.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/workerInterface")
public class WorkerInterfaceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/workerInterface.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String limit = "limit";
        String decision = "decision";
        if (req.getParameter("countLimitButton") !=  null) {
            //resp.setHeader("limit", "100");
            //req.setAttribute("limit", "100");
            req.getSession().setAttribute("limit", limit);
            resp.sendRedirect(req.getContextPath());
            //req.getParameter("limit").replace("0", "100");
            //req.getServletContext().getRequestDispatcher("/workerInterface.jsp").forward(req, resp);
            //doGet(req, resp);
        }
        if (req.getParameter("logOutButton") != null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }

        if (req.getParameter("makeDecision") != null) {
            req.setAttribute("decision", decision);
            req.getServletContext().getRequestDispatcher("/workerInterface.jsp").forward(req, resp);
            doGet(req, resp);
        }
    }

}
