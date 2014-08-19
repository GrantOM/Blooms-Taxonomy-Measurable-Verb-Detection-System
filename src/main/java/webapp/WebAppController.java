package webapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebAppController {

    @RequestMapping("/index")
    public String index(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
    
    /*@RequestMapping("/indexTwo")
    public @ResponseBody String indexTwo() {
    	//MyObject mo = new MyObject();
    	//mo.doSomethingTothisObject("Something");
    	return "{\"name\":\"myOjbect\",\"content\":\"This is a dummy JSON object\"}";
    }*/    
}
