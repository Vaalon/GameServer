package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.executor.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by talosar on 1/18/16.
 */
public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile userProfile = null;
        try {
            userProfile = accountService.getUserByLogin(login);
        } catch (DBException e) {
            //TODO: Заглушка. Как-нибудь обработать
        }

        if (userProfile != null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("User with this login already exists");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            accountService.addNewUser(new UserProfile(login, password, "temp@gmail.com"));
        } catch (DBException e) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(e.toString());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
