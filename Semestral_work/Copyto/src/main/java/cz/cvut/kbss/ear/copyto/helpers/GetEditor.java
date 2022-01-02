package cz.cvut.kbss.ear.copyto.helpers;

import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;

import java.util.ArrayList;
import java.util.List;

public class GetEditor {

    public List<OrderContainer> setFakeCategories(List<OrderContainer> containers) {
        for (OrderContainer container : containers) {
            List<Category> categories = container.getOrder().getCategories();
            List<Category> fakeCategories = new ArrayList<>();
            for (Category toChange : categories) {
                Category fakeCategory = new Category();
                fakeCategory.setId(toChange.getId());
                fakeCategory.setName(toChange.getName());
                fakeCategories.add(fakeCategory);
            }
            container.getOrder().setCategories(fakeCategories);
        }
        return containers;
    }

    public OrderContainer setFakeCategories(OrderContainer container) {
        List<Category> categories = container.getOrder().getCategories();
        List<Category> fakeCategories = new ArrayList<>();
        for (Category toChange : categories) {
            Category fakeCategory = new Category();
            fakeCategory.setId(toChange.getId());
            fakeCategory.setName(toChange.getName());
            fakeCategories.add(fakeCategory);
        }
        container.getOrder().setCategories(fakeCategories);

        return container;
    }

    public Category setFakeOrders(Category category) {
        List<Order> orders = category.getOrders();
        List<Order> fakeOrders = new ArrayList<>();
        for (Order toChange : orders) {
            Order fakeOrder = new Order();
            fakeOrder.setDeadline(toChange.getDeadline());
            fakeOrder.setLink(toChange.getLink());
            fakeOrder.setState(toChange.getState());
            fakeOrder.setId(toChange.getId());
            fakeOrder.setInsertionDate(toChange.getInsertionDate());
            fakeOrders.add(fakeOrder);
        }
        category.setOrders(fakeOrders);
        return category;
    }

    public List<Category> setFakeOrders(List<Category> categories) {
        for(Category category : categories){
            List<Order> orders = category.getOrders();
            List<Order> fakeOrders = new ArrayList<>();
            for (Order toChange : orders) {
                Order fakeOrder = new Order();
                fakeOrder.setDeadline(toChange.getDeadline());
                fakeOrder.setLink(toChange.getLink());
                fakeOrder.setState(toChange.getState());
                fakeOrder.setId(toChange.getId());
                fakeOrder.setInsertionDate(toChange.getInsertionDate());
                fakeOrders.add(fakeOrder);
            }
            category.setOrders(fakeOrders);
        }
        return categories;
    }

}
