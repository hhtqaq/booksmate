package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.ms_psn.entity.PrivateLetters;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface PrivateLettersMapper extends BaseMapper<PrivateLetters> {

    List<PrivateLetters> getMsgListByFrdId(Integer psnId, Integer frdId);
}
