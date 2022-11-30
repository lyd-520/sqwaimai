
package com.roy.sqwaimai.dao.cms;


import com.roy.sqwaimai.bean.entity.cms.Article;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.List;

public interface ArticleRepository extends BaseRepository<Article,Long> {
    /**
     * 查询指定栏目下所有文章列表
     * @param idChannel
     * @return
     */
    List<Article> findAllByIdChannel(Long idChannel);
}
