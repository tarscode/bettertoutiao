package com.bettertoutiao.controller;


import com.bettertoutiao.model.*;
import com.bettertoutiao.service.CommentService;
import com.bettertoutiao.service.DepartService;
import com.bettertoutiao.service.NewsService;
import com.bettertoutiao.service.UserService;
import com.bettertoutiao.util.CommonUtil;
import com.bettertoutiao.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyang on 17/2/9.
 */
@Controller
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    DepartService departService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/bettertoutiao/news/{nid}"})
    public String newsDetail(Model model, @PathVariable("nid") int nid) {
        News news = newsService.getNews(nid);
        model.addAttribute("news", news);
        List<Comment> commentList = commentService.getCommentsByEntity(nid, EntityType.ENTITY_NEWS);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments", vos);
        return "detail";
    }

    @RequestMapping(path = {"/bettertoutiao/release"})
    public String release() {
        return "addNews";
    }

    @RequestMapping(path = {"/bettertoutiao/news/addNews"}, method = {RequestMethod.POST})
    public String addNews(@RequestParam(value = "title", defaultValue = "无标题", required = false) String title,
                          @RequestParam(value = "url", defaultValue = "/home", required = false) String url,
                          @RequestParam(value = "content" , defaultValue = "无内容", required = false) String content) {
        try {
            if (hostHolder.getUser() == null) {
                return JsonUtil.getJsonString(999, "未登录");
            }

            User user = hostHolder.getUser();
            News news = new News();
            news.setTitle(title);
            news.setUrl(url);
            news.setContent(content);
            news.setType(CommonUtil.TYPE_USER);
            news.setDepart(user.getId());
            news.setCreatetime(new Date());
            news.setNewsdate(new Date());
            newsService.addNews(news);
            return "redirect:/bettertoutiao/home";
        } catch (Exception e) {
            logger.error("增加站内信失败" + e.getMessage());
            return "redirect:/bettertoutiao/addNews";
        }
    }
}
