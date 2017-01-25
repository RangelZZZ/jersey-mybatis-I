package school.thoughtworks.pos.mapper;

import school.thoughtworks.pos.bean.Cart;
import school.thoughtworks.pos.bean.Item;

import java.util.List;

public interface CartMapper {
    List<Cart> getAllCarts();

    List<Cart> getCartByCartId(Integer id);

    Integer insertCart(Cart cart);

    Integer updateCartById(Cart cart);

    Integer deleteCartById(Integer id);

    List<Item> getItemsByCartId(Integer id);

    Integer insertItemCart(Integer itemId, Integer cartId);

    Integer deleteItemCart(Integer itemId,Integer cartId);


}
