package sajo.study.common.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sajo.study.common.core.model.ChatRoom;

@Controller
@Slf4j
public class ViewController {
    @GetMapping("/")
    public String main(Model model) {
        log.info("GET /");
        model.addAttribute("room",new ChatRoom("sajo"));
        return "index";
    }
}
