package com.koreait.ap;

import com.koreait.ap.model.SearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApController {

    @Autowired private ApService service;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("locationList", service.selLocationList());
        return "main";
    }

    @PostMapping("/result")
    public void result(Model model, SearchDto dto) {
        model.addAttribute("list", service.getData(dto));
    }

//    @GetMapping("/result")
//    public String result(Model model, SearchDto dto) {
//        model.addAttribute("list", service.getData(dto));
//        return "redirect:/result";
//    }
}
