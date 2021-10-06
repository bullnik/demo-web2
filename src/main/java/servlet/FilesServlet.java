package servlet;

import model.UserProfile;
import service.AccountService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FilesServlet extends HttpServlet {

    AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String userName = "";
        try {
            UserProfile userProfile = accountService.getUserBySessionId(req.getSession().getId());
            userName = userProfile.getLogin();
        } catch (RuntimeException exception) {
            resp.sendRedirect("/login");
            return;
        }

        String homeFolder = "C:\\Users\\" + userName;

        Map<String, String[]> params = req.getParameterMap();
        String path = "";
        try {
            path = params.get("path")[0].replace('/', '\\');
        } catch (Exception ignored) { }

        if (!path.startsWith(homeFolder)) {
            System.out.println("hf: " + homeFolder);
            System.out.println("path: " + path);
            path = homeFolder;
        }

        String pathAdv = path.substring(0, path.lastIndexOf('\\'));

        try {
            File[] files = new File(path).listFiles();
            req.setAttribute("files", files);
        } catch (Exception ignored) { }

        req.setAttribute("name", userName);
        req.setAttribute("date", new Date());
        req.setAttribute("path", path);
        req.setAttribute("pathAdv", pathAdv);
        req.getRequestDispatcher("listFiles.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        try {
            accountService.deleteSession(request.getSession().getId());
        } catch (RuntimeException ignored) { }
        response.sendRedirect("/login");
    }
}