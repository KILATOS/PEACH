package org.peach.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/styles")
public class CssController {
    @RequestMapping("/styleForUser.css")
    public String getStyleForUsers(){
        return "styles/styleForUser";
    }
    @RequestMapping("/styleForBooks.css")
    public String getStyleForBooks(){return "styles/styleForBooks";}

}
