package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.util.JacksonUtils;
import com.ecjtu.hht.booksmate.ms_psn.entity.MsgRelation;
import com.ecjtu.hht.booksmate.ms_psn.entity.Notice;
import com.ecjtu.hht.booksmate.ms_psn.entity.PrivateLetters;
import com.ecjtu.hht.booksmate.ms_psn.mapper.MsgRelationMapper;
import com.ecjtu.hht.booksmate.ms_psn.mapper.NoticeMapper;
import com.ecjtu.hht.booksmate.ms_psn.mapper.PrivateLettersMapper;
import com.ecjtu.hht.booksmate.ms_psn.service.IMsgRelationService;
import com.ecjtu.hht.booksmate.ms_psn.service.IPrivateLettersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@Service
public class PrivateLettersServiceImpl extends ServiceImpl<PrivateLettersMapper, PrivateLetters> implements IPrivateLettersService {

    @Autowired
    private PrivateLettersMapper privateLettersMapper;
    @Autowired
    private IMsgRelationService msgRelationService;
    @Autowired
    private MsgRelationMapper relationMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 获取本人与好友的聊天记录
     *
     * @param psnId
     * @param frdId
     * @return
     */
    @Override
    public List<PrivateLetters> getMsgListByFrdId(Integer psnId, Integer frdId) {
        //SELECT * from private_letters t where  (t.receive_psn_id=1 and t.sender_psn_id=2) or (t.receive_psn_id=2 and t.sender_psn_id=1)
        //ORDER BY t.send_date;
        return privateLettersMapper.getMsgListByFrdId(psnId, frdId);
    }

    /**
     * 发送消息给好友
     *
     * @param privateLetters
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(PrivateLetters privateLetters) {
        Date updateTime = new Date();
        //0未读
        privateLetters.setStatus(0);
        privateLetters.setSendDate(new Date());
        //保存消息记录
        privateLettersMapper.insert(privateLetters);
        //更新最后一条消息
        MsgRelation tempRelation = new MsgRelation();
        tempRelation.setSenderId(privateLetters.getSenderPsnId());
        tempRelation.setReceiveId(privateLetters.getReceivePsnId());
        MsgRelation msgRelation = relationMapper.selectOne(tempRelation);
        String content = privateLetters.getContent();
        if (msgRelation != null) {
            msgRelation.setUpdateTime(updateTime);
            msgRelation.setLastContent(content);
            relationMapper.updateById(msgRelation);
        } else {
            tempRelation.setUpdateTime(updateTime);
            tempRelation.setStatus(0);
            tempRelation.setLastContent(content);
            relationMapper.insert(tempRelation);
        }
        //发送通知
        Notice notice = new Notice();
        notice.setType(1);
        notice.setSenderPsn(privateLetters.getSenderPsnId());
        notice.setReceivePsn(privateLetters.getReceivePsnId());
        Notice notice1 = noticeMapper.selectOne(notice);
        Map<String, Object> paramsMap = new HashMap<>();
        if (content.length() > 10) {
            content = content.substring(0, 9);
        }
        paramsMap.put("content", content);
        String msg = JacksonUtils.mapToJsonStr(paramsMap);
        //如果不为空代表曾经发送过 只需要修改状态
        if (notice1 != null) {
            notice1.setStatus(1);
            notice1.setMsg(msg);
            notice1.setUpdateDate(updateTime);
            noticeMapper.updateById(notice1);
        } else {
            notice.setCreateDate(updateTime);
            notice.setStatus(1);
            notice.setMsg(msg);
            notice.setUpdateDate(updateTime);
            noticeMapper.insert(notice);
        }
    }


}
