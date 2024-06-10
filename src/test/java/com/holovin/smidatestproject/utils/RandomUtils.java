package com.holovin.smidatestproject.utils;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.User;
import org.jeasy.random.EasyRandom;

public class RandomUtils {

    static EasyRandom generator = new EasyRandom();

    public static Company createCompany() {
        return generator.nextObject(Company.class);
    }

    public static User createUser() {
        User user = generator.nextObject(User.class);
        user.setRoles("USER");
        return user;
    }
}
