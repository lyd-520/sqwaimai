package com.roy.sqwaimai.service.cms;

import com.roy.sqwaimai.bean.entity.cms.Contacts;
import com.roy.sqwaimai.dao.cms.ContactsRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ContactsService extends BaseService<Contacts,Long, ContactsRepository> {
}
