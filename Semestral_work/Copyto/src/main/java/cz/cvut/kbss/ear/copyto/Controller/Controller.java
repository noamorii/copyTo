package cz.cvut.kbss.ear.copyto.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping
    @RequestMapping("/")
    String getIndex(Model model){
        model.addAttribute("index", "dsafadf");
        model.addAttribute("val", )
        return "index";
    }
}
