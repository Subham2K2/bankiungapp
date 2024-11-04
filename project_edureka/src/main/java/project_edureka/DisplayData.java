package project_edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/display")
public class DisplayData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/edureka", "root", "Subham@2002");
        return con;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        PrintWriter out = response.getWriter();
	        Cookie[] cookies = request.getCookies();  
	        String user = null;
	        
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals("username")) {
	                    user = cookie.getValue();
	                    break;
	                }
	            }
	        }
	        
	        if (user == null) {
	            out.println("<p>User not logged in!</p>");
	            return;
	        }
	        
	        Connection con = initializeDatabase();
	        
	        String sql = "SELECT * FROM statements WHERE Date BETWEEN ? AND ? AND Username = ?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, request.getParameter("from"));
	        pst.setString(2, request.getParameter("to"));
	        pst.setString(3, user);

	        ResultSet rs = pst.executeQuery();
	        
	        out.println("<html><body style='background-color:MediumSeaGreen'><h2 style='text-align:center'>Display Statement</h2>"
	                + "<form action='display' method='post'>"
	                + "	<label for='date'>Date Range:</label><br>"
	                + "	<label for='from'>From:</label>"
	                + "	<input type='date' id='from' name='from'>"
	                + "	<label for='to'>To:</label>"
	                + "	<input type='date' id='to' name='to'>"
	                + "	<input type='submit' value='Display'>"
	                + "</form><br><br>");
	        
	        if (!rs.isBeforeFirst()) {    
	            out.println("<p>No data found for the given date range.</p>");
	        } else {
	            out.println("<table border=1><tr><th>Trans_No</th><th>Date</th><th>Description</th><th>Cheque_No</th>"
	                        + "<th>Withdraw</th><th>Deposit</th><th>Available_Balance</th></tr>");
	            
	            while(rs.next()) {
	                String trans_No = rs.getString("Trans_No");
	                String date = rs.getString("Date");
	                String desc = rs.getString("Description");
	                String cheq = rs.getString("Cheque_No");
	                String withdraw = rs.getString("Withdraw");
	                String deposit = rs.getString("Deposit");
	                String bal = rs.getString("Available_Balance");
	                
	                out.println("<tr><td>"+ trans_No +"</td><td>"+ date +"</td><td>"+ desc + "</td><td>"+ cheq +"</td><td>"+ withdraw +"</td><td>"+ deposit + "</td><td>"+ bal +"</td></tr>");
	            }
	            
	            out.println("</table>");
	        }
	        
	        out.println("</body></html>");
	        
	        rs.close();
	        pst.close();
	        con.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}







