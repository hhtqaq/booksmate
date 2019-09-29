package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ecjtu.hht.booksmate.ms_psn.entity.Dynamic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface DynamicMapper extends BaseMapper<Dynamic> {

    /**
     * 获取个人所有动态
     * @param psnId 创建人id
     * @param status  0删除  1正常
     * @param dynamicPage  分页
     * @return
     */
    List<Dynamic> getAllDynamic(Page<Dynamic> dynamicPage, Integer psnId, Integer status);

    @Update("update dynamic set awardCount=#{count} where id=#{dynId}")
    void updateCount(Integer count, Integer dynId);

    @Select("select count(id) from dynamic where create_psn=#{psnId} and dyn_status=#{dynStatus}")
    int getCountByPsnId(Integer psnId, Integer dynStatus);

    /**
     * 添加排他锁  先查后改不会出现问题
     * @param dynId
     * @return
     */
    Dynamic selectByIdForUpdate(@Param("dynId") Integer dynId);
    List<Dynamic> getAllFollowDynamic(List<Integer> psnIds, int dynStatus);
}
