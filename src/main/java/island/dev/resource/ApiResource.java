package island.dev.resource;
import island.dev.entity.Wishlist;
import island.dev.repository.*;
import jakarta.inject.Inject;
import jakarta.persistence.Tuple;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiResource {
    @Inject
    WishlistRepository wishlistRepository;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;

    // Return the wishlist of a customer
    @GET
    @Path("/customers/{customerId}/wishlist")
    public Response getWishlist(@PathParam("customerId") Integer customerId) {
        wishlistRepository=new WishlistRepository();
        List<Wishlist> wishlists = wishlistRepository.find("customer.id", customerId).list();
        return Response.ok(wishlists).build();
    }

    // Return the total sale amount of the current day
    @GET
    @Path("/sales/today")
    public Response getTotalSaleAmountOfCurrentDay() {
        orderRepository=new OrderRepository();
        BigDecimal totalSaleAmount = orderRepository.findTotalSaleAmountOfCurrentDay();
        return Response.ok(totalSaleAmount).build();
    }

    // Return the max sale day of a certain time range
    @GET
    @Path("/sales/max")
    public Response getMaxSaleDay(@QueryParam("startDate") String startDateStr,
                                  @QueryParam("endDate") String endDateStr) {
        orderRepository=new OrderRepository();
        LocalDateTime startDate = LocalDateTime.parse(startDateStr);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        List<Tuple> result = orderRepository.findMaxSaleDayOfTimeRange(startDate, endDate);
        Tuple maxSaleDay = result.get(0);
        Instant orderDateInstant = maxSaleDay.get("orderDate", Instant.class);
        LocalDateTime orderDate = orderDateInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Response.ok(result.isEmpty() ? null : orderDate).build();
    }

    // Return top 5 selling items of all time (based on total sale amount)
    @GET
    @Path("/sales/top/all-time")
    public Response getTop5SellingItemsAllTime() {
        orderItemRepository=new OrderItemRepository();
        List<String> topSellingItems = orderItemRepository.findTop5SellingItemsAllTime();
        return Response.ok(topSellingItems).build();
    }

    // Return top 5 selling items of the last month (based on number of sales)
    @GET
    @Path("/sales/top/last-month")
    public Response getTopSellingItemsLastMonth() {
        orderItemRepository=new OrderItemRepository();
        List<String > topSellingItems = orderItemRepository.findTop5SellingItemsLastMonth();
        return Response.ok(topSellingItems).build();
    }
}
