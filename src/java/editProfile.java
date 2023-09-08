/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 91951
 */
public class editProfile extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt1, pstmt2 = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql = "select * from participents where pusername=?";

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");

            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            pstmt1 = con.prepareStatement(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            IOException, ServletException {
        init();
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setContentType("text/html");

        session = request.getSession(false);
//        if(session.)
        if (session != null) {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Event Page</title>");
            out.println("<link rel=\"stylesheet\" href=\"style.css\">");
            out.println("<link rel=\"stylesheet\" href=\"RegisterEvent.css\">");
            out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<header>\n"
                    + "<h1>Welcome To Event Portal</h1>\n"
                    + "<nav>\n"
                    + "<a href=\"LogoutServlet\">Logout</a>\n"
                    + "</nav>\n"
                    + "</header>");
            out.println("<body>");

            try {
                String pusername = (String) session.getAttribute("pusername");
                pstmt1.setString(1, pusername);
                rs = pstmt1.executeQuery();
                if (rs.next()) {
                    out.println("<div class=\"container\">\n"
                            + "            <form method=\"post\" action=\"editProfileDone\">\n"
                            + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                            + "                    <label for=\"pusername\">Username:</label>\n"
                            + "                    <input type=\"text\" value=\"" + pusername + "\" id=\"pusername\" name=\"pusername\" placeholder=\"Enter username\" readonly>\n"
                            + "                </div>\n"
                            + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                            + "                    <label for=\"pname\">New Name:</label>\n"
                            + "                    <input type=\"text\" value=\"" + rs.getString("pname") + "\" id=\"pname\" name=\"pname\" placeholder=\"Enter name\">\n"
                            + "                </div>\n"
                            + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                            + "                    <label for=\"ppassword\">New Password:</label>\n"
                            + "                    <input type=\"text\" value=\"" + rs.getString("ppassword") + "\" id=\"ppassword\" name=\"ppassword\" placeholder=\"Enter New Password:\">\n"
                            + "                </div>\n"
                            + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                            + "                    <label for=\"pemail\">New Email:</label>\n"
                            + "                    <input type=\"text\" value=\"" + rs.getString("pemail") + "\" id=\"pemail\" name=\"pemail\" placeholder=\"Enter Email\">\n"
                            + "                </div>\n"
                            + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                            + "                    <label for=\"pmobilno\">New Mobile No.:</label>\n"
                            + "                    <input type=\"text\" value=\"" + rs.getString("pmobilno") + "\" id=\"pmobilno\" name=\"pmobilno\" placeholder=\"Enter Mobile No\">\n"
                            + "                </div>\n"
                            + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                            + "                    <input type=\"submit\" id=\"sub\" value=\"Edit Profile\">\n"
                            + "                </div>\n"
                            + "            </form>\n"
                            + "        </div>");
                }

            } catch (Exception ex) {
                out.println(ex);
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
}
