package hw6.tests;


import hw6.Map;
import hw6.SimpleMap;

public class SimpleMapTest extends MapTest {

    @Override
    protected Map<String, String> createMap() {
        return new SimpleMap<>();
    }
}
