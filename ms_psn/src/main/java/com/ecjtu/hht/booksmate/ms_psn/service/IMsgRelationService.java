package com.ecjtu.hht.booksmate.ms_psn.service;

import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.ms_psn.entity.MsgRelation;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-04-23
 */
public interface IMsgRelationService extends IService<MsgRelation> {

    /**
     * 发送消息给好友
     * @param frdId
     * @param psnId
     */
    void sendMessageToFrd(Integer frdId, Integer psnId);

    /**
     * 获取所有的聊天对象
     * @param psnId
     * @return
     */
    List<Map<String, Object>> getAllChatPsnList(Integer psnId);

    void sendMessageToFrd(MsgRelation tempRelation);
}
