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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ritac
 */
@WebServlet("/login")
public class Login extends HttpServlet {
private static final long serialVersionUID = 1L;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        if(uemail == null || uemail.equals("")){
               request.setAttribute("status","invalidEmail");
                dispatcher = request.getRequestDispatcher("login.jsp");
                 dispatcher.forward(request, response);
        }
        if(upwd == null || upwd.equals("")){
              request.setAttribute("status","invalidUpwd");
                dispatcher = request.getRequestDispatcher("login.jsp");
                 dispatcher.forward(request, response);
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "");
          PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from users where uemail = ? and upwd = ?");
        pst.setString(1, uemail);
            pst.setString(2, upwd);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                session.setAttribute("name", rs.getString("uname"));
                dispatcher = request.getRequestDispatcher("index.jsp");
            }else{
                request.setAttribute("status", "failed");
                               dispatcher = request.getRequestDispatcher("login.jsp");
            }
             dispatcher.forward(request, response);
        }catch(ServletException | IOException | ClassNotFoundException | SQLException e){
        }
    }

}
