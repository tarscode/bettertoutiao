package com.bettertoutiao.controller;

import com.bettertoutiao.model.News;
import com.bettertoutiao.service.NewsService;
import com.bettertoutiao.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuyang on 17/1/1.
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    NewsService newsService;

    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "Hello World";
    }

    @RequestMapping(path = {"/home"})
    public String home(Model model) {
        List<News> news= newsService.getNews(1, 10);
        model.addAttribute("news",news);
        return "home";
    }
    @RequestMapping(path = {"/detail"})
    public String detail() {
        return "detail";
    }

    @RequestMapping(path = {"/example/{id}"})
    public String example(@PathVariable("id") int id) {
        return "example"+id;
    }

    @RequestMapping(path = {"/camel"}, method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String camel(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        logger.info("/camel page:" + page + " size:" + size);
        List<News> list = newsService.getNews(page, size);
        logger.info("news size:" + list.size());
        String json = JsonUtil.getJsonString(0, list);
        return json;
    }
}
