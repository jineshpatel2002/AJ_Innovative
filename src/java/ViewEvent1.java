
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class ViewEvent1 extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    HttpSession session = null;

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_innovative", "root", "");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            IOException, ServletException {

        init();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Event Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link rel=\"stylesheet\" href=\"ViewEvent1.css\">");
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
            Statement stmt1 = con.createStatement();
            pstmt = con.prepareStatement("select * from event_");
            rs = pstmt.executeQuery();

            out.println("<h1 style=\"margin:20px;\">Event Details</h1>");
            out.println("<div class=\"card-container\">");
            while (rs.next()) {
                String n = rs.getString("enum");
                String nm = rs.getString("ename");
                String edate = rs.getString("edate");

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = sdf.parse(edate);

                Calendar currentCalendar = Calendar.getInstance();
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(date);
                int flag = dateCalendar.compareTo(currentCalendar);

                if (flag >= 0) {
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(12001) % 12;
                    String img = "image" + randomNumber + ".jpg";

                    out.println("<div class=\"card\">");
                    out.println("<img src=\"" + img + "\" alt=\"Event Image\">");
                    out.println("<div class=\"card-content\">"); // use flexbox to align items horizontally
                    out.println("<h2 class=\"card-title\">" + nm + "</h2>");

                    out.println("<form action=\"UpdateEvent\" method=\"post\">");

                    out.println("<input type=\"hidden\" name=\"enum\" value=\"" + n + "\">");
                    out.println("<button class=\"card-button\" type=\"submit\">Update</button>");
                    out.println("</form>");
//                if(session!=null)
                    out.println("<a class=\"card-button\" href=\"DeleteEvent?enum=" + n + "\">Delete</a>");
//                else
                    out.println("</div>");
                    out.println("</div>");

                }
            }
            out.println("</div>");
            stmt1 = con.createStatement();
            pstmt = con.prepareStatement("select * from event_");
            rs = pstmt.executeQuery();

            out.println("<h1 style=\"margin:20px;\">Sold Out Events</h1>");
            out.println("<div class=\"card-container\">");
            while (rs.next()) {
                String n = rs.getString("enum");
                String nm = rs.getString("ename");
                String edate = rs.getString("edate");
                int currCapacity = rs.getInt("currCapacity");

                if (currCapacity==0) {
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(12001) % 12;
                    String img = "image" + randomNumber + ".jpg";

                    out.println("<div class=\"card\">");
                    out.println("<img src=\"" + img + "\" alt=\"Event Image\">");
                    out.println("<div class=\"card-content\">"); // use flexbox to align items horizontally
                    out.println("<h2 class=\"card-title\">" + nm + "</h2>");

                    out.println("<form action=\"UpdateEvent\" method=\"post\">");

                    out.println("<input type=\"hidden\" name=\"enum\" value=\"" + n + "\">");
                    out.println("<button class=\"card-button\" type=\"submit\">Update</button>");
                    out.println("</form>");
//                if(session!=null)
                    out.println("<a class=\"card-button\" href=\"DeleteEvent?enum=" + n + "\">Delete</a>");
//                else
                    out.println("</div>");
                    out.println("</div>");

                }
            }
            out.println("</div>");
            stmt1 = con.createStatement();
            pstmt = con.prepareStatement("select * from event_");
            rs = pstmt.executeQuery();

            out.println("<h1 style=\"margin:20px;\">Events Completed</h1>");
            out.println("<div class=\"card-container\">");
            while (rs.next()) {
                String n = rs.getString("enum");
                String nm = rs.getString("ename");
                String edate = rs.getString("edate");

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = sdf.parse(edate);

                Calendar currentCalendar = Calendar.getInstance();
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(date);
                int flag = dateCalendar.compareTo(currentCalendar);

                if (flag<0) {
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(12001) % 12;
                    String img = "image" + randomNumber + ".jpg";

                    out.println("<div class=\"card\">");
                    out.println("<img src=\"" + img + "\" alt=\"Event Image\">");
                    out.println("<div class=\"card-content\">"); // use flexbox to align items horizontally
                    out.println("<h2 class=\"card-title\">" + nm + "</h2>");

                    out.println("<form action=\"UpdateEvent\" method=\"post\">");

                    out.println("<input type=\"hidden\" name=\"enum\" value=\"" + n + "\">");
                    out.println("<button class=\"card-button\" type=\"submit\" disabled>Update</button>");
                    out.println("</form>");
//                if(session!=null)
                    out.println("<a class=\"card-button\" href=\"DeleteEvent?enum=" + n + "\">Delete</a>");
//                else
                    out.println("</div>");
                    out.println("</div>");

                }
            }
            out.println("</div>");
            out.print("</body>");
            out.print("</html>");
//            }
        } catch (Exception exe) {
            out.println("exe");
        }

    }
}
