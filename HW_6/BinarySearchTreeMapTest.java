package hw6.tests;


import hw6.BinarySearchTreeMap;
import hw6.Map;

public class BinarySearchTreeMapTest extends OrderedMapTest {

    @Override
    protected Map<String, String> createMap() {
        return new BinarySearchTreeMap<>();
    }
}
