package ui.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        procesRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        procesRequest(request, response);
    }

    private void procesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination = "";
        String command = "home";
        if (request.getParameter("command") != null){
            command = request.getParameter("command");
        }
        
        switch (command){
            case "home":
                destination = home(request, response);
                break;
            case "more":
                destination = "more.jsp";
                break;
            case "setCookie":
                destination = setCookie(request, response);
                break;
            default:
                destination = home(request, response);
        }
        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request,response);
    }

    private String setCookie(HttpServletRequest request, HttpServletResponse response) {
        String quote = null;
        quote = request.getParameter("quote");

        Cookie cookie = new Cookie("quote", quote);
        response.addCookie(cookie);
        request.setAttribute("quote", quote);

        return "index.jsp";
    }

    private String home(HttpServletRequest request, HttpServletResponse response) {
        Cookie quoteCookie = getCookie(request);
        if (quoteCookie == null || quoteCookie.getValue().equals("no")){
            request.setAttribute("quote", "no");
        } else {
            request.setAttribute("quote", "yes");
        }
        return "index.jsp";
    }

    private Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        for(Cookie c : cookies){
            if (c.getName().equals("quote"))
                return c;
        }
        return null;
    }
}
