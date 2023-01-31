/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.registration;

import com.mysql.jdbc.PreparedStatement;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author ritac
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
       /*PrintWriter out = response.getWriter();
       out.print("Working");*/
        String uname = request.getParameter("Your name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass");
        String umobile = request.getParameter("contact");
        RequestDispatcher dispatcher = null;
         //Connection con = null;
         /*PrintWriter out = response.getWriter();
        out.print(uname);
        out.print(uemail);
        out.print(upwd);
        out.print(umobile);*/
        
         try{
            Class.forName("com.mysql.jdbc.Driver");
           
            java.sql.Connection con;
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "");
            PreparedStatement pst = (PreparedStatement) con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?)");
            pst.setString(1, uname);
            pst.setString(2, upwd);
            pst.setString(3, uemail);
            pst.setString(4, umobile);
            int rowCount = pst.executeUpdate();
            dispatcher = request.getRequestDispatcher("registration.jsp");
            if (rowCount > 0) {
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("status", "failed");
            }
            
            dispatcher.forward(request, response);
             con.close();
    }catch(ServletException | IOException | ClassNotFoundException | SQLException e){
    }/*finally{
            try {
                con.close();
            } catch (SQLException e) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, e);
         }
            }*/
}
}
