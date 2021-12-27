package cz.cvut.kbss.ear.copyto.environment;

import cz.cvut.kbss.ear.copyto.model.Category;

import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    /*============CATEGORY GENERATOR===============*/

    public static Category generateCategory(String name) {
        final Category cat = new Category();
        cat.setName(name);
        return cat;
    }
}
