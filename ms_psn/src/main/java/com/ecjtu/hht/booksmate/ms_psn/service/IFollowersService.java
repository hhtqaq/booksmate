package com.ecjtu.hht.booksmate.ms_psn.service;

import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.entity.Followers;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface IFollowersService extends IService<Followers> {

    /**
     * 根据个人id和好友ID判断是不是关注关系
     * @param psnId
     * @param frdId
     * @return
     */
    boolean checkIsFollow(String psnId, String frdId);

    /**
     * 显示所有的关注列表
     * @param psnId
     * @return
     */
    List<Person> showAllFollowers(Integer psnId);


    /**
     * 关注好友
     * @param followers
     * @return
     */
    BookResult followPsn(Followers followers);

    /**
     * 获取所有的粉丝
     * @param id
     * @return
     */
    List<Person> showAllFollowering(Integer id);

    /**
     * 取消关注
     * @param followers
     * @return
     */
    BookResult cancleFollow(Followers followers);

    BookResult followPsnEach(Followers followers, Integer noticeId);

    /**
     * 获取粉丝数量
     * @param psnId
     * @return
     */
    int getCountByFollowId(Integer psnId, Integer followerStatus);

    /**
     * 获取关注数量
     * @param psnId
     * @param i
     * @return
     */
    int getCountByPsnId(Integer psnId, Integer followerStatus);

    /**
     * 判断是否是好友
     * @param createPsn
     * @param psnId
     * @return
     */
    boolean checkIsFriend(Integer createPsn, Integer psnId);

    /**
     * 获取我的所有关注的psnId
     * @param psnId
     * @return
     */
    List<Integer> getFollowersIds(Integer psnId);
}
