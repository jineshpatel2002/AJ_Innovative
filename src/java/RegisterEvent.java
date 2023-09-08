/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author 91951
 */
public class RegisterEvent extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt1, pstmt2 = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql = "select * from event_ "
            + "where enum=?";
    String sql1 = "update event_ "
            + "set ename=? , coordinator=? , coornum=? , "
            + "fees=? , venue=? , edate=? "
            + "where enum=?";

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");

            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            pstmt1 = con.prepareStatement(sql);
            pstmt2 = con.prepareStatement(sql1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doPost(HttpServletRequest request,
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
            String pusername = (String) session.getAttribute("pusername");
            try {
                pstmt1.setString(1, request.getParameter("enum"));
                rs = pstmt1.executeQuery();
                rs.next();
                out.println("<div class=\"container\">\n"
                        + "            <form method=\"post\" action=\"RegisterEventDone\">\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <label for=\"ename\">Enter Event Name:</label>\n"
                        + "                    <input type=\"text\" value=\"" + rs.getString("ename") + "\" id=\"ename\" name=\"ename\" placeholder=\"Event Name\" readonly>\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <label for=\"enum\">Enter Event No:</label>\n"
                        + "                    <input type=\"text\" value=\"" + rs.getString("enum") + "\" id=\"enum\" name=\"enum\" placeholder=\"Event No\" readonly>\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <label for=\"Fees\">Payment Of:</label>\n"
                        + "                    <input type=\"text\" value=\"" + rs.getString("fees") + "\" id=\"fees\" name=\"fees\" placeholder=\"Fees\" readonly>\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <label for=\"cardno\">Enter Card No:</label>\n"
                        + "                    <input type=\"text\" id=\"cardno\" name=\"cardno\" placeholder=\"Card No\">\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <label for=\"edate\">Enter Expiry Date:</label>\n"
                        + "                    <input type=\"text\" id=\"edate\" name=\"edate\" placeholder=\"MM/YY\">\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <label for=\"cvv\">Enter CVV No:</label>\n"
                        + "                    <input type=\"password\" id=\"cvv\" name=\"cvv\" placeholder=\"CVV\">\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <label for=\"cvv\">Enter Card Holder's Name:</label>\n"
                        + "                    <input type=\"text\" id=\"cname\" name=\"cname\" placeholder=\"Card Holder's Name\">\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <input type=\"hidden\" id=\"pusername\" name=\"pusername\" value=\"" + pusername + "\">\n"
                        + "                </div>\n"
                        + "                <div style=\"display: flex; flex-wrap: wrap;\">\n"
                        + "                    <input type=\"submit\" id=\"sub\" value=\"Proceed For Payment\">\n"
                        + "                </div>\n"
                        + "            </form>\n"
                        + "        </div>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception ex) {
                out.println(ex);
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("LogoutServlet");
            rd.forward(request, response);
        }

    }
}
