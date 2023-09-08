
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class StoreP extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        //Fetching data from Psignup.html from user
        String up = request.getParameter("Pusername");
        String pp = request.getParameter("Ppassword");
        String cp = request.getParameter("Cpassword");
        String name = request.getParameter("Pname");
        String email = request.getParameter("Pemail");
        String monum = request.getParameter("Pmobileno");

        if (pp.equals(cp)) {
            //Connection & storing into Database
            try {

                pstmt = con.prepareStatement("insert into participents "
                        + "(pusername,ppassword,pname,pemail,pmobilno) "
                        + "values (?,?,?,?,?)");
                pstmt.setString(1, up);
                pstmt.setString(2, pp);
                pstmt.setString(3, name);
                pstmt.setString(4, email);
                pstmt.setString(5, monum);
                int tt = pstmt.executeUpdate();

                RequestDispatcher rd = request.getRequestDispatcher("Plogin.html");
                rd.forward(request, response);

            } catch (Exception exe) {
                out.println(exe);
            }

        } else {
            out.println("<center><h1>!! Please Enter Password And Confirm Password Same !!</h1><center>");
            RequestDispatcher rd = request.getRequestDispatcher("Psignup.html");
            rd.include(request, response);
        }

    }

}
