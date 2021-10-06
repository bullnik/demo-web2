package servlet;

import model.UserProfile;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            accountService.getUserBySessionId(req.getSession().getId());
            resp.sendRedirect("/files");

        } catch (RuntimeException e) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            UserProfile userProfile = accountService.getUserByLoginAndPassword(login, password);
            accountService.addSession(request.getSession().getId(), userProfile);
            response.sendRedirect("/files");

        } catch (Exception exception) {
            request.setAttribute("login", login);
            request.setAttribute("password", password);
            request.setAttribute("error", exception.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
