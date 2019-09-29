package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.ms_psn.entity.Followers;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface FollowersMapper extends BaseMapper<Followers> {

    /**
     * 根据本人ID和还有ID查询
     *
     * @param psnId
     * @param frdId
     * @return
     */
    Followers selectByPsnIdAndFrdId(String psnId, String frdId);

    /**
     * 获取所有的关注人id
     *
     * @param psnId
     * @return
     */
    List<Integer> getAllFollowersId(Integer psnId);

    /**
     * 获取所有的粉丝
     *
     * @param psnId
     * @return
     */
    List<Integer> getAllFolloweringId(Integer psnId);

    void updateFollowerStatus(Followers followers);

    @Select("select count(id) from followers where follower_id=#{psnId} and follow_status=#{followerStatus}")
    int getCountByFollowId(Integer psnId, Integer followerStatus);

    @Select("select count(id) from followers where psn_id=#{psnId} and follow_status=#{followerStatus}")
    int getCountByPsnId(Integer psnId, Integer followerStatus);

    @Select("SELECT * from followers t where (t.psn_id=#{createPsn} and t.follower_id=#{psnId} and t.follow_status=1) or" +
            " (t.psn_id=#{psnId} and t.follower_id=#{createPsn} and t.follow_status=1)")
    List<Followers> checkIsFriend(Integer createPsn, Integer psnId);
}
