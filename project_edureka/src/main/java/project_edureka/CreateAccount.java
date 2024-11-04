//package project_edureka;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/create_account")
//public class CreateAccount extends HttpServlet {
//	
//private static final long serialVersionUID = 1L;
//	
//	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException{
//
//			Class.forName("com.mysql.cj.jdbc.Driver");
//	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/edureka", "root", "Subham@2002");
//	        return con;
//	    }
//	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//response.getWriter().append("Served at: ").append(request.getContextPath());
//		try {
//			
//			Connection con = initializeDatabase();
//			PreparedStatement pst = con.prepareStatement("insert into account_details (Name, DOB, Address, Email_ID, Account_Type, Username) values(?, ?, ?, ?, ?, ?)");
//  
//			Cookie ck[] = request.getCookies();  
//			String user = ck[0].getValue();
//			
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date myDate = (Date) formatter.parse(request.getParameter("dob"));
//            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
//            
//            pst.setString(1, request.getParameter("name"));
//            pst.setDate(2, sqlDate);
//            pst.setString(3, request.getParameter("address"));
//            pst.setString(4, request.getParameter("email"));
//            pst.setString(5, request.getParameter("type"));
//            pst.setString(6, user);
//  
//            // Execute the insert command using executeUpdate()
//            // to make changes in database
//            int count = pst.executeUpdate();
//            if(count!=0) {
//                PrintWriter out = response.getWriter();
//                out.println("<html><body><b>Account created Successfully!" + "</b></body></html>");
//            } else {
//            	PrintWriter out = response.getWriter();
//            	out.println("<html><body><b>User Account is not created!" + "</b></body></html>");
//            }
//            // Close all the connections
//            pst.close();
//            con.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
//	
//}

package project_edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/create_account")
public class CreateAccount extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
	
    protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/edureka", "root", "Subham@2002");
        return con;
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = initializeDatabase();
            PreparedStatement pst = con.prepareStatement("insert into account_details (Name, DOB, Address, Email_ID, Account_Type, Username) values(?, ?, ?, ?, ?, ?)");
  
            // Retrieve the username from cookies
            Cookie ck[] = request.getCookies();  
            String user = ck[0].getValue();
            System.out.println(user);
			
            // Parse the date from the request and convert it to java.sql.Date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = formatter.parse(request.getParameter("dob"));
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            // Set the parameters for the prepared statement
            pst.setString(1, request.getParameter("name"));
            pst.setDate(2, sqlDate);
            pst.setString(3, request.getParameter("address"));
            pst.setString(4, request.getParameter("email"));
            pst.setString(5, request.getParameter("type"));
//            pst.setString(6, request.getParameter("username"));
            pst.setString(6, user);
  
            // Execute the insert command
            int count = pst.executeUpdate();
//            PrintWriter out = response.getWriter();
            if(count!=0) {
                PrintWriter out = response.getWriter();
                out.println("<html><body><b>Account created Successfully!" + "</b></body></html>");
            } else {
            	PrintWriter out = response.getWriter();
            	out.println("<html><body><b>User Account is not created!" + "</b></body></html>");
            }
            
            // Close the prepared statement and connection
            pst.close();
            con.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

