// ============================================================
//  File : LogoutServlet.java
//  Place: src/LogoutServlet.java
//
//  Invalidates the current HttpSession (clears all stored data)
//  and redirects the user back to the login page.
// ============================================================

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ── Get existing session WITHOUT creating a new one ──
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Remove specific attributes (optional — invalidate() removes all)
            session.removeAttribute("username");
            session.removeAttribute("loginTime");
            session.removeAttribute("role");

            // ── Invalidate the entire session ──
            // This destroys the session and all its data on the server.
            session.invalidate();
        }

        // ── Redirect to login page after logout ──
        response.sendRedirect("login.html?logout=1");
    }
}
