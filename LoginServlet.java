// ============================================================
//  File : LoginServlet.java
//  Place: src/LoginServlet.java
//
//  Handles POST login form submission.
//  - Validates username & password
//  - On success: creates an HttpSession, stores user info
//  - On failure: redirects back to login page with error
// ============================================================

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // Simple in-memory credentials (in real apps use a database)
    private static final String[][] USERS = {
        { "admin",  "admin"  },
        { "user1",  "user1"  },
        { "guest",  "guest"  }
    };

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // ── Validate credentials ──────────────────────────────
        if (isValidUser(username, password)) {

            // ── Create / get session ──────────────────────────
            // getSession(true) creates a new session if none exists
            HttpSession session = request.getSession(true);

            // ── Store user data in session ────────────────────
            session.setAttribute("username",  username);
            session.setAttribute("loginTime", new java.util.Date().toString());
            session.setAttribute("role",      username.equals("admin") ? "Administrator" : "User");

            // Session timeout: 10 minutes (600 seconds)
            session.setMaxInactiveInterval(600);

            // ── Redirect to welcome/dashboard page ────────────
            response.sendRedirect("WelcomeServlet");

        } else {
            // Invalid credentials → redirect back with error flag
            response.sendRedirect("login.html?error=1");
        }
    }

    // Check username & password against our simple store
    private boolean isValidUser(String username, String password) {
        if (username == null || password == null) return false;
        for (String[] user : USERS) {
            if (user[0].equals(username.trim()) && user[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
}
