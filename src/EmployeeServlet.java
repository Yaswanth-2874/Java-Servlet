import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empId = request.getParameter("empId");

        try (Connection con = DBUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs;

            if (empId != null && !empId.isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM employees WHERE id = " + empId);
                if (rs.next()) {
                    out.println("<h2>Employee Details</h2>");
                    out.println("ID: " + rs.getInt("id") + "<br>");
                    out.println("Name: " + rs.getString("name") + "<br>");
                    out.println("Department: " + rs.getString("department") + "<br>");
                    out.println("Salary: " + rs.getDouble("salary") + "<br>");
                } else {
                    out.println("<p>No employee found with ID " + empId + "</p>");
                }
            } else {
                rs = stmt.executeQuery("SELECT * FROM employees");
                out.println("<h2>All Employees</h2>");
                while (rs.next()) {
                    out.println("<hr>");
                    out.println("ID: " + rs.getInt("id") + "<br>");
                    out.println("Name: " + rs.getString("name") + "<br>");
                    out.println("Department: " + rs.getString("department") + "<br>");
                    out.println("Salary: " + rs.getDouble("salary") + "<br>");
                }
            }

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
