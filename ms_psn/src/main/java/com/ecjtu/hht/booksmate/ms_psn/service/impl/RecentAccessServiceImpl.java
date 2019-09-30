package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.common.util.DateUtil;
import com.ecjtu.hht.booksmate.ms_psn.entity.RecentAccess;
import com.ecjtu.hht.booksmate.ms_psn.mapper.RecentAccessMapper;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import com.ecjtu.hht.booksmate.ms_psn.service.IRecentAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@Service
public class RecentAccessServiceImpl extends ServiceImpl<RecentAccessMapper, RecentAccess> implements IRecentAccessService {

    @Autowired
    private RecentAccessMapper recentAccessMapper;
    @Autowired
    private IPersonService personService;

    /**
     * 获取最近访客记录
     *
     * @param psnId
     * @return
     */
    @Override
    public List<Map<String, Object>> getRecentAccess(Integer psnId) {
        List<Map<String, Object>> recentList = new ArrayList<Map<String, Object>>();
        List<RecentAccess> recentAccesses = recentAccessMapper.getRecentAccess(psnId);
        recentAccesses.forEach(recent -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", recent.getId());
            map.put("visitorId", recent.getVisitorId());
            map.put("timedesc", DateUtil.getTimeFormatText(recent.getVisitTime()));
            Person person = personService.selectById(recent.getVisitorId());
            map.put("avatar", person.getAvatar());
            map.put("visitorName", person.getPsnName());
            map.put("hobbyBook", person.getHobbyBooks());
            map.put("visitordepartment", person.getDepartment());
            recentList.add(map);
        });
        return recentList;
    }

    /**
     * @param frdId 被访问的人id
     * @param id    访问人id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrupdateRecent(String frdId, Integer id) {
        RecentAccess recentAccess = new RecentAccess(Integer.parseInt(frdId), id);
        RecentAccess access = recentAccessMapper.selectOne(recentAccess);
        //如果存在表里 那么更新时间
        Date date = new Date();
        if (access != null) {
            access.setVisitTime(date);
            recentAccessMapper.updateById(access);
        } else {
            recentAccess.setVisitTime(date);
            recentAccessMapper.insert(recentAccess);
        }
    }
}
