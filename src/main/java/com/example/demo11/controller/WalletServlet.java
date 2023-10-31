package com.example.demo11.controller;

import com.example.demo11.model.User;
import com.example.demo11.model.Wallet;
import com.example.demo11.sevice.IWalletDAO;
import com.example.demo11.sevice.WalletDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "WalletServlet", value = "/wallet")
public class WalletServlet extends HttpServlet {
    public static WalletDAO walletDAO;
    private static IWalletDAO iWalletDAO;

    @Override
    public void init() throws ServletException {
        walletDAO = new WalletDAO();
        iWalletDAO = new WalletDAO();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "updateWallet":
                try {
                    updateWallet(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "showWalletUpdate":
                try {
                    showWalletUpdate(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }


    private void showWallet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(req.getParameter("id"));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Wallet wallet = walletDAO.checkID(id);
        req.setAttribute("wallet", wallet);
        List<Wallet> listWallet = iWalletDAO.showAllWallet(username, password);
        req.setAttribute("list", listWallet);

        req.getRequestDispatcher("user/listHome.jsp").forward(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        System.out.println(action);
        switch (action) {
            case "ShowWallet":
                try {
                    showWallet(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "addWallet":
                try {
                    addWallet(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void showWalletUpdate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        int idWallet = Integer.parseInt(req.getParameter("idWallet"));
        System.out.println(idWallet);
        List<Wallet> wallet = iWalletDAO.showWallet(idWallet);
        req.setAttribute("listWalletUpdate", wallet);

        req.getRequestDispatcher("Wallet/updateWallet.jsp").forward(req, resp);
    }

    private void updateWallet(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        int idWallet = Integer.parseInt(request.getParameter("idWallet"));
        String nameWallet = request.getParameter("nameWallet");
        String icon = request.getParameter("icon");
        Double money = Double.valueOf(request.getParameter("money"));

        String description = request.getParameter("description");
        String currency = request.getParameter("currency");
        System.out.println(idWallet + nameWallet + icon + money + description + currency);
        iWalletDAO.updateWallet(new Wallet(idWallet, icon, nameWallet, money, currency, description));



        List<Wallet> listWallet = iWalletDAO.showAllWallet(username, password);
        request.setAttribute("list", listWallet);
        request.getRequestDispatcher("user/listHome.jsp").forward(request, response);

    }

    private void addWallet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        int idUser = Integer.parseInt(req.getParameter("id"));
        System.out.println(idUser);
        String username = req.getParameter("username");
        System.out.println(username);
        String password = req.getParameter("password");
        String icon = req.getParameter("icon");
        String nameWallet = req.getParameter("nameWallet");
        Double money = Double.valueOf(req.getParameter("money"));
        String currency = req.getParameter("currency");
        String description = req.getParameter("description");
        Wallet wallet = new Wallet(icon, nameWallet, money, currency, description);
        iWalletDAO.addWallet(wallet);
        Wallet wallet1 = iWalletDAO.selectId(nameWallet);
        int idWallet = wallet1.getIdWallet();
        System.out.println(idWallet);
        iWalletDAO.addToUser_Wallet(idUser, idWallet);

        List<Wallet> listWallet = iWalletDAO.showAllWallet(username, password);
        req.setAttribute("list", listWallet);
        req.getRequestDispatcher("user/listHome.jsp").forward(req, resp);
    }
}

