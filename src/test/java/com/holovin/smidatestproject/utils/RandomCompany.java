package com.holovin.smidatestproject.utils;

import com.holovin.smidatestproject.model.Company;
import org.jeasy.random.EasyRandom;

public class RandomCompany {

    static EasyRandom generator = new EasyRandom();

    public static Company create() {
        return generator.nextObject(Company.class);
    }
}
