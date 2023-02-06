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

import ru.javawebinar.topjava.Config;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorageInterface;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorageInterface storage;

    @Override
    public void init(ServletConfig config)throws ServletException {
        super.init(config);
        storage = Config.get().getMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("redirect to meals");

        final String id = request.getParameter("id");
        final String action = request.getParameter("action");

        if(action == null){
            request.setAttribute("allMeals",
                    MealsUtil.filteredByStreams(storage.getAll(),
                            LocalTime.MIN,
                            LocalTime.MAX,
                            Config.CALORIES_PER_DAY_LIMIT));

            request.getRequestDispatcher("jsp/mealsView.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action){
            case "delete":
                storage.delete(Integer.valueOf(id));
                response.sendRedirect("meals");
                return;
            case "add":
                //meal = storage.create(LocalDateTime.now(), "", 0);
                meal = new Meal(LocalDateTime.now(), "", 0);
                break;
            case "update":
                meal = storage.get(Integer.valueOf(id));
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("jsp/mealEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        final String id = request.getParameter("id");
        final String description = request.getParameter("description");
        final String dateTime = request.getParameter("dateTime");
        final String calories = request.getParameter("calories");

        if(id.isEmpty()){
            storage.create(new Meal(LocalDateTime.parse(dateTime.subSequence(0, dateTime.length())),
                    description,
                    Integer.valueOf(calories).intValue()));
        }else{
            storage.update(new Meal(LocalDateTime.parse(dateTime.subSequence(0, dateTime.length())),
                    description,
                    Integer.valueOf(calories).intValue()));
        }

        response.sendRedirect("meals");
    }
}
