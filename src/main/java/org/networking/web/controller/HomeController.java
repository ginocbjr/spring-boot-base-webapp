package org.networking.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Gino on 8/28/2015.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping(value = {"/login", "/logout"})
    String login() {
        return "login";
    }
}
