package school.thoughtworks.pos.mapper;

import school.thoughtworks.pos.bean.Item;

import java.util.List;
import java.util.Map;

public interface ItemMapper {
    List<Item> findAll();

    List<Item> findById(int id);

    Integer insertItem(Item item);

    Integer updateItemById(Item item);

    Integer deleteItemById(Integer id);


}