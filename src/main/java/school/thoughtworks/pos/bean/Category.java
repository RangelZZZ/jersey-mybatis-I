package school.thoughtworks.pos.bean;


import java.util.HashMap;
import java.util.Map;

public class Category {
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();

        result.put("id", getId());
        result.put("name", getName());
        result.put("categoryUri", "category/" + getId());

        return result;
    }

}
