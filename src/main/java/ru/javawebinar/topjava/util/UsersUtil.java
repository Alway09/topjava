package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User("Toyama Tokanawa", "best_racing_team@gmail.com", "185ddfs@FFFD85", Role.ADMIN, Role.USER),
            new User("John Snow", "john_cold@gmail.com", "13abc28F_2", Role.ADMIN),
            new User("Geralt of Rivia", "plotva@gmail.com", "Avvd88569_fd", Role.USER)
    );
}
