package com.bettertoutiao.controller;

import com.bettertoutiao.model.Depart;
import com.bettertoutiao.model.News;
import com.bettertoutiao.service.DepartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by liuyang on 17/2/14.
 */
@Controller
public class DepartController {
    private static final Logger logger = LoggerFactory.getLogger(DepartController.class);

    @Autowired
    DepartService departService;

    @RequestMapping(path = {"/depart"})
    public String depart(Model model,@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                         @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        int pre = page > 1 ? page - 1 : 1;
        int next = page + 1;
        List<Depart> departs = departService.getDeparts();
        model.addAttribute("departs", departs);
        model.addAttribute("pre", pre);
        model.addAttribute("cur", page);
        model.addAttribute("next", next);
        return "depart";
    }
}
