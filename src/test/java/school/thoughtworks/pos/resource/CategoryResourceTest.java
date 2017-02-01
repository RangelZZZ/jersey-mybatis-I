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
import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.CategoryMapper;
import school.thoughtworks.pos.mapper.ItemMapper;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Compiler.enable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryResourceTest extends JerseyTest {
    Category mockCategory = mock(Category.class);

    @Override
    protected Application configure() {
        enable(TestProperties.DUMP_ENTITY);

        SqlSession session = App.getSession();
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return new ResourceConfig().register(new AbstractBinder() {

            @Override
            protected void configure() {

                bind(categoryMapper).to(CategoryMapper.class);
                bind(session).to(SqlSession.class);
            }
        }).packages("school.thoughtworks.pos.resource");
    }


    @Test
    public void should_get_all_category() throws Exception {
        Response response = target("/category").request().get();
        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List<Category> items = (List<Category>) result.get("categories");
        assertThat(items.size(), is(2));
    }


    @Test
    public void should_return_category_by_id() throws Exception {

        Response response = target("/category/4").request().get();
        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List items = (List) result.get("categories");
        Map item = (Map) items.get(0);
        assertThat(item.get("id"), is(4));
        assertThat(item.get("name"), is("vegetables"));
        assertThat(item.get("categoryUri"), is("category/4"));
    }

    @Test
    public void should_insert_category() throws Exception {
        Map data = new HashMap();
        data.put("name", "supplies");

        Response response = target("/category").request().post(
                Entity.entity(data, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertThat(response.getStatus(), is(201));

        Map map = response.readEntity(Map.class);
        Assert.assertThat(map.get("categoryUri"), is("category/5"));

    }

    @Test
    public void should_update_category() throws Exception {
        Map category = new HashMap();
        category.put("name", "wash supplies");
        category.put("id", 4);

        Response response = target("/category/4").request().put(
                Entity.entity(category, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertThat(response.getStatus(), is(204));
    }

    @Test
    public void should_delete_category() throws Exception {

        Response response = target("/category/4").request().delete();
        Assert.assertThat(response.getStatus(), is(204));
    }

}
