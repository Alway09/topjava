package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
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
        for (int i = 0; i < 2; ++i) {
            create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30 + i, 10, 0), "Завтрак", 500));
            create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30 + i, 13, 0), "Обед", 1000));
            create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30 + i, 20, 0), "Ужин", 400));
        }
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 101));
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
        mealMap.replace(meal.getId(), meal);
        logToDebug("update", meal.getId());
        return meal;
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