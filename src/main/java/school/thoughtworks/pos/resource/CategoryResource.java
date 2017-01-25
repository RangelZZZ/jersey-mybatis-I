package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.mapper.CategoryMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/category")
public class CategoryResource {

    @Inject
    private CategoryMapper categoryMapper;

    @Inject
    private SqlSession sqlSession;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<Category> categories = categoryMapper.getAllCategories();

        List<Map> items = categories
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());
        Map result = new HashMap();

        result.put("categories", items);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(
            @PathParam("id") int id) {
        List<Category> categories = categoryMapper.getCategoryById(id);

        List<Map> items = categories
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());
        Map result = new HashMap();

        result.put("categories", items);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCategory(Map data) {

        String name = (String) data.get("name");
        Category category = new Category();
        category.setName(name);

        categoryMapper.insertCategory(category);
        sqlSession.commit();

        Map result = new HashMap();
        result.put("categoryUri", "category/" + category.getId());
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItems(
            @PathParam("id") int id,
            Map data) {
        String name = (String) data.get("name");

        Category category = new Category();
        category.setName(name);
        category.setId(id);

        categoryMapper.updateCategoryById(category);
        sqlSession.commit();

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemById(
            @PathParam("id") Integer id) {
        categoryMapper.deleteCategoryById(id);
        sqlSession.commit();
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}


