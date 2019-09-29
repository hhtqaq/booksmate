package com.ecjtu.hht.booksmate.ms_psn.service;

import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.ms_psn.entity.RecentAccess;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface IRecentAccessService extends IService<RecentAccess> {

    /**
     * 获取最近访客记录
     * @param psnId
     * @return
     */
    List<Map<String, Object>> getRecentAccess(Integer psnId);

    /**
     *
     * @param frdId   被访问的人id
     * @param id      访问人id
     */
    void saveOrupdateRecent(String frdId, Integer id);
}
