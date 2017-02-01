package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Cart;
import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.CartMapper;
import school.thoughtworks.pos.mapper.CategoryMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/cart")
public class CartResource {
    @Inject
    private CartMapper cartMapper;

    @Inject
    private SqlSession sqlSession;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCarts() {
        List<Cart> carts = cartMapper.getAllCarts();

        List<Map> items = carts
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());
        Map result = new HashMap();

        result.put("carts", items);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(
            @PathParam("id") int id) {
        List<Cart> carts = cartMapper.getCartByCartId(id);

        List<Map> items = carts
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());
        Map result = new HashMap();

        result.put("carts", items);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCategory(Map data) {

        Integer userId = (Integer) data.get("userId");
        Cart cart = new Cart();
        cart.setUserId(userId);

        cartMapper.insertCart(cart);
        sqlSession.commit();

        Map result = new HashMap();
        result.put("cartUri", "cart/" + cart.getId());
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItems(
            @PathParam("id") int id,
            Map data) {
        Integer userId = (Integer) data.get("userId");

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setId(id);

        cartMapper.updateCartById(cart);
        sqlSession.commit();

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemById(
            @PathParam("id") Integer id) {
        cartMapper.deleteCartById(id);
        sqlSession.commit();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByCartId(
            @PathParam("id") Integer id) {
        List<Item> allItems = cartMapper.getItemsByCartId(id);

        List<Map> items = allItems
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        Map result = new HashMap();
        result.put("items", items);
        return Response.status(Response.Status.OK).entity(result).build();

    }

    @POST
    @Path("/{id}/items/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertItemCart(
            @PathParam("id") int cartId,
            @PathParam("itemId") int itemId) {
        System.out.println(cartId);
        System.out.println(itemId);
        cartMapper.insertItemCart(itemId, cartId);
        sqlSession.commit();
        return Response.status(Response.Status.CREATED).build();

    }

    @DELETE
    @Path("/{id}/items/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemCart(
            @PathParam("id") int cartId,
            @PathParam("itemId") int itemId) {

        cartMapper.deleteItemCart(itemId, cartId);
        sqlSession.commit();
        return Response.status(Response.Status.NO_CONTENT).build();

    }

}



