package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    private static final int ALL_MEALS_ID_START = UserTestData.GUEST_ID + 1;
    //public static final List<Meal> ALL_MEALS;
    //public static final Meal EXISTED_USER_MEAL;
    public static final int NOT_EXISTED_MEAL_ID = 1;

    public static final Meal user_meal1 = new Meal(ALL_MEALS_ID_START,
            LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
            "Еда 1 USER", 1000);
    public static final Meal user_meal2 = new Meal(ALL_MEALS_ID_START + 1,
            LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
            "Еда 2 USER", 700);
    public static final Meal user_meal3 = new Meal(ALL_MEALS_ID_START + 2,
            LocalDateTime.of(2020, Month.JANUARY, 30, 18, 0),
            "Еда 3 USER", 299);
    public static final Meal user_meal4 = new Meal(ALL_MEALS_ID_START + 3,
            LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
            "Еда 4 USER", 700);
    public static final Meal user_meal5 = new Meal(ALL_MEALS_ID_START + 4,
            LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
            "Еда 5 USER", 700);
    public static final Meal user_meal6 = new Meal(ALL_MEALS_ID_START + 5,
            LocalDateTime.of(2020, Month.JANUARY, 31, 18, 0),
            "Еда 6 USER", 400);
    public static final Meal user_meal7 = new Meal(ALL_MEALS_ID_START + 6,
            LocalDateTime.of(2020, Month.JANUARY, 31, 23, 59, 59),
            "Еда 7 USER", 400);

    public static final Meal admin_meal1 = new Meal(ALL_MEALS_ID_START + 7,
            LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0),
            "Еда 1 ADMIN", 1000);
    public static final Meal admin_meal2 = new Meal(ALL_MEALS_ID_START + 8,
            LocalDateTime.of(2020, Month.JANUARY, 29, 13, 0),
            "Еда 2 ADMIN", 899);
    public static final Meal admin_meal3 = new Meal(ALL_MEALS_ID_START + 9,
            LocalDateTime.of(2020, Month.JANUARY, 29, 13, 0, 1),
            "Еда 3 ADMIN", 100);
    public static final Meal admin_meal4 = new Meal(ALL_MEALS_ID_START + 10,
            LocalDateTime.of(2020, Month.JANUARY, 29, 18, 0),
            "Еда 4 ADMIN", 1);
    public static final Meal admin_meal5 = new Meal(ALL_MEALS_ID_START + 11,
            LocalDateTime.of(2020, Month.JANUARY, 28, 10, 0),
            "Еда 5 ADMIN", 1000);
    public static final Meal admin_meal6 = new Meal(ALL_MEALS_ID_START + 12,
            LocalDateTime.of(2020, Month.JANUARY, 28, 13, 0),
            "Еда 6 ADMIN", 1000);
    public static final Meal admin_meal7 = new Meal(ALL_MEALS_ID_START + 13,
            LocalDateTime.of(2020, Month.JANUARY, 31, 18, 0),
            "Еда 6 ADMIN", 1000);
    public static final Meal admin_meal8 = new Meal(ALL_MEALS_ID_START + 14,
            LocalDateTime.of(2020, Month.JANUARY, 28, 18, 0),
            "Еда 7 ADMIN", 1);

    public static final List<Meal> userMeals = Arrays.asList(
            user_meal1, user_meal2, user_meal3, user_meal4, user_meal5, user_meal6, user_meal7
    );
    public static final List<Meal> adminMeals = Arrays.asList(
            admin_meal1, admin_meal2, admin_meal3, admin_meal4, admin_meal5, admin_meal6, admin_meal7
    );

    /*static {
        int counter = 0;
        ALL_MEALS = Arrays.asList(
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                        "Еда 1 USER", 1000),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                        "Еда 2 USER", 700),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 30, 18, 0),
                        "Еда 3 USER", 299),

                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                        "Еда 4 USER", 700),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                        "Еда 5 USER", 700),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 31, 18, 0),
                        "Еда 6 USER", 400),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 31, 23, 59, 59),
                        "Еда 7 USER", 400),

                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0),
                        "Еда 1 ADMIN", 1000),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 29, 13, 0),
                        "Еда 2 ADMIN", 899),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 29, 13, 0, 1),
                        "Еда 3 ADMIN", 100),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 29, 18, 0),
                        "Еда 4 ADMIN", 1),

                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 28, 10, 0),
                        "Еда 5 ADMIN", 1000),
                new Meal(ALL_MEALS_ID_START + counter++,
                        LocalDateTime.of(2020, Month.JANUARY, 28, 13, 0),
                        "Еда 6 ADMIN", 1000),
                new Meal(ALL_MEALS_ID_START + counter,
                        LocalDateTime.of(2020, Month.JANUARY, 28, 18, 0),
                        "Еда 7 ADMIN", 1)
        );

        EXISTED_USER_MEAL = ALL_MEALS.get(0);
    }*/

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2021, Month.JANUARY, 29, 18, 0),
                "Новая еда", 100);
    }

    public static Meal getUpdated(Meal meal) {
        Meal updated = new Meal(meal);
        updated.setCalories(123);
        updated.setDateTime(LocalDateTime.of(2021, Month.JANUARY, 29, 18, 0));
        updated.setDescription("Обновленная еда");
        return updated;
    }

}