package project_edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

private static PreparedStatement pst;
	
	public void init(ServletConfig config) throws ServletException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/edureka", "root", "Subham@2002");
			
			String sql = "select * from users where username = ? and password = ?";
			pst = con.prepareStatement(sql);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			pst.setString(1, user);
			pst.setString(2, password);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("menu.html");
				rd.forward(request, response);
				Cookie c1 = new Cookie("username", user);		
				c1.setMaxAge(60); 	//1s
				response.addCookie(c1);
				
			}else {
				PrintWriter out = response.getWriter();
				out.println("<p style=font-size:20px;color:yellow;text-align:center>Invalid Username/Password ... Try again</p>");
				RequestDispatcher rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}
			
			Cookie[] cookies = request.getCookies();
			 
			for (Cookie aCookie : cookies) {
			    String name = aCookie.getName();
			    String value = aCookie.getValue();
			 
			    System.out.println("Inside LoginServlet.java\n" + name + " = " + value);
			}
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
