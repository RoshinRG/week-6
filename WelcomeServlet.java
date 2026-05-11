// ============================================================
//  File : WelcomeServlet.java
//  Place: src/WelcomeServlet.java
//
//  Protected page — only accessible when a valid session exists.
//  Reads user info from HttpSession and displays a dashboard.
//  If no session → redirects to login page.
// ============================================================

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ── Check for existing session (false = do NOT create new) ──
        HttpSession session = request.getSession(false);

        // If no session or session doesn't have username → not logged in
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // ── Read values stored in session ──────────────────────────
        String username  = (String) session.getAttribute("username");
        String loginTime = (String) session.getAttribute("loginTime");
        String role      = (String) session.getAttribute("role");
        String sessionId = session.getId();                          // unique session ID
        long   created   = session.getCreationTime();               // ms since epoch
        long   lastAccess= session.getLastAccessedTime();
        int    maxIdle   = session.getMaxInactiveInterval();        // seconds
        boolean isNew    = session.isNew();

        // Format times
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy  HH:mm:ss");
        String createdStr    = sdf.format(new java.util.Date(created));
        String lastAccessStr = sdf.format(new java.util.Date(lastAccess));

        // ── Write HTML response ────────────────────────────────────
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'/>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'/>");
        out.println("  <title>Welcome - Session Dashboard</title>");
        out.println("  <style>");
        out.println("    * { margin:0; padding:0; box-sizing:border-box; }");
        out.println("    body {");
        out.println("      font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;");
        out.println("      background:#eef2f7; min-height:100vh;");
        out.println("      display:flex; flex-direction:column;");
        out.println("      align-items:center; padding:40px 16px;");
        out.println("    }");
        out.println("    .top-bar {");
        out.println("      width:100%; max-width:640px;");
        out.println("      display:flex; justify-content:space-between; align-items:center;");
        out.println("      margin-bottom:24px;");
        out.println("    }");
        out.println("    .top-bar span { font-size:0.9rem; color:#777; }");
        out.println("    .logout-btn {");
        out.println("      padding:8px 20px; background:#c62828; color:#fff;");
        out.println("      border-radius:8px; text-decoration:none;");
        out.println("      font-size:0.88rem; font-weight:700;");
        out.println("      transition:background 0.2s;");
        out.println("    }");
        out.println("    .logout-btn:hover { background:#b71c1c; }");
        out.println("    .card {");
        out.println("      background:#fff; border-radius:14px;");
        out.println("      box-shadow:0 8px 30px rgba(0,0,0,0.12);");
        out.println("      padding:38px 36px; width:100%; max-width:640px;");
        out.println("    }");
        out.println("    .welcome-header { display:flex; align-items:center; gap:14px; margin-bottom:24px; }");
        out.println("    .avatar {");
        out.println("      width:56px; height:56px; border-radius:50%;");
        out.println("      background:#1a237e; color:#fff;");
        out.println("      display:flex; align-items:center; justify-content:center;");
        out.println("      font-size:1.5rem; font-weight:700; flex-shrink:0;");
        out.println("    }");
        out.println("    .welcome-header h2 { color:#1a237e; font-size:1.4rem; }");
        out.println("    .welcome-header p  { color:#888; font-size:0.86rem; margin-top:3px; }");
        out.println("    .role-badge {");
        out.println("      display:inline-block; padding:3px 12px;");
        out.println("      border-radius:20px; font-size:0.74rem; font-weight:700;");
        out.println("      background:#e8eaf6; color:#3f51b5; letter-spacing:0.5px;");
        out.println("    }");
        out.println("    .section-title {");
        out.println("      font-size:0.8rem; text-transform:uppercase;");
        out.println("      letter-spacing:1px; color:#aaa; font-weight:700;");
        out.println("      margin: 20px 0 10px;");
        out.println("    }");
        out.println("    .info-box {");
        out.println("      background:#f8f9ff; border:1.5px solid #e8eaf6;");
        out.println("      border-radius:10px; padding:4px 0;");
        out.println("    }");
        out.println("    .info-row {");
        out.println("      display:flex; justify-content:space-between;");
        out.println("      padding:10px 16px; border-bottom:1px solid #eee;");
        out.println("      font-size:0.9rem;");
        out.println("    }");
        out.println("    .info-row:last-child { border-bottom:none; }");
        out.println("    .field { color:#888; font-weight:600; }");
        out.println("    .value { color:#1a237e; font-weight:700; word-break:break-all; text-align:right; max-width:65%; }");
        out.println("    .new-badge {");
        out.println("      background:#e8f5e9; color:#2e7d32;");
        out.println("      padding:2px 8px; border-radius:4px; font-size:0.75rem;");
        out.println("    }");
        out.println("    .old-badge {");
        out.println("      background:#fff8e1; color:#f57f17;");
        out.println("      padding:2px 8px; border-radius:4px; font-size:0.75rem;");
        out.println("    }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");

        // Top bar
        out.println("<div class='top-bar'>");
        out.println("  <span>&#128274; Session Active</span>");
        out.println("  <a class='logout-btn' href='LogoutServlet'>Logout</a>");
        out.println("</div>");

        out.println("<div class='card'>");

        // Welcome header
        out.println("  <div class='welcome-header'>");
        out.println("    <div class='avatar'>" + escapeHtml(username).substring(0,1).toUpperCase() + "</div>");
        out.println("    <div>");
        out.println("      <h2>Welcome, " + escapeHtml(username) + "!</h2>");
        out.println("      <p>You are successfully logged in &nbsp;<span class='role-badge'>" + escapeHtml(role) + "</span></p>");
        out.println("    </div>");
        out.println("  </div>");

        // User session info
        out.println("  <div class='section-title'>Session Information</div>");
        out.println("  <div class='info-box'>");

        out.println("    <div class='info-row'><span class='field'>Username</span><span class='value'>" + escapeHtml(username) + "</span></div>");
        out.println("    <div class='info-row'><span class='field'>Role</span><span class='value'>" + escapeHtml(role) + "</span></div>");
        out.println("    <div class='info-row'><span class='field'>Login Time</span><span class='value'>" + escapeHtml(loginTime) + "</span></div>");
        out.println("    <div class='info-row'><span class='field'>Session Status</span><span class='value'>"
                + (isNew ? "<span class='new-badge'>NEW</span>" : "<span class='old-badge'>EXISTING</span>") + "</span></div>");

        out.println("  </div>");

        out.println("  <div class='section-title'>HttpSession Details</div>");
        out.println("  <div class='info-box'>");

        out.println("    <div class='info-row'><span class='field'>Session ID</span><span class='value'>" + escapeHtml(sessionId) + "</span></div>");
        out.println("    <div class='info-row'><span class='field'>Created At</span><span class='value'>" + createdStr + "</span></div>");
        out.println("    <div class='info-row'><span class='field'>Last Accessed</span><span class='value'>" + lastAccessStr + "</span></div>");
        out.println("    <div class='info-row'><span class='field'>Max Idle Time</span><span class='value'>" + maxIdle + " seconds</span></div>");

        out.println("  </div>");

        out.println("</div>"); // .card
        out.println("</body></html>");
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                .replace("\"","&quot;").replace("'","&#x27;");
    }
}
