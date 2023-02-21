package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MatcherUtil;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final MatcherUtil MATCHER = new MatcherUtil("");

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MATCHER.assertMatch(created, newMeal);
        MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, user_meal1.getDateTime(),
                        "DataAccessException must be thrown", 2000), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(user_meal4.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(user_meal4.getId(), USER_ID));
    }

    @Test
    public void deleteNotExisted() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_EXISTED_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(user_meal2.getId(), ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(user_meal7.getId(), USER_ID);
        MATCHER.assertMatch(meal, user_meal7);
    }

    @Test
    public void getNotExisted() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTED_MEAL_ID, USER_ID));
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(user_meal6.getId(), ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        MATCHER.assertMatch(getBetweenInclusive(USER_ID), Arrays.asList(user_meal7, user_meal6, user_meal5, user_meal4));
    }

    @Test
    public void getBetweenInclusiveNotExisted() {
        MATCHER.assertMatch(getBetweenInclusive(NOT_FOUND), Collections.emptyList());
    }

    private List<Meal> getBetweenInclusive(int userId) {
        return service.getBetweenInclusive(user_meal4.getDate(), user_meal4.getDate(), userId);
    }

    @Test
    public void getAll() {
        MATCHER.assertMatch(service.getAll(USER_ID), reversedMealList(userMeals));
    }

    @Test
    public void getAllNotExisted() {
        MATCHER.assertMatch(service.getAll(NOT_FOUND), Collections.emptyList());
    }

    @Test
    public void getAllNotForeign() {
        MATCHER.assertMatch(service.getAll(NOT_FOUND), Collections.emptyList());
    }

    private List<Meal> reversedMealList(List<Meal> meals) {
        List<Meal> copy = new ArrayList<>(meals);
        Collections.reverse(copy);
        return copy;
    }

    @Test
    public void update() {
        Meal updated = getUpdated(user_meal5);
        service.update(updated, USER_ID);
        MATCHER.assertMatch(service.get(user_meal5.getId(), USER_ID), getUpdated(user_meal5));
    }

    @Test
    public void updateForeign() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(user_meal4), ADMIN_ID));
    }

    @Test
    public void updateNotExisted() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(user_meal3), NOT_FOUND));
    }
}