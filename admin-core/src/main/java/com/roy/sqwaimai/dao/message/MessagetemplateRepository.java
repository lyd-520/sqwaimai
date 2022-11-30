package com.roy.sqwaimai.dao.message;



import com.roy.sqwaimai.bean.entity.message.MessageTemplate;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.List;


public interface MessagetemplateRepository extends BaseRepository<MessageTemplate,Long> {
    MessageTemplate findByCode(String code);

    List<MessageTemplate> findByIdMessageSender(Long idMessageSender);
}

