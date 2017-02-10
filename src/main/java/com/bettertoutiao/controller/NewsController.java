package com.bettertoutiao.controller;


import com.bettertoutiao.model.Comment;
import com.bettertoutiao.model.EntityType;
import com.bettertoutiao.model.HostHolder;
import com.bettertoutiao.model.News;
import com.bettertoutiao.service.CommentService;
import com.bettertoutiao.service.DepartService;
import com.bettertoutiao.service.NewsService;
import com.bettertoutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bettertoutiao.model.ViewObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyang on 17/2/9.
 */
@Controller
public class NewsController {

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

    @RequestMapping(path = {"/news/{nid}"})
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
}
