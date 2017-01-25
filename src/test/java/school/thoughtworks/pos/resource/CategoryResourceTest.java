package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import school.thoughtworks.pos.App;
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
}