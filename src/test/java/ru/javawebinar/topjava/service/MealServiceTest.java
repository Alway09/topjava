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
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MatcherUtil.*;
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
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, MealTestData.ALL_MEALS.get(0).getDateTime(),
                        "DataAccessException must be thrown", 2000), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(EXISTED_USER_MEAL.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(EXISTED_USER_MEAL.getId(), USER_ID));
    }

    @Test
    public void deleteNotExisted() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_EXISTED_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(EXISTED_USER_MEAL.getId(), ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(EXISTED_USER_MEAL.getId(), USER_ID);
        assertMatch(meal, EXISTED_USER_MEAL);
    }

    @Test
    public void getNotExisted() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTED_MEAL_ID, USER_ID));
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(EXISTED_USER_MEAL.getId(), ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = ALL_MEALS.subList(0, 3);
        Collections.reverse(meals);
        assertMatch(getBetweenInclusive(USER_ID), meals);
    }

    @Test
    public void getBetweenInclusiveNotExisted() {
        assertMatch(getBetweenInclusive(NOT_FOUND), Collections.emptyList());
    }

    private List<Meal> getBetweenInclusive(int userId) {
        return service.getBetweenInclusive(EXISTED_USER_MEAL.getDate(), EXISTED_USER_MEAL.getDate(), userId);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), reversedMealList(ALL_MEALS.subList(0, 7)));
    }

    private List<Meal> reversedMealList(List<Meal> meals) {
        Collections.reverse(meals);
        return meals;
    }

    @Test
    public void getAllNotExisted() {
        assertMatch(service.getAll(NOT_FOUND), Collections.emptyList());
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(EXISTED_USER_MEAL.getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateForeign() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
    }

    @Test
    public void updateNotExisted() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), NOT_FOUND));
    }
}