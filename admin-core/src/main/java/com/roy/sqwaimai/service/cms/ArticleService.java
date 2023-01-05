package com.roy.sqwaimai.service.cms;

import com.roy.sqwaimai.bean.entity.cms.Article;
import com.roy.sqwaimai.bean.enumeration.cms.ChannelEnum;
import com.roy.sqwaimai.bean.vo.query.SearchFilter;
import com.roy.sqwaimai.dao.cms.ArticleRepository;
import com.roy.sqwaimai.service.BaseService;
import com.roy.sqwaimai.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService extends BaseService<Article, Long, ArticleRepository> {
}
