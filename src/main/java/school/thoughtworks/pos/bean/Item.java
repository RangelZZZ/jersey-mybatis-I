package school.thoughtworks.pos.bean;

import java.util.HashMap;
import java.util.Map;

public class Item {
    private int id;
    private double price;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();

        result.put("id", getId());
        result.put("name", getName());
        result.put("price", getPrice());

        return result;
    }
}
