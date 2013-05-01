package vn.edu.rmit.assessedlab2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventServlet extends HttpServlet {
    private static final String CITY_PARAMETER_COOKIE_NAME = "city", CATEGORY_PARAMETER_COOKIE_NAME = "category",
            EVENT_FILE_NAME = "/events.txt", OPTION_ALL = "All";
    private static final int COOKIE_MAX_AGE = 2592000;

    private static enum EVENT_FIELD {
        City, Category, Title, Duration
    }

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

        // Render page.
        renderPage(writer, cookies);

        // Close print writer.
        writer.close();
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

        // Create response cookies.
        Cookie cookies[] = new Cookie[2];
        Cookie cookie = new Cookie(CITY_PARAMETER_COOKIE_NAME, city);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        resp.addCookie(cookie);
        cookies[0] = cookie;
        cookie = new Cookie(CATEGORY_PARAMETER_COOKIE_NAME, category);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        resp.addCookie(cookie);
        cookies[1] = cookie;

        // Get print writer.
        PrintWriter writer = resp.getWriter();

        // Render page.
        renderPage(writer, cookies);

        // Close print writer.
        writer.close();
    }

    /**
     * Read event schedule from file.
     *
     * @return
     */
    private List<String[]> parseEvents() {
        List<String[]> events = new ArrayList<String[]>();
        try {
            // Attempt to open file for reading.
//            File file = new File(EVENT_FILE_NAME);
            InputStream file = getServletContext().getResourceAsStream(EVENT_FILE_NAME);
            Scanner scanner = new Scanner(file);

            // Read each line.
            for (int count = 0; scanner.hasNext(); count++) {
                String event = scanner.nextLine();

                // Parse event fields.
                String[] fields = event.split("\t");

                // If number of fields is not correct, throw error.
                if (fields.length != EVENT_FIELD.values().length) {
                    throw new Exception("Invalid event file! Number of fields not correct at line " + count + ".");
                }

                // Add event to list.
                events.add(fields);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return events;
    }

    /**
     * Get all cities from event list.
     *
     * @param events
     * @return
     */
    private List<String> parseCities(List<String[]> events) {
        List<String> cities = new ArrayList<String>();

        for (String[] event : events) {
            String city = event[EVENT_FIELD.City.ordinal()];
            if (!cities.contains(city)) {
                cities.add(city);
            }
        }

        return cities;
    }

    /**
     * Get all categories from event list.
     *
     * @param events
     * @return
     */
    private List<String> parseCategories(List<String[]> events) {
        List<String> categories = new ArrayList<String>();

        for (String[] event : events) {
            String category = event[EVENT_FIELD.Category.ordinal()];
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        return categories;
    }

    /**
     * Render page contents.
     *
     * @param writer
     * @param cookies
     */
    private void renderPage(PrintWriter writer, Cookie[] cookies) {
        List<String[]> events = parseEvents();
        List<String> cities = parseCities(events), categories = parseCategories(events);
        String city = null, category = null;

        // Write header.
        appendHeader(writer);

        // If cookies are List, show monthly events.
        if (cookies != null) {
            // Check if city and category cookie existed and update values.
            for (Cookie cookie : cookies) {
                // If city cookie existed, modify city value.
                String name = cookie.getName();
                if (name.equalsIgnoreCase(CITY_PARAMETER_COOKIE_NAME)) {
                    city = cookie.getValue();
                } else if (name.equalsIgnoreCase(CATEGORY_PARAMETER_COOKIE_NAME)) {
                    category = cookie.getValue();
                }
            }
        }

        // Show input form.
        showInputForm(writer, cities, categories, city, category);

        // Show event table.
        showEventTable(writer, events, city, category);

        // Write footer.
        appendFooter(writer);
    }

    /**
     * Write HTML header information.
     *
     * @param writer
     */
    private void appendHeader(PrintWriter writer) {
        writer.append("<html>\n");
        writer.append("<head>\n");
        writer.append("<title>Monthly Event Schedule</title>\n");
        writer.append("</head>\n");
        writer.append("<body>\n");
        writer.append("<h1>Monthly Event Schedule</h1>\n");
    }

    /**
     * Show input form for city and category.
     *
     * @param writer
     * @param city
     * @param category
     */
    private void showInputForm(PrintWriter writer, List<String> cities, List<String> categories, String city,
                               String category) {
        writer.append("<form method='post' action='#'>\n");
        writer.append("<fieldset>\n");
        writer.append("<legend>Configurations:</legend>\n");
        writer.append("City:&nbsp;\n");
        writer.append("<select name='" + CITY_PARAMETER_COOKIE_NAME + "'>\n");
        writer.append("<option value='" + OPTION_ALL + "'");
        if (city.equalsIgnoreCase(OPTION_ALL)) {
            writer.append(" selected='selected'");
        }
        writer.append(">" + OPTION_ALL + "</option>\n");
        for (String item : cities) {
            writer.append("<option value='" + item + "'");
            if (item.equalsIgnoreCase(city)) {
                writer.append(" selected='selected'");
            }
            writer.append(">" + item + "</option>\n");
        }
        writer.append("</select>\n");
        writer.append("&nbsp;&nbsp;&nbsp;\n");
        writer.append("Category:&nbsp;\n");
        writer.append("<select name='" + CATEGORY_PARAMETER_COOKIE_NAME + "'>\n");
        writer.append("<option value='" + OPTION_ALL + "'");
        if (city.equalsIgnoreCase(OPTION_ALL)) {
            writer.append(" selected='selected'");
        }
        writer.append(">" + OPTION_ALL + "</option>\n");
        for (String item : categories) {
            writer.append("<option value='" + item + "'");
            if (item.equalsIgnoreCase(category)) {
                writer.append(" selected='selected'");
            }
            writer.append(">" + item + "</option>\n");
        }
        writer.append("</select>\n");
        writer.append("&nbsp;&nbsp;&nbsp;\n");
        writer.append("<input type='submit' value='Update' />\n");
        writer.append("</fieldset>\n");
        writer.append("</form>\n");
    }

    /**
     * Show event table.
     *
     * @param writer
     * @param city
     * @param category
     */
    private void showEventTable(PrintWriter writer, List<String[]> events, String city, String category) {
        writer.append("<h2>Monthly ");
        if (!category.equalsIgnoreCase(OPTION_ALL)) {
            writer.append(category + " ");
        }
        writer.append("events");
        if (!city.equalsIgnoreCase(OPTION_ALL)) {
            writer.append(" in " + city);
        }
        writer.append(":\n");
        writer.append("<table border='1' cellpadding='5'>\n");
        writer.append("<tr>\n");
        writer.append("<th>ID</th>\n");
        for (EVENT_FIELD field : EVENT_FIELD.values()) {
            writer.append("<th>" + field.name() + "</th>\n");
        }
        writer.append("</tr>\n");

        // Filter event list to match conditions.
        List<String[]> filteredEvents = new ArrayList<String[]>(), outputEvents = new ArrayList<String[]>();
        if (city.equalsIgnoreCase(OPTION_ALL)) {
            filteredEvents.addAll(events);
        } else {
            for (String[] event : events) {
                if (event[EVENT_FIELD.City.ordinal()].equalsIgnoreCase(city)) {
                    filteredEvents.add(event);
                }
            }
        }
        if (category.equalsIgnoreCase(OPTION_ALL)) {
            outputEvents.addAll(filteredEvents);
        } else {
            for (String[] event : filteredEvents) {
                if (event[EVENT_FIELD.Category.ordinal()].equalsIgnoreCase(category)) {
                    outputEvents.add(event);
                }
            }
        }

        // Display filtered event list.
        for (int i = 0; i < outputEvents.size(); i++) {
            String[] event = outputEvents.get(i);
            writer.append("<tr>\n");
            writer.append("<td>" + (i + 1) + "</td>\n");
            for (EVENT_FIELD field : EVENT_FIELD.values()) {
                writer.append("<td>" + event[field.ordinal()] + "</td>\n");
            }
            writer.append("</tr>\n");
        }

        writer.append("</table>\n");
    }

    /**
     * Write HTML footer information.
     *
     * @param writer
     */
    private void appendFooter(PrintWriter writer) {
        writer.append("</body>\n");
        writer.append("</html>\n");
    }
}
