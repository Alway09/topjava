package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealStorage implements MealStorage {
    private static final Logger log = getLogger(InMemoryMealStorage.class);
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private final AtomicInteger generatedId = new AtomicInteger(0);

    public InMemoryMealStorage() {
        MealsUtil.getTestMealList().forEach(this::create);
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(generatedId.incrementAndGet());
        mealMap.put(meal.getId(), meal);
        logToDebug("create", meal.getId());
        return meal;
    }

    @Override
    public Meal get(int id) {
        logToDebug("get", id);
        return mealMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        Meal res = mealMap.replace(meal.getId(), meal);
        logToDebug("update", meal.getId());
        return res == null ? null : meal;
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
        logToDebug("delete", id);
    }

    @Override
    public List<Meal> getAll() {
        log.debug("get all meals");
        return new ArrayList<>(mealMap.values());
    }

    private void logToDebug(final String action, int id) {
        log.debug("{} meal with id {}", action, id);
    }
}