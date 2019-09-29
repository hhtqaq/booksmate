package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.ms_psn.entity.RecentAccess;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface RecentAccessMapper extends BaseMapper<RecentAccess> {

    List<RecentAccess> getRecentAccess(Integer psnId);
}
