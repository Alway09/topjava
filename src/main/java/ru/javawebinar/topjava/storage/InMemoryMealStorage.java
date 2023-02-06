package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealStorage implements MealStorageInterface {
    private static final Logger log = getLogger(InMemoryMealStorage.class);
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private AtomicInteger generatedId = new AtomicInteger(0);

    @Override
    public Meal create(Meal meal){
        try{
            meal.setId(generatedId.incrementAndGet());
            mealMap.putIfAbsent(meal.getId(), meal);
            log.debug("id " + meal.getId() +" create");
            return meal;
        }
        catch (RuntimeException e){
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Meal get(Integer id) {
        log.debug("id " + id + " get");
        return mealMap.get(id);
    }

    @Override
    public void update(Meal meal) {
        try{
            mealMap.replace(meal.getId(), meal);
            log.debug("id " + meal.getId() + " update");
        }
        catch (RuntimeException e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        mealMap.remove(id);
        log.debug("id " + id + " delete");
    }

    @Override
    public void clear() {
        mealMap.clear();
        log.debug("clear");
    }

    @Override
    public List<Meal> getAll() {
        log.debug("get all");
        return new ArrayList<Meal>(mealMap.values());
    }

    @Override
    public int size() {
        log.debug("size");
        return mealMap.size();
    }
}