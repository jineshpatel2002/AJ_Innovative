/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author 91951
 */
public class MyEvents extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt1, pstmt2 = null;
    ResultSet rs1, rs2 = null;
    String sql = "select * from card_ "
            + "where pusername=?";
    String sql1 = "select * from event_ "
            + "where enum=?";

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_innovative", "root", "");
            pstmt1 = con.prepareStatement(sql);
            pstmt2 = con.prepareStatement(sql1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            IOException, ServletException {

        init();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Event Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link rel=\"stylesheet\" href=\"ViewEvent2.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<header>\n"
                + "<h1>Welcome To Event Portal</h1>\n"
                + "<nav>\n"
                + "<a href=\"editProfile\" style=\"float:left; margin-left:120px;\">Edit Profile</a>\n"
                + "<a href=\"MyEvents\" style=\"float:left;\">My Events</a>\n"
                + "<a href=\"LogoutServlet\">Logout</a>\n"
                + "</nav>\n"
                + "</header>");

        HttpSession session = request.getSession(false);
        String pusername = (String) session.getAttribute("pusername");
        try {
            pstmt1.setString(1, pusername);
            rs1 = pstmt1.executeQuery();

            out.println("<h1 style=\"margin:20px;\">My Events Details</h1>");
            out.println("<div class=\"card-container\">");
            while (rs1.next()) {
                int cid=rs1.getInt("cid");
                String n = rs1.getString("enum");
                pstmt2.setString(1, n);
                rs2 = pstmt2.executeQuery();
                rs2.next();
                String nm = rs2.getString("ename");
                String edate = rs2.getString("edate");

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(edate);

                Calendar currentCalendar = Calendar.getInstance();
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(date);
                int flag = dateCalendar.compareTo(currentCalendar);

                Random rand = new Random();
                int randomNumber = rand.nextInt(12001) % 12;
                String img = "image" + randomNumber + ".jpg";

                out.println("<div class=\"card\">");
                out.println("<img src=\"" + img + "\" alt=\"Event Image\">");
                out.println("<div class=\"card-content\">"); // use flexbox to align items horizontally
                out.println("<h2 class=\"card-title\">" + nm + "</h2>");
                out.println("<p>" + "Fees: " + rs2.getString("fees") + ""
                        + "<br>Venue: " + rs2.getString("venue") + ""
                        + "<br>Date: " + edate + ""
                        + "" + "</p>");
                
                out.println("<form action=\"CancelEventRegistration\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"cid\" value=\"" + cid + "\">");
                if (flag < 0) {
                    out.println("<button class=\"card-button1\" type=\"submit\" style=\"color:green; font-weight:bold;\" disabled>Completed</button>");
                } else {
                    out.println("<button class=\"card-button\" type=\"submit\">Cancel</button>");
                }
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
            }
        } catch (Exception ex) {
            out.println(ex);
        }
        out.println("</body>");
        out.println("</html>");
    }
}
