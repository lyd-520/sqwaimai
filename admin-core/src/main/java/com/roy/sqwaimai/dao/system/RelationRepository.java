package com.roy.sqwaimai.dao.system;

import com.roy.sqwaimai.bean.entity.system.Relation;
import com.roy.sqwaimai.dao.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created  on 2018/3/21 0021.
 *
 *@Author enilu
 */
public interface RelationRepository extends BaseRepository<Relation,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from t_sys_relation where roleid=?1")
    int deleteByRoleId(Long roleId);
}
