package com.roy.sqwaimai.dao.system;


import com.roy.sqwaimai.bean.entity.system.FileInfo;
import com.roy.sqwaimai.dao.BaseRepository;

public interface FileInfoRepository  extends BaseRepository<FileInfo,Long> {
    FileInfo findByRealFileName(String fileName);
}
