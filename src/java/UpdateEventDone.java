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
public class UpdateEventDone extends HttpServlet {
    Connection con = null;
    PreparedStatement pstmt2,pstmt3 = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql="update event_ set "
            + "ename=?, coordinator=?, coornum=?, "
            + "fees=?, venue=?, edate=?, maxCapacity=?, currCapacity=? "
            + "where enum=?";
    String sql1="select * from event_ where enum=?";

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");
            
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            pstmt2=con.prepareStatement(sql);
            pstmt3=con.prepareStatement(sql1);
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
            pstmt3.setString(1,request.getParameter("enum"));
            rs=pstmt3.executeQuery();
            int maxCapacity=0,currCapacity=0;
            if(rs.next()){
                maxCapacity=rs.getInt("maxCapacity");
                currCapacity=rs.getInt("currCapacity");
            }
            int diff=0;
            int newCapacity=Integer.parseInt(request.getParameter("maxCapacity"));
            if(newCapacity<maxCapacity){
               diff=maxCapacity-newCapacity;
               currCapacity-=diff;
            }
            else{
                diff=newCapacity-maxCapacity;
                currCapacity+=diff;
            }
            pstmt2.setString(1, request.getParameter("EventName"));
            pstmt2.setString(2, request.getParameter("coordinatorName"));
            pstmt2.setString(3, request.getParameter("CoordinatorNo"));
            pstmt2.setString(4, request.getParameter("fee"));
            pstmt2.setString(5, request.getParameter("venue"));
            pstmt2.setString(6, request.getParameter("date"));
            pstmt2.setInt(7, newCapacity);
            pstmt2.setInt(8, currCapacity);
            pstmt2.setString(9, request.getParameter("enum"));
            int t=pstmt2.executeUpdate();
            response.sendRedirect("ViewEvent1");
        }
        catch(Exception ex){
            out.println(ex);
        }
        out.print("</body>");
        out.print("</html>");
    }
}
