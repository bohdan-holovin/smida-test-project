package com.holovin.smidatestproject.utils;

import com.holovin.smidatestproject.model.Company;
import org.jeasy.random.EasyRandom;

public class RandomUtils {

    static EasyRandom generator = new EasyRandom();

    public static Company createCompany() {
        return generator.nextObject(Company.class);
    }
}
