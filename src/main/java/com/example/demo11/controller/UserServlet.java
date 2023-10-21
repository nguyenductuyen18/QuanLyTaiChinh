package com.example.demo11.controller;

import com.example.demo11.model.User;
import com.example.demo11.sevice.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    public static UserDAO userDAO;


    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {

            case "Register":
                req.getRequestDispatcher("Register.jsp").forward(req, resp);
                break;
            default:
                break;
        }
        register(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        System.out.println(action);
        switch (action) {
            case "addUser":
                try {
                    addUser(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Login":
                register(req, resp);
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public static void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("user/listHome.jsp").forward(request, response);//vị chí sẽ chuyền tới sau khi nhấn vào
    }

    public static void addUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        System.out.println(password);
        System.out.println(confirmPassword);
        if (password.equals(confirmPassword)) {
            String gender = request.getParameter("gender");
            String email=request.getParameter("email");
            String birthdateString = request.getParameter("birthdate");
            String image = request.getParameter("image");
            int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
            System.out.println(userName + password + gender + birthdateString + phoneNumber);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdate = null;
            try {
                birthdate = sdf.parse(birthdateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }




            userDAO.addUser(new User(fullName, userName, password,email, gender,image, birthdate, phoneNumber));
            boolean userAddedSuccessfully = true;
            if (userAddedSuccessfully) {
                request.getRequestDispatcher("users/list.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("RegisterFailure.jsp").forward(request, response);
            }
            System.out.println("thanh cong");

        } else {
            System.out.println("hello ");
        }
    }

}
