package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.ms_psn.entity.MsgRelation;
import com.ecjtu.hht.booksmate.ms_psn.mapper.MsgRelationMapper;
import com.ecjtu.hht.booksmate.ms_psn.service.IMsgRelationService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-04-23
 */
@Service
public class MsgRelationServiceImpl extends ServiceImpl<MsgRelationMapper, MsgRelation> implements IMsgRelationService {

    @Autowired
    private MsgRelationMapper relationMapper;
    @Autowired
    private IPersonService personService;
    /**
     * 发送消息给好友
     *
     * @param frdId
     * @param psnId
     */
    @Override
    @Transactional
    public void sendMessageToFrd(Integer frdId, Integer psnId) {

    }

    /**
     * 获取所有的聊天对象
     *
     * @param psnId
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllChatPsnList(Integer psnId) {
        List<Map<String, Object>> maps=new ArrayList<>();
        //获取所有的消息列表的人员id
        List<Integer> psnIds=relationMapper.getMsgrelationPsnId(psnId);
        psnIds.forEach(pId -> {
            Map<String, Object> map=new HashMap<>();
            Person person = personService.selectById(pId);
            map.put("person",person);
            maps.add(map);
        });
    return maps;
    }

    @Override
    public void sendMessageToFrd(MsgRelation tempRelation) {
        MsgRelation msgRelation = relationMapper.selectOne(tempRelation);
        if(msgRelation!=null){
            msgRelation.setUpdateTime(new Date());
            relationMapper.updateById(msgRelation);
        }else{
            tempRelation.setUpdateTime(new Date());
            tempRelation.setStatus(0);
            relationMapper.insert(tempRelation);
        }
    }
}
