/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 91951
 */
public class editProfileDone extends HttpServlet {
    Connection con = null;
    PreparedStatement pstmt1, pstmt2 = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql = "update participents set "
            + "ppassword=?, pname=?, pemail=?, pmobilno=? "
            + "where pusername=?";

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");

            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            pstmt1 = con.prepareStatement(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws
            IOException, ServletException{
        init();
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setContentType("text/html");

        session = request.getSession(false);
//        if(session.)
        if (session != null) {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Event Page</title>");
            out.println("<link rel=\"stylesheet\" href=\"style.css\">");
            out.println("<link rel=\"stylesheet\" href=\"RegisterEvent.css\">");
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
            try{
                pstmt1.setString(1, request.getParameter("ppassword"));
                pstmt1.setString(2, request.getParameter("pname"));
                pstmt1.setString(3, request.getParameter("pemail"));
                pstmt1.setString(4, request.getParameter("pmobilno"));
                pstmt1.setString(5, request.getParameter("pusername"));
                int t=pstmt1.executeUpdate();
                response.sendRedirect("ViewEvent2");
            }
            catch(Exception ex){
                out.println(ex);
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
}
