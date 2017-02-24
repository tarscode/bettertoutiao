package com.bettertoutiao.controller;

import com.bettertoutiao.async.EventModel;
import com.bettertoutiao.async.EventProducer;
import com.bettertoutiao.async.EventType;
import com.bettertoutiao.model.Comment;
import com.bettertoutiao.model.EntityType;
import com.bettertoutiao.model.HostHolder;
import com.bettertoutiao.service.CommentService;
import com.bettertoutiao.service.LikeService;
import com.bettertoutiao.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuyang on 17/2/10.
 */
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/bettertoutiao/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJsonString(999);
        }

        Comment comment = commentService.getCommentById(commentId);

        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                .setExt("questionId", String.valueOf(comment.getEntityId())));

        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return JsonUtil.getJsonString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/bettertoutiao/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJsonString(999);
        }

        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return JsonUtil.getJsonString(0, String.valueOf(likeCount));
    }
}
