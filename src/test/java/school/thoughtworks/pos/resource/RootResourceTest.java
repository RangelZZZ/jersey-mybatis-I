package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;
import school.thoughtworks.pos.App;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.ItemMapper;

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
import static org.mockito.Mockito.when;

public class RootResourceTest extends JerseyTest {

    Item mockItem = mock(Item.class);

    @Override
    protected Application configure() {
        enable(TestProperties.DUMP_ENTITY);

        SqlSession session = App.getSession();
        ItemMapper itemMapper = session.getMapper(ItemMapper.class);

        return new ResourceConfig().register(new AbstractBinder() {

            @Override
            protected void configure() {

                bind(itemMapper).to(ItemMapper.class);
                bind(session).to(SqlSession.class);
            }
        }).packages("school.thoughtworks.pos.resource");
    }

    @Test
    public void root_path_should_return_items_uri() throws Exception {
        Response response = target("/").request().get();
        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        assertThat(result.get("items"), is("/items"));
    }

    @Test
    public void should_return_items_by_id() throws Exception {

        Response response = target("/items/4").request().get();
        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List items = (List) result.get("items");
        Map item = (Map) items.get(0);
        assertThat(item.get("id"), is(4));
        assertThat(item.get("name"), is("banana"));
        assertThat(item.get("price"), is(20.0));

    }

    @Test
    public void should_insert_items() throws Exception {
        Map data = new HashMap();
        data.put("name", "rrrrrr");
        data.put("price", 2);

        Response response = target("/items").request().post(
                Entity.entity(data, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertThat(response.getStatus(), is(201));

        Map map = response.readEntity(Map.class);
        Assert.assertThat(map.get("itemUri"), is("items/6"));

    }

    @Test
    public void should_update_items() throws Exception {
        Map item = new HashMap();
        item.put("name", "eeeee");
        item.put("price", 2);
        item.put("id", 5);

        Response response = target("/items/5").request().put(
                Entity.entity(item, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertThat(response.getStatus(), is(204));
    }

    @Test
    public void should_delete_items() throws Exception {

        Response response = target("/items/3").request().delete();
        Assert.assertThat(response.getStatus(), is(204));
    }


}