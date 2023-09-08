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
public class DeleteEvent extends HttpServlet {
    
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql="delete from event_ "
            + "where enum=?";

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");
            
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            pstmt=con.prepareStatement(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            IOException, ServletException{
        
        init();
        response.setContentType("text/html");
        session = request.getSession(false);
        if(session==null)
        {
            RequestDispatcher rd = request.getRequestDispatcher("LogoutServlet");
            rd.forward(request, response);
        }
        else
        {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Event Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link rel=\"stylesheet\" href=\"ViewEvent1.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");
        
        try{
            pstmt.setString(1,request.getParameter("enum"));
            int t=pstmt.executeUpdate();
            RequestDispatcher rd = request.getRequestDispatcher("ViewEvent1");
            rd.include(request, response);
        }
        catch(Exception ex){
            out.println(ex);
        }
        out.print("</body>");
        out.print("</html>");
        }
    }
}
