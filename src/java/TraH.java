
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TraH extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;
    HttpSession session = null;
    String sql="select * from card_";

    public void init() {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("Driver");
            String url = context.getInitParameter("URL");
            
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            pstmt2=con.prepareStatement(sql);
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
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Transactions Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<header>\n"
                + "<h1>Welcome To Event Portal</h1>\n"
                + "<nav>\n"
                + "<a href=\"LogoutServlet\">Logout</a>\n"
                + "</nav>\n"
                + "</header>");

        try {
            rs = pstmt2.executeQuery();

            out.println("<h1 style=\"margin:20px;\"> Transaction  Details </h1>");

            out.println("<div>");
            out.println("<table>");
            out.println("<tr><th>Event No</th><th>Event Name</th>"
                    + "<th>Card No</th><th>Card Holder Name</th>");

            while (rs.next()) {

                String en = rs.getString("enum");
                String re = rs.getString("ename");
                String pd = rs.getString("cardname");
                String name = rs.getString("cardno");

                out.println("<tr><td>" + en + "</td><td>" + re + "</td><td>" + name + "</td><td>" + pd + "</td></tr>");
            }

            out.println("</table>");
            out.println("</h3>");
            out.println("</div>");
        } catch (Exception exe) {
            out.println(exe);
        }
        out.println("<footer>\n"
                + "            <div class=\"container1\">\n"
                + "                <p>&copy; 2023 Event Portal</p>\n"
                + "                <ul>\n"
                + "                    <li><a href=\"#\">Terms of Service</a></li>\n"
                + "                    <li><a href=\"#\">Privacy Policy</a></li>\n"
                + "                    <li><a href=\"#\">Contact Us</a></li>\n"
                + "                </ul>\n"
                + "            </div>\n"
                + "        </footer>");
        out.print("</body>");
        out.print("</html>");
    }
}
