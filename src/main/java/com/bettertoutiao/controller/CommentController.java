package com.bettertoutiao.controller;

import com.bettertoutiao.model.Comment;
import com.bettertoutiao.model.EntityType;
import com.bettertoutiao.model.HostHolder;
import com.bettertoutiao.service.CommentService;
import com.bettertoutiao.service.SensitiveService;
import com.bettertoutiao.service.UserService;
import com.bettertoutiao.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * Created by liuyang on 17/2/8.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    SensitiveService sensitiveService;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
            //去掉html标签
            content = HtmlUtils.htmlEscape(content);
            // 过滤content
            content = sensitiveService.filter(content);
            Comment comment = new Comment();
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(CommonUtil.ANONYMOUS_USERID);
            }
            comment.setContent(content);
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreateDate(new Date());
            comment.setStatus(0);
            commentService.addComment(comment);
            // 更新题目里的评论数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            // 怎么异步化,Ajax
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/news/" + String.valueOf(newsId);
    }
}
