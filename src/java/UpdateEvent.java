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
public class UpdateEvent extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt1, pstmt2 = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql = "select * from event_ "
            + "where enum=?";
    String sql1 = "update event_ "
            + "set ename=? , coordinator=? , coornum=? , "
            + "fees=? , venue=? , edate=?, maxCapacity=?"
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
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setContentType("text/html");
        session = request.getSession(false);
        if(session==null)
        {
            RequestDispatcher rd = request.getRequestDispatcher("LogoutServlet");
            rd.forward(request, response);
        }
        else{
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Event Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link rel=\"stylesheet\" href=\"UpdateEvent.css\">");
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
            pstmt1.setString(1, request.getParameter("enum"));
            rs = pstmt1.executeQuery();
            rs.next();
            out.println("<p style=\"margin:10px; font-weight: bold;\">Update the event details</p>");
            out.println("<div class=\"form-container\">\n"
                    + "            <form method=\"post\" action=\"UpdateEventDone\">\n"
                    + "                <input type=\"hidden\" name=\"enum\" value=\"" + request.getParameter("enum") + "\">\n"
                    + "                <label for=\"EventName\">Event Name</label>\n"
                    + "                <input type=\"text\" value=\"" + rs.getString("ename") + "\" name=\"EventName\" id=\"EventName\" placeholder=\"Enter Event Name\">\n"
                    + "\n"
                    + "                <label for=\"coordinatorName\">Coordinator Name</label>\n"
                    + "                <input type=\"text\" value=\"" + rs.getString("coordinator") + "\" name=\"coordinatorName\" id=\"coordinatorName\" placeholder=\"Enter Coordinator Name\">\n"
                    + "\n"
                    + "                <label for=\"CoordinatorNo\">Coordinator Number</label>\n"
                    + "                <input type=\"text\" value=\"" + rs.getString("coornum") + "\" name=\"CoordinatorNo\" id=\"CoordinatorNo\" placeholder=\"Enter Coordinator Number\">\n"
                    + "\n"
                    + "                <label for=\"fee\">Fee for Registration</label>\n"
                    + "                <input type=\"text\" value=\"" + rs.getString("fees") + "\" name=\"fee\" id=\"fee\" placeholder=\"Enter Registration Fee\">\n"
                    + "\n"
                    + "                <label for=\"venue\">Venue of Event</label>\n"
                    + "                <input type=\"text\" value=\"" + rs.getString("venue") + "\" name=\"venue\" id=\"venue\" placeholder=\"Enter Event Venue\">\n"
                    + "\n"
                    + "                <label for=\"date\">Date of Event</label>\n"
                    + "                <input type=\"text\" value=\"" + rs.getString("edate") + "\" name=\"date\" id=\"date\" placeholder=\"Enter Event Date\">\n"
                    + "\n"
                    + "                <label for=\"macCapacity\">Max Capacity</label>\n"
                    + "                <input type=\"text\" value=\"" + rs.getString("maxCapacity") + "\" name=\"maxCapacity\" id=\"maxCapacity\" placeholder=\"Enter maximum Capacity\">\n"
                    + "\n"
                    + "                <input type=\"submit\" value=\"Submit\">\n"
                    + "            </form>\n"
                    + "        </div>");
            
        } catch (Exception ex) {
            out.println(ex);
        }

        out.print("</body>");
        out.print("</html>");
        }
        
    }
}
