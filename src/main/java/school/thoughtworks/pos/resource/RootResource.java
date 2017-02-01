package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.ItemMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/")
public class RootResource {

    @Inject
    private ItemMapper itemMapper;

    @Inject
    private SqlSession sqlSession;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRootInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("items", "/items");

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        Map<String, Object> result = new HashMap<>();

        List<Item> originItems = itemMapper.findAll();

        List<Map> items = originItems
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        result.put("items", items);
        result.put("totalCount", items.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/items/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsById(
            @PathParam("id") int id) {
        Map<String, Object> result = new HashMap<>();

        List<Item> originItems = itemMapper.findById(id);

        List<Map> items = originItems
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        result.put("items", items);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertItems(Map data) {
        Integer price = (Integer) data.get("price");
        String name = (String) data.get("name");

        Item item = new Item();
        item.setPrice(price);
        item.setName(name);

        itemMapper.insertItem(item);
        sqlSession.commit();

        Map result = new HashMap();

        result.put("itemUri", "items/" + item.getId());
        return Response.status(Response.Status.CREATED).entity(result).build();

    }

    @PUT
    @Path("/items/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItems(
            @PathParam("id") int id,
            Map data) {
        Integer price = (Integer) data.get("price");
        String name = (String) data.get("name");

        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setPrice(price);

        itemMapper.updateItemById(item);
        sqlSession.commit();

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/items/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemById(
            @PathParam("id") Integer id) {
        itemMapper.deleteItemById(id);
        sqlSession.commit();
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
