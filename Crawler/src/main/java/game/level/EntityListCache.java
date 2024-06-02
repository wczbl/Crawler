package game.level;

import game.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityListCache {

    public static List<Set<Entity>> cacheMap = new ArrayList<>();
    public static int c;

    public static Set<Entity> get() {
        if (c == cacheMap.size()) cacheMap.add(new HashSet<>());
        Set<Entity> result = cacheMap.get(c++);
        result.clear();
        return result;
    }

    public static void reset() { c = 0; }
}