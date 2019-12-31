package sajo.study.common.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sajo.study.common.core.dto.ChatRoomDTO;
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

    @PostMapping("/room/{id}")
    public String create(@PathVariable Long id, @RequestBody ChatRoomDTO dto, Model model) {
        model.addAttribute("room", chatRoomService.save(dto));
        return "index";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void badRequest() {
        // Not Implemented Yet
    }
}
