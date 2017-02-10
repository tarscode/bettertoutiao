package com.bettertoutiao.service;

import com.bettertoutiao.dao.NewsDAO;
import com.bettertoutiao.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuyang on 17/1/1.
 */
@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getNews(int page, int size) {
        logger.info("page:" + page + " size" + size);
        List<News> list;
        page = page * (size - 1);
        list = newsDAO.selectByPage(page, size);
        return list;
    }

    public News getNews(int id) {
        News news;
        news = newsDAO.selectById(id);
        return news;
    }

    public List<News> getNews(int depart, int page, int size) {
        logger.info("depart:" + depart + " page:" + page + " size" + size);
        List<News> list;
        page = page * (size - 1);
        list = newsDAO.selectByDepart(depart, page, size);
        return list;
    }
}
