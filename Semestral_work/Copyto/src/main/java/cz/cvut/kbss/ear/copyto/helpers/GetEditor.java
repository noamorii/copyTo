package cz.cvut.kbss.ear.copyto.helpers;

import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;

import java.util.ArrayList;
import java.util.List;

public class GetEditor {

    public List<OrderContainer> setFakeCategories(List<OrderContainer> containers){
        for(OrderContainer container : containers){
            List<Category> categories = container.getOrder().getCategories();
            List<Category> fakeCategories = new ArrayList<>();
            for(Category toChange : categories){
                Category fakeCategory = new Category();
                fakeCategory.setId(toChange.getId());
                fakeCategory.setName(toChange.getName());
                fakeCategories.add(fakeCategory);
            }
            container.getOrder().setCategories(fakeCategories);
        }
        return containers;
    }

}
