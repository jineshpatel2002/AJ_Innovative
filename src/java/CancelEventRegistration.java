/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author 91951
 */
public class CancelEventRegistration extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4 = null;
    ResultSet rs1, rs2 = null;
    String sql = "select * from card_ "
            + "where cid=?";
    String sql1 = "select currCapacity from event_ "
            + "where enum=?";
    String sql2 = "delete from card_ "
            + "where cid=?";
    String sql3 = "update event_ set "
            + "currCapacity=? where enum=?";

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_innovative", "root", "");
            pstmt1 = con.prepareStatement(sql);
            pstmt2 = con.prepareStatement(sql1);
            pstmt3 = con.prepareStatement(sql2);
            pstmt4 = con.prepareStatement(sql3);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doPost(HttpServletRequest request,
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
        try {
            int cid = Integer.parseInt(request.getParameter("cid"));
            pstmt1.setInt(1, cid);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                String n = rs1.getString("enum");
                pstmt2.setString(1, n);
                rs2 = pstmt2.executeQuery();
                if (rs2.next()) {
                    int currCapacity = rs2.getInt("currCapacity");
                    pstmt4.setInt(1, currCapacity +1);
                    pstmt4.setString(2, n);
                    int t = pstmt4.executeUpdate();
                    
                    pstmt3.setInt(1, cid);
                    t=pstmt3.executeUpdate();
                }
            }
            response.sendRedirect("MyEvents");
        } catch (Exception ex) {
            out.println(ex);
        }
        out.println("</body>");
        out.println("</html>");
    }
}
