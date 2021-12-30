package cz.cvut.kbss.ear.copyto.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.kbss.ear.copyto.dao.Generator;
import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.rest.handler.ErrorInfo;
import cz.cvut.kbss.ear.copyto.service.CategoryService;
import cz.cvut.kbss.ear.copyto.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoryRestControllerTest extends BaseControllerTestRunner {

    @Mock
    private CategoryService categoryServiceMock;

    @Mock
    private OrderService orderServiceMock;

    @InjectMocks
    private CategoryController sut;

    @BeforeEach
    public void setUp() {
        super.setUp(sut);
    }

    @Test
    public void getAllReturnsCategoriesReadByCategoryService() throws Exception {
        final List<Category> categories = IntStream.range(0, 5).mapToObj(i -> {
            final Category cat = new Category();
            cat.setName("Category" + i);
            cat.setId(Generator.randomInt());
            return cat;
        }).collect(Collectors.toList());
        when(categoryServiceMock.findCategories()).thenReturn(categories);

        final MvcResult mvcResult = mockMvc.perform(get("/rest/categories")).andReturn();
        final List<Category> result = readValue(mvcResult, new TypeReference<List<Category>>() {
        });
        assertEquals(categories.size(), result.size());
        verify(categoryServiceMock).findCategories();
    }

    @Test
    public void createCategoryCreatesCategoryUsingService() throws Exception {
        final Category toCreate = new Category();
        toCreate.setName("New Category");

        System.out.println("++++++++++++++++++++++++++++++" + (toJson(toCreate)));

        mockMvc.perform(post("/rest/categories").content(toJson(toCreate)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        final ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryServiceMock).createCategory(captor.capture());
        assertEquals(toCreate.getName(), captor.getValue().getName());
    }

    @Test
    public void createCategoryReturnsResponseWithLocationHeader() throws Exception {
        final Category toCreate = new Category();
        toCreate.setName("New Category");
        toCreate.setId(Generator.randomInt());

        final MvcResult mvcResult = mockMvc
                .perform(post("/rest/categories").content(toJson(toCreate)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andReturn();
        verifyLocationEquals("/rest/categories/" + toCreate.getId(), mvcResult);
    }

    @Test
    public void getByIdReturnsMatchingCategory() throws Exception {
        final Category category = new Category();
        category.setId(Generator.randomInt());
        category.setName("category");
        when(categoryServiceMock.findCategory(category.getId())).thenReturn(category);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/categories/" + category.getId())).andReturn();

        final Category result = readValue(mvcResult, Category.class);
        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }
    @Test
    public void getByIdThrowsNotFoundForUnknownCategoryId() throws Exception {
        final int id = 123;
        final MvcResult mvcResult = mockMvc.perform(get("/rest/categories/" + id)).andExpect(status().isNotFound())
                .andReturn();
        final ErrorInfo result = readValue(mvcResult, ErrorInfo.class);
        assertNotNull(result);
        assertThat(result.getMessage(), containsString("Category identified by "));
        assertThat(result.getMessage(), containsString(Integer.toString(id)));
    }

    @Test
    public void getOrdersByCategoryReturnsOrdersForCategory() throws Exception {
        final List<Order> orders = Arrays.asList(Generator.generateOrder(), Generator.generateOrder());
        when(orderServiceMock.findOrders(any())).thenReturn(orders);
        final Category category = new Category();
        category.setName("test");
        category.setId(Generator.randomInt());
        when(categoryServiceMock.findCategory((Integer) any())).thenReturn(category);
        final MvcResult mvcResult = mockMvc.perform(get("/rest/categories/" + category.getId() + "/orders")).andReturn();
        final List<Order> result = readValue(mvcResult, new TypeReference<List<Order>>() {
        });
        assertNotNull(result);
        assertEquals(orders.size(), result.size());
        verify(categoryServiceMock).findCategory(category.getId());
        verify(orderServiceMock).findOrders(category);
    }

    @Test
    public void getProductsByCategoryThrowsNotFoundForUnknownCategoryId() throws Exception {
        final int id = 123;
        mockMvc.perform(get("/rest/categories/" + id + "/orders")).andExpect(status().isNotFound());
        verify(categoryServiceMock).findCategory(id);
        verify(orderServiceMock, never()).findOrders(any());
    }

    //TODO chyba
//    @Test
//    public void addProductToCategoryAddsProductToSpecifiedCategory() throws Exception {
//        final Category category = new Category();
//        category.setName("test");
//        category.setId(Generator.randomInt());
//        when(categoryServiceMock.find(any())).thenReturn(category);
//        final OrderDetail order = Generator.generateOrder();
//        order.setId(Generator.randomInt());
//        mockMvc.perform(post("/rest/categories/" + category.getId() + "/orders").content(toJson(order)).contentType(
//                MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNoContent());
//        final ArgumentCaptor<OrderDetail> captor = ArgumentCaptor.forClass(OrderDetail.class);
//        verify(categoryServiceMock).addOrder(eq(category), captor.capture());
//        assertEquals(order.getId(), captor.getValue().getId());
//    }

    @Test
    public void addProductToCategoryThrowsNotFoundForUnknownCategory() throws Exception {
        final Order product = Generator.generateOrder();
        product.setId(Generator.randomInt());
        final int categoryId = 123;
        mockMvc.perform(post("/rest/categories/" + categoryId + "/orders").content(toJson(product)).contentType(
                MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
        verify(categoryServiceMock).findCategory(categoryId);
        verify(categoryServiceMock, never()).addOrder(any(), any());
    }

    @Test
    public void removeProductRemovesProductFromCategory() throws Exception {
        final Category category = new Category();
        category.setName("test");
        category.setId(Generator.randomInt());
        when(categoryServiceMock.findCategory((Integer) any())).thenReturn(category);
        final Order order = Generator.generateOrder();
        order.setId(Generator.randomInt());
        order.setCategory(category);
        when(orderServiceMock.findOrder(any())).thenReturn(order);
        mockMvc.perform(delete("/rest/categories/" + category.getId() + "/orders/" + order.getId()))
                .andExpect(status().isNoContent());
        verify(categoryServiceMock).removeOrder(category, order);
    }

    @Test
    public void removeProductThrowsNotFoundForUnknownProductId() throws Exception {
        final Category category = new Category();
        category.setName("test");
        category.setId(Generator.randomInt());
        when(categoryServiceMock.findCategory((Integer) any())).thenReturn(category);
        final int unknownId = 123;
        mockMvc.perform(delete("/rest/categories/" + category.getId() + "/orders/" + unknownId))
                .andExpect(status().isNotFound());
        verify(categoryServiceMock).findCategory(category.getId());
        verify(categoryServiceMock, never()).removeOrder(any(), any());
    }
}
