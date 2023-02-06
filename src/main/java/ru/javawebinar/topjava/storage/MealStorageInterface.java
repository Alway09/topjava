package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealStorageInterface{

    Meal create(Meal meal);

    Meal get(Integer id);

    void update(Meal meal);

    void delete(Integer id);

    void clear();

    List<Meal> getAll();

    int size();
}
