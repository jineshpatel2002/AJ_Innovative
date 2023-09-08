
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class VaPa extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    HttpSession session = null;

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");
            
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {

        init();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        


        //Fetching data  from HTML form
        String n = request.getParameter("Pausername");
        String p = request.getParameter("Papassword");

        // session management
        session = request.getSession();
        session.setAttribute("pusername", n);
        
        try {
            pstmt=con.prepareStatement("select * from participents where pusername=? and ppassword=?");
            pstmt.setString(1, n);
            pstmt.setString(2, p);
            rs=pstmt.executeQuery();
            if(rs.next()){
                response.sendRedirect("ViewEvent2");
//                RequestDispatcher rd = request.getRequestDispatcher("ViewEvent2");
//            rd.forward(request, response);
            }
            else{
               out.print("<center><h1>Sorry username and password incorrect</h1></center>");
            RequestDispatcher rd = request.getRequestDispatcher("Plogin.html");
            rd.include(request, response); 
            }
            
        } catch (Exception ex) {
            out.println(ex);
        }
    }
}
