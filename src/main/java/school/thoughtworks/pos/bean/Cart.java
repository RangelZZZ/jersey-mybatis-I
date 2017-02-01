package school.thoughtworks.pos.bean;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int id;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();

        result.put("id", getId());
        result.put("userId", getUserId());
        result.put("cartUri", "cart/" + getId());

        return result;
    }

}
