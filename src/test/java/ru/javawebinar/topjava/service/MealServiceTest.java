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
import ru.javawebinar.topjava.AssertMatcher;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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
    private static final AssertMatcher MATCHER = new AssertMatcher();

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
                service.create(new Meal(null, userMeal1.getDateTime(),
                        "DataAccessException must be thrown", 2000), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(userMeal4.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(userMeal4.getId(), USER_ID));
    }

    @Test
    public void deleteNotExisted() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_EXISTED_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(userMeal2.getId(), ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(userMeal7.getId(), USER_ID);
        MATCHER.assertMatch(meal, userMeal7);
    }

    @Test
    public void getNotExisted() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTED_MEAL_ID, USER_ID));
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(userMeal6.getId(), ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        MATCHER.assertMatch(getBetweenInclusive(USER_ID), Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4));
    }

    @Test
    public void getBetweenInclusiveNotExisted() {
        MATCHER.assertMatch(getBetweenInclusive(NOT_FOUND), Collections.emptyList());
    }

    private List<Meal> getBetweenInclusive(int userId) {
        return service.getBetweenInclusive(userMeal4.getDate(), userMeal4.getDate(), userId);
    }

    @Test
    public void getAll() {
        MATCHER.assertMatch(service.getAll(USER_ID), userMeals);
    }

    @Test
    public void getAllNotExisted() {
        MATCHER.assertMatch(service.getAll(NOT_FOUND), Collections.emptyList());
    }

    @Test
    public void getAllForeign() {
        MATCHER.assertMatch(service.getAll(ADMIN_ID), Collections.emptyList());
    }

    @Test
    public void update() {
        Meal updated = getUpdated(userMeal5);
        service.update(updated, USER_ID);
        MATCHER.assertMatch(service.get(userMeal5.getId(), USER_ID), getUpdated(userMeal5));
    }

    @Test
    public void updateForeign() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(userMeal4), ADMIN_ID));
    }

    @Test
    public void updateNotExisted() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(userMeal3), NOT_FOUND));
    }
}