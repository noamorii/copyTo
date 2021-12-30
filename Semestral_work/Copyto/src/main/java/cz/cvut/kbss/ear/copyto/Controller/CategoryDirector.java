package cz.cvut.kbss.ear.copyto.Controller;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.service.CategoryService;
import cz.cvut.kbss.ear.copyto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryDirector {

    private final CategoryService categoryService;
    private final OrderService orderService;

    @Autowired
    public CategoryDirector(CategoryService categoryService, OrderService orderService){
        this.categoryService = categoryService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getById(Model model, @PathVariable Integer id) {
        final Category category = categoryService.findCategory(id);
        System.out.println("category is: " + category);
        model.addAttribute("login", category);
        if (category == null) {
            throw NotFoundException.create("Category", id);
        }
        return "index";
    }
}
