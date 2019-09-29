package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.entity.Followers;
import com.ecjtu.hht.booksmate.ms_psn.entity.Notice;
import com.ecjtu.hht.booksmate.ms_psn.mapper.FollowersMapper;
import com.ecjtu.hht.booksmate.ms_psn.mapper.NoticeMapper;
import com.ecjtu.hht.booksmate.ms_psn.service.IFollowersService;
import com.ecjtu.hht.booksmate.ms_psn.service.INoticeService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@Service
public class FollowersServiceImpl extends ServiceImpl<FollowersMapper, Followers> implements IFollowersService {

    @Autowired
    private FollowersMapper followersMapper;
    @Autowired
    private IPersonService personService;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private INoticeService noticeService;

    /**
     * 根据个人id和好友ID判断是不是关注关系
     *
     * @param psnId
     * @param frdId
     * @return
     */
    @Override
    public boolean checkIsFollow(String psnId, String frdId) {
        Followers follower = followersMapper.selectByPsnIdAndFrdId(psnId, frdId);
        if (follower != null && follower.getFollowStatus() == 1) {//1代表关注
            return true;
        }
        return false;
    }

    /**
     * 显示所有的关注列表
     *
     * @param psnId
     * @return
     */
    @Override
    public List<Person> showAllFollowers(Integer psnId) {
        List<Person> personList = new ArrayList<>();
        List<Integer> followerIds = followersMapper.getAllFollowersId(psnId);
        followerIds.forEach(followerId -> {
            Person person = personService.selectById(followerId);
            personList.add(person);
        });
        return personList;
    }

    /**
     * 关注好友
     *
     * @param followers
     * @return
     */
    @Transactional
    @Override
    public BookResult followPsn(Followers followers) {
        Followers tempFollow = followersMapper.selectOne(followers);
        //如果不为空 则更新
        Date date = new Date();
        Integer isFollow = null;
        if (tempFollow != null) {
            tempFollow.setFollowTime(date);
            tempFollow.setFollowStatus(1);
            isFollow = followersMapper.updateById(tempFollow);

        } else {
            followers.setFollowStatus(1);
            followers.setFollowTime(date);
            isFollow = followersMapper.insert(followers);
        }
        //关注别人时 发送通知
        Notice notice = new Notice();
        notice.setType(2);
        notice.setSenderPsn(followers.getPsnId());
        notice.setReceivePsn(followers.getFollowerId());
        Notice notice1 = noticeMapper.selectOne(notice);
        //如果不为空代表曾经关注过 只需要修改状态
        if (notice1 != null) {
            notice1.setStatus(1);
            notice1.setUpdateDate(date);
            noticeMapper.updateById(notice1);
        } else {
            notice.setCreateDate(date);
            notice.setStatus(1);
            notice.setUpdateDate(date);
            noticeMapper.insert(notice);
        }
        return isFollow > 0 ? BookResult.build(200, "关注成功") : BookResult.build(400, "关注失败");
    }

    /**
     * 获取所有的粉丝
     *
     * @param psnId
     * @return
     */
    @Override
    public List<Person> showAllFollowering(Integer psnId) {
        List<Person> followeringList = new ArrayList<>();
        List<Integer> followeringIds = followersMapper.getAllFolloweringId(psnId);
        followeringIds.forEach(followeringId -> {
            Person person = personService.selectById(followeringId);
            followeringList.add(person);
        });
        return followeringList;
    }

    /**
     * 取消关注
     *
     * @param followers
     * @return
     */
    @Transactional
    @Override
    public BookResult cancleFollow(Followers followers) {
        followers.setFollowStatus(0);
        followersMapper.updateFollowerStatus(followers);
        return BookResult.build(200, "取消关注成功");
    }

    @Override
    @Transactional
    public BookResult followPsnEach(Followers followers, Integer noticeId) {
        //先进行正常的关注操作 在修改通知状态
        BookResult bookResult = this.followPsn(followers);
        //修改通知状态
        noticeService.ignoreNotice(noticeId);
        return bookResult;
    }

    /**
     * 获取粉丝数量
     *
     * @param psnId
     * @return
     */
    @Override
    public int getCountByFollowId(Integer psnId, Integer followerStatus) {
        return followersMapper.getCountByFollowId(psnId, followerStatus);
    }

    /**
     * 获取关注数量
     *
     * @param psnId
     * @param followerStatus
     * @return
     */
    @Override
    public int getCountByPsnId(Integer psnId, Integer followerStatus) {
        return followersMapper.getCountByPsnId(psnId, followerStatus);
    }

    /**
     * 判断是否是好友
     *
     * @param createPsn
     * @param psnId
     * @return
     */
    @Override
    public boolean checkIsFriend(Integer createPsn, Integer psnId) {
        List<Followers> followers = followersMapper.checkIsFriend(createPsn, psnId);
        return followers == null ? false : true;
    }

    /**
     * 获取我的所有关注的psnId
     *
     * @param psnId
     * @return
     */
    @Override
    public List<Integer> getFollowersIds(Integer psnId) {
        return followersMapper.getAllFollowersId(psnId);
    }
}
