package com.roy.sqwaimai.dao.message;

import com.roy.sqwaimai.bean.entity.message.Message;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.ArrayList;


public interface MessageRepository extends BaseRepository<Message,Long> {
    void deleteAllByIdIn(ArrayList<String> list);
}

