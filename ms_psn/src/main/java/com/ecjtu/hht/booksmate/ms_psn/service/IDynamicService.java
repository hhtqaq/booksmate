package com.ecjtu.hht.booksmate.ms_psn.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.ms_psn.entity.Dynamic;
import com.ecjtu.hht.booksmate.ms_psn.vo.DynamicVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
public interface IDynamicService extends IService<Dynamic> {

    /**
     * 发布动态
     * @param dynamic
     */
    void postDyn(Dynamic dynamic);


    /**
     * 获取所有动态VO类
     * @param psnId
     * @param type 动态类型 1=所有 2=关注 3=我自己
     * @return
     */
    List<DynamicVO> getAllDyn(Page<Dynamic> dynamicPage, Integer psnId, Integer type);
    /**
     * 加减动态点赞数
     * @param dynId
     * @param type
     * @return
     */
    BookResult optDynAwardCount(Integer dynId, Integer type);

    /**
     * 获取动态详情
     * @param dynId    动态Id
     * @return
     */
    DynamicVO getDynDetailById(Integer dynId);

    /**获取动态数量
     *
     * @param psnId
     * @return
     */
    int getCountByPsnId(Integer psnId, Integer dynStatus);
}
