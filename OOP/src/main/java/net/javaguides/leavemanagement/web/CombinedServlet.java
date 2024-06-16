package net.javaguides.leavemanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.javaguides.leavemanagement.dao.CombinedDAO;
import net.javaguides.leavemanagement.model.Leave;
import net.javaguides.leavemanagement.model.User;

@WebServlet("/")
public class CombinedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CombinedDAO combinedDAO;

    public void init() {
        combinedDAO = new CombinedDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/login":
                    login(request, response);
                    break;
                case "/register":
                    register(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertLeave(request, response);
                    break;
                case "/delete":
                    deleteLeave(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateLeave(request, response);
                    break;
                default:
                    listLeave(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listLeave(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Leave> listLeave = combinedDAO.selectAllLeaves();
        request.setAttribute("leaveList", listLeave);
        RequestDispatcher dispatcher = request.getRequestDispatcher("leave-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("leave-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Leave existingLeave = combinedDAO.selectLeave(id);
                request.setAttribute("leave", existingLeave);
                RequestDispatcher dispatcher = request.getRequestDispatcher("leave-form.jsp");
                dispatcher.forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("list");
            }
        } else {
            response.sendRedirect("list");
        }
    }

    private void insertLeave(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String status = request.getParameter("status");
        String type = request.getParameter("type");
        Leave newLeave = new Leave(name, status, type);
        combinedDAO.insertLeave(newLeave);
        response.sendRedirect("list");
    }

    private void updateLeave(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                String type = request.getParameter("type");
                Leave leaveToUpdate = new Leave(id, name, status, type);
                combinedDAO.updateLeave(leaveToUpdate);
                response.sendRedirect("list");
            } catch (NumberFormatException e) {
                response.sendRedirect("list");
            }
        } else {
            response.sendRedirect("list");
        }
    }

    private void deleteLeave(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                combinedDAO.deleteLeave(id);
                response.sendRedirect("list");
            } catch (NumberFormatException e) {
                response.sendRedirect("list");
            }
        } else {
            response.sendRedirect("list");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contactNumber");

        User newUser = new User(username, password, email, contactNumber);
        System.out.println("Registering user: " + newUser);
        combinedDAO.registerUser(newUser);

        response.sendRedirect("login.jsp"); // Redirect to login page after successful registration
    }


    private void login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Login request received for username: " + username);

        User user = combinedDAO.loginUser(username, password);

        if (user != null) {
            // Login successful, you can set session attributes here if needed
            request.getSession().setAttribute("user", user);
            System.out.println("Login successful for username: " + username);
            response.sendRedirect("leave-list.jsp");
        } else {
            // Login failed
            System.out.println("Login failed for username: " + username);
            request.setAttribute("error", "Invalid username or password. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
