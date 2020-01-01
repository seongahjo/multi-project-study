package sajo.study.common.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import sajo.study.common.web.service.ChatRoomService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/room/{id}")
    public String main(@PathVariable Long id, Model model) {
        model.addAttribute("room", chatRoomService.findOne(id));
        return "index";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void badRequest() {
        // Not Implemented Yet
    }
}
