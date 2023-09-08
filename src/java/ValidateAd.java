
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ValidateAd extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    HttpSession session=null;
    
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_innovative", "root", "");
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
 
        String un = request.getParameter("Ausername"); // Fetch username
        String pw = request.getParameter("Apassword"); // Fetch password

        
                // session management
        session = request.getSession();
        session.setAttribute("un",un);
        session.setAttribute("pw",pw);

        
        out.println("<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n");

//        
        try {
//            session=request.getSession();
//            session.setAttribute("ausername", un);
            pstmt = con.prepareStatement("SELECT * FROM admin where ausername=? and apassword=?");
            pstmt.setString(1, un);
            pstmt.setString(2, pw);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                RequestDispatcher rd = request.getRequestDispatcher("AdminEvent.html");
                rd.forward(request, response);
            } else {
                out.println("<h3 style=\"text-align:center;\">!! Please Enter Valid Username & Password for Admin !!</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("Alogin.html");
                rd.include(request, response);
            }
        } catch (SQLException ex) {
            out.println(ex);
        }

        
        out.println("</body></html>");
    }

}
