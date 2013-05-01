package vn.edu.rmit.assessedlab2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;

public class EventServlet extends HttpServlet {
    private static final String CITY_PARAMETER_COOKIE_NAME = "city",
            CATEGORY_PARAMETER_COOKIE_NAME = "category";
    private static final int COOKIE_MAX_AGE = 2592000;

    /**
     * Handle GET request.
     *
     * @param req
     * @param resp
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws
            javax.servlet.ServletException, java.io.IOException {
        // Get all cookies.
        Cookie[] cookies = req.getCookies();

        // Get print writer.
        PrintWriter writer = resp.getWriter();

        // Show input form.
        showInputForm(writer);

        // If cookies are set, show monthly events.
        if (cookies != null) {
            // TODO: Show monthly events.
        }

        // Close print writer.
        writer.close();
    }

    /**
     * Show input form for city and category.
     *
     * @param writer
     */
    private void showInputForm(PrintWriter writer) {
        // TODO: Show input form.
    }

    /**
     * Handle POST request.
     *
     * @param req
     * @param resp
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws
            javax.servlet.ServletException, java.io.IOException {
        // Get parameters.
        String city = req.getParameter(CITY_PARAMETER_COOKIE_NAME),
                category = req.getParameter(CATEGORY_PARAMETER_COOKIE_NAME);

        // Get all cookies.
        Cookie[] cookies = req.getCookies();

        // Check if city and category cookie existed.
        boolean cityExisted = false, categoryExisted = false;
        for (Cookie cookie : cookies) {
            // If city cookie existed, modify city value.
            if (cookie.getName().equalsIgnoreCase(CITY_PARAMETER_COOKIE_NAME)) {
                cityExisted = true;

                cookie.setValue(city);
            } else if (cookie.getName().equalsIgnoreCase(CATEGORY_PARAMETER_COOKIE_NAME)) {
                categoryExisted = true;

                cookie.setValue(category);
            }
        }

        // If cookies not existed, create new ones.
        Cookie cookie = new Cookie(CITY_PARAMETER_COOKIE_NAME, city);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        resp.addCookie(cookie);
        cookie = new Cookie(CATEGORY_PARAMETER_COOKIE_NAME, category);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        resp.addCookie(cookie);
    }
}
