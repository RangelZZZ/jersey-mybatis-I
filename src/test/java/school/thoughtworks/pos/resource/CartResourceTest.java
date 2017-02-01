package school.thoughtworks.pos.resource;


import org.apache.ibatis.session.SqlSession;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;
import school.thoughtworks.pos.App;
import school.thoughtworks.pos.bean.Cart;
import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.mapper.CartMapper;
import school.thoughtworks.pos.mapper.CategoryMapper;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class CartResourceTest extends JerseyTest {
    Cart mockCart = mock(Cart.class);

    @Override
    protected Application configure() {
        enable(TestProperties.DUMP_ENTITY);

        SqlSession session = App.getSession();
        CartMapper cartMapper = session.getMapper(CartMapper.class);

        return new ResourceConfig().register(new AbstractBinder() {

            @Override
            protected void configure() {

                bind(cartMapper).to(CartMapper.class);
                bind(session).to(SqlSession.class);
            }
        }).packages("school.thoughtworks.pos.resource");
    }

    @Test
    public void root_should_get_all_carts() throws Exception {
        Response response = target("/cart").request().get();
        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List<Cart> items = (List<Cart>) result.get("carts");
        assertThat(items.size(), is(5));
    }

    @Test
    public void should_return_cart_by_id() throws Exception {

        Response response = target("/cart/4").request().get();
        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List items = (List) result.get("carts");
        Map item = (Map) items.get(0);
        assertThat(item.get("id"), is(4));
        assertThat(item.get("userId"), is(2));
        assertThat(item.get("cartUri"), is("cart/4"));
    }

    @Test
    public void should_insert_carts() throws Exception {
        Map data = new HashMap();
        data.put("userId", 6);

        Response response = target("/cart").request().post(
                Entity.entity(data, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertThat(response.getStatus(), is(201));

        Map map = response.readEntity(Map.class);
        Assert.assertThat(map.get("cartUri"), is("cart/8"));

    }

    @Test
    public void should_update_carts() throws Exception {
        Map cart = new HashMap();
        cart.put("userId", 5);
        cart.put("id", 5);

        Response response = target("/cart/5").request().put(
                Entity.entity(cart, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertThat(response.getStatus(), is(204));
    }

    @Test
    public void should_delete_items() throws Exception {

        Response response = target("/cart/3").request().delete();
        Assert.assertThat(response.getStatus(), is(204));
    }

    @Test
    public void should_return_items_by_cart_id() throws Exception {

        Response response = target("/cart/4/items").request().get();
        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List items = (List) result.get("carts");
        Map item = (Map) items.get(0);
        assertThat(item.get("id"), is(4));
        assertThat(item.get("userId"), is(2));
        assertThat(item.get("cartUri"), is("cart/4"));
    }

    @Test
    public void should_insert_item_cart() throws Exception {

        Map itemCart = new HashMap();
        itemCart.put("itemId", 3);
        itemCart.put("cartId", 6);

        Response response = target("/cart/6/items/3").request().post(
                Entity.entity(itemCart, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertThat(response.getStatus(), is(201));
    }

    @Test
    public void should_delete_item_cart() throws Exception {

        Response response = target("/cart/6/items/3").request().delete();
        Assert.assertThat(response.getStatus(), is(204));
    }


}
