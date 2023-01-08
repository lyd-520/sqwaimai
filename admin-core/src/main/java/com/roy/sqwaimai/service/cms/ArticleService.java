package com.roy.sqwaimai.service.cms;

import com.roy.sqwaimai.bean.entity.cms.Article;
import com.roy.sqwaimai.dao.cms.ArticleRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends BaseService<Article, Long, ArticleRepository> {
}
