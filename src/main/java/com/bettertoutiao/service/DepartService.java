package com.bettertoutiao.service;

import com.bettertoutiao.dao.DepartDAO;
import com.bettertoutiao.model.Depart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuyang on 17/2/3.
 */
@Service
public class DepartService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DepartDAO departDAO;

    public List<Depart> getDeparts(){
        return departDAO.getAllDeparts();
    }
}
