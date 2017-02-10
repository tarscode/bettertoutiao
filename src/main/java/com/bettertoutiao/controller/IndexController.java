package com.bettertoutiao.controller;

import com.bettertoutiao.model.Depart;
import com.bettertoutiao.model.News;
import com.bettertoutiao.service.DepartService;
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
    @Autowired
    DepartService departService;

    @RequestMapping(path = {"/", "/index"})
    public String index() {
        return "login";
    }

    @RequestMapping(path = {"/detail"})
    public String detail(Model model, @RequestParam(value = "id", defaultValue = "1", required = false) int id) {
        News news = newsService.getNews(id);
        model.addAttribute("news", news);
        return "detail";
    }

    @RequestMapping(path = {"/example/{id}"})
    public String example(@PathVariable("id") int id) {
        return "example" + id;
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

    @RequestMapping(path = {"/home","/depart"})
    public String home(Model model, @RequestParam(value = "id", defaultValue = "1", required = false) int departId,
                         @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                         @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        int pre = page > 1 ? page - 1 : 1;
        int next = page + 1;
        List<News> news = newsService.getNews(departId, page, size);
        List<Depart> departs = departService.getDeparts();
        model.addAttribute("depart", departs);
        model.addAttribute("news", news);
        model.addAttribute("pre", pre);
        model.addAttribute("cur", page);
        model.addAttribute("next", next);
        model.addAttribute("departId",departId);
        return "home";
    }


}
