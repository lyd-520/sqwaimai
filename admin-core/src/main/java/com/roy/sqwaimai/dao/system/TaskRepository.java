
package com.roy.sqwaimai.dao.system;

import com.roy.sqwaimai.bean.entity.system.Task;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.List;

public interface TaskRepository extends BaseRepository<Task,Long> {

    long countByNameLike(String name);

    List<Task> findByNameLike(String name);
    List<Task> findAllByDisabled(boolean disable);
}
