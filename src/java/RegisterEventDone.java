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
public class RegisterEventDone extends HttpServlet {
    Connection con = null;
    PreparedStatement pstmt2,pstmt3,pstmt4 = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql="insert into card_ "
            + "(cardno,cardname,cvv,exdate,payment,enum,ename,pusername) values "
            + "(?,?,?,?,?,?,?,?)";
    String sql1="select currCapacity from event_ "
            + "where enum=?";
    String sql2="update event_ set "
            + "currCapacity=? where "
            + "enum=?";

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");
            
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            pstmt2=con.prepareStatement(sql);
            pstmt3=con.prepareStatement(sql1);
            pstmt4=con.prepareStatement(sql2);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws
            IOException, ServletException{
        init();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Event Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");
        try{
            pstmt2.setString(1, request.getParameter("cardno"));
            pstmt2.setString(2, request.getParameter("cname"));
            pstmt2.setString(3, request.getParameter("cvv"));
            pstmt2.setString(4, request.getParameter("edate"));
            pstmt2.setString(5, request.getParameter("fees"));
            pstmt2.setString(6, request.getParameter("enum"));
            pstmt2.setString(7, request.getParameter("ename"));
            pstmt2.setString(8, request.getParameter("pusername"));
            int t=pstmt2.executeUpdate();
            
            pstmt3.setString(1, request.getParameter("enum"));
            rs=pstmt3.executeQuery();
            rs.next();
            int currCapacity=rs.getInt("currCapacity");
            pstmt4.setInt(1, currCapacity-1);
            pstmt4.setString(2, request.getParameter("enum"));
            t=pstmt4.executeUpdate();
            
            response.sendRedirect("ViewEvent2");
        }
        catch(Exception ex){
            out.println(ex);
        }
        out.println("</body>");
        out.println("</html>");
    }
}
