package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_PER_DAY_LIMIT = 2000;
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new InMemoryMealStorage();
        log.debug("Initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String id = request.getParameter("id");
        final String action = request.getParameter("action");

        if (action == null) {
            forwardToMealsView(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                log.debug("delete action");
                return;
            case "add":
                meal = new Meal(LocalDateTime.now(), "", 0);
                log.debug("add action");
                break;
            case "update":
                meal = storage.get(Integer.parseInt(id));
                log.debug("update action");
                break;
            default:
                forwardToMealsView(request, response);
                return;
        }

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("jsp/mealEdit.jsp").forward(request, response);
        log.debug("forward on meal edit form");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        final String id = request.getParameter("id");

        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (id.isEmpty()) {
            storage.create(meal);
            log.debug("new meal created");
        } else {
            meal.setId(Integer.parseInt(id));
            storage.update(meal);
            log.debug("meal with id {} updated", meal.getId());
        }

        response.sendRedirect("meals");
        log.debug("redirected to meals");
    }

    private void forwardToMealsView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("allMeals",
                MealsUtil.filteredByStreams(storage.getAll(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        CALORIES_PER_DAY_LIMIT));

        request.getRequestDispatcher("jsp/meals.jsp").forward(request, response);
        log.debug("Forward to meals");
    }
}
