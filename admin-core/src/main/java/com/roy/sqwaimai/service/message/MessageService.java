package com.roy.sqwaimai.service.message;

import com.roy.sqwaimai.bean.entity.message.Message;
import com.roy.sqwaimai.dao.message.MessageRepository;
import com.roy.sqwaimai.dao.message.MessagesenderRepository;
import com.roy.sqwaimai.dao.message.MessagetemplateRepository;
import com.roy.sqwaimai.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends BaseService<Message,Long, MessageRepository> {
}

