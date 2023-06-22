package com.sahno.controller.mvc;

import com.sahno.data.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frame")
public class FrameController {
    @GetMapping("/{frame}")
    public String get(@PathVariable String frame) {
        return Constants.FRAME_PATH + frame;
    }
}
