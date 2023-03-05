package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository mealCrudRepository;
    private final CrudUserRepository userCrudRepository;

    public DataJpaMealRepository(CrudMealRepository mealCrudRepository, CrudUserRepository userCrudRepository) {
        this.mealCrudRepository = mealCrudRepository;
        this.userCrudRepository = userCrudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(userCrudRepository.getReferenceById(userId));
        if(meal.isNew()){
            return mealCrudRepository.save(meal);
        }
        return get(meal.id(), userId) == null ? null : mealCrudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealCrudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = mealCrudRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return mealCrudRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return mealCrudRepository.getBetweenHalfOpen(userId, startDateTime, endDateTime);
    }
}
