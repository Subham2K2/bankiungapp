package project_edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/authorize")
public class AuthorizeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException{

		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/edureka", "root", "Subham@2002");
        return con;
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
		PrintWriter out = response.getWriter();
		
		Connection con = initializeDatabase();
		Float amount = Float.parseFloat(request.getParameter("amount"));
		
		String sql = "select * from credit_card where Card_No = ?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, request.getParameter("card"));
		
		ResultSet rs = pst.executeQuery();
		
		if(rs.next() && amount < 1) {
			out.println("<p style=font-size:20px;color:yellow;text-align:center>Transaction approved!</p>");
			RequestDispatcher rd = request.getRequestDispatcher("creditcard.html");
			rd.include(request, response);
		}	
		else {
			out.println("<p style=font-size:20px;color:yellow;text-align:center>Transaction denied, Invalid card ... Try again!</p>");
			RequestDispatcher rd = request.getRequestDispatcher("creditcard.html");
			rd.include(request, response);
		}
		pst.close();
        con.close();
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
