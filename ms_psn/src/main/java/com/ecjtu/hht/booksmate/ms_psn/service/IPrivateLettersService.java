package com.ecjtu.hht.booksmate.ms_psn.service;

import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.ms_psn.entity.PrivateLetters;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface IPrivateLettersService extends IService<PrivateLetters> {

    /**
     * 获取本人与好友的聊天记录
     *
     * @param psnId
     * @param frdId
     * @return
     */
    List<PrivateLetters> getMsgListByFrdId(Integer psnId, Integer frdId);


    /**
     * 发送消息给好友
     *
     * @param privateLetters
     */
    void sendMessage(PrivateLetters privateLetters);
}
