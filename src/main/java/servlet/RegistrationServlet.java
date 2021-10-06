package servlet;

import model.UserProfile;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sessionId = req.getSession().getId();

        try {
            accountService.getUserBySessionId(sessionId);
            resp.sendRedirect("/files");

        } catch (RuntimeException e) {
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordRetry = request.getParameter("passwordRetry");
        String email = request.getParameter("email");

        String error = "";

        try {
            UserProfile userProfile = new UserProfile(login, password, email);
            accountService.registerNewUser(userProfile);
            accountService.addSession(request.getSession().getId(), userProfile);
            response.sendRedirect("/files");
            return;

        } catch (Exception exception) {
            error = exception.getMessage();
        }

        if (login.equals("")) {
            error = "Login not valid";
        } else if (password.equals("")) {
            error = "Password not valid";
        } else if (!passwordRetry.equals(password)) {
            error = "Password retry not same with password";
        } else if (email.equals("")) {
            error = "Email not valid";
        }

        request.setAttribute("login", login);
        request.setAttribute("password", password);
        request.setAttribute("passwordRetry", passwordRetry);
        request.setAttribute("email", email);
        request.setAttribute("error", error);
        request.getRequestDispatcher("registration.jsp").forward(request, response);
    }
}
