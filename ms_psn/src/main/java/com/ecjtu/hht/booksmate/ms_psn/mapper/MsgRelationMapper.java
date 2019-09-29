package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.ms_psn.entity.MsgRelation;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-04-23
 */
public interface MsgRelationMapper extends BaseMapper<MsgRelation> {


    List<MsgRelation> getAllMsgRelation(Integer psnId);

    List<Integer> getMsgrelationPsnId(Integer psnId);
}
