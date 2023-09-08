
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddEvent extends HttpServlet {

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
            IOException, ServletException {

        init();
        response.setContentType("text/html");

        // session management
        session = request.getSession(false);
//        String un = session.getAttribute("un").toString();
        if (session == null) {
            RequestDispatcher rd = request.getRequestDispatcher("LogoutServlet");
            rd.forward(request, response);
        } else {
            PrintWriter out = response.getWriter();
            //Fetching data from HTML form
            String a1 = request.getParameter("EventNo");
            String a2 = request.getParameter("EventName");
            String a3 = request.getParameter("coordinatorName");
            String a4 = request.getParameter("CoordinatorNo");
            String a5 = request.getParameter("fee");
            String a6 = request.getParameter("venue");
            String a7 = request.getParameter("date");
            String a8 = request.getParameter("maxCapacity");
            
            out.println("<!DOCTYPE html>"
                    + "<html>\n"
                    + "<head></head>\n"
                    + "<body>\n");

            //Connection to Database
            try {

                pstmt = con.prepareStatement("INSERT INTO event_ "
                        + "(enum,ename,coordinator,coornum,fees,venue,edate,maxCapacity,currCapacity) "
                        + "values (?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, a1);
                pstmt.setString(2, a2);
                pstmt.setString(3, a3);
                pstmt.setString(4, a4);
                pstmt.setString(5, a5);
                pstmt.setString(6, a6);
                pstmt.setString(7, a7);
                pstmt.setString(8, a8);
                pstmt.setString(9, a8);
                int t = pstmt.executeUpdate();

                RequestDispatcher rd = request.getRequestDispatcher("EventDetails.html");
                rd.include(request, response);

                out.println("<br><center><h3> Event Added</h3></center>");
                System.out.println("Added to database!");
            } catch (Exception exe) {
                out.println(exe);
                out.println("Add event details again");
                RequestDispatcher rd = request.getRequestDispatcher("CreateE.html");
                rd.include(request, response);
            }

            out.println("</body></html>");
        }
    }
}
