package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.common.util.DateUtil;
import com.ecjtu.hht.booksmate.ms_psn.entity.DynComment;
import com.ecjtu.hht.booksmate.ms_psn.entity.Dynamic;
import com.ecjtu.hht.booksmate.ms_psn.mapper.DynamicMapper;
import com.ecjtu.hht.booksmate.ms_psn.service.IDynCommentService;
import com.ecjtu.hht.booksmate.ms_psn.service.IDynamicService;
import com.ecjtu.hht.booksmate.ms_psn.service.IFollowersService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import com.ecjtu.hht.booksmate.ms_psn.vo.DynamicVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements IDynamicService {

    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private IPersonService personService;
    @Autowired
    private IDynCommentService dynCommentService;
    @Autowired
    private IFollowersService followersService;

    /**
     * 发布动态
     *
     * @param dynamic
     */
    @Override
    @Transactional
    public void postDyn(Dynamic dynamic) {
        dynamic.setCreateTime(new Date());
        dynamic.setDynStatus(1);
        dynamic.setAwardCount(0);
        dynamicMapper.insert(dynamic);
    }

    /**
     * 获取所有动态VO类
     *
     * @param psnId
     * @param type  动态类型  1=所有 2=关注 3=我自己
     * @return
     */
    @Override
    public List<DynamicVO> getAllDyn(Page<Dynamic> dynamicPage, Integer psnId, Integer type) {
        List<DynamicVO> dynamicVOs = new ArrayList<>();
        List<Dynamic> dynamics = new ArrayList<>();
        if (type == 1) {
            dynamics = dynamicMapper.getAllDynamic(dynamicPage, null, null);
            for (int i = 0; i < dynamics.size(); i++) {
                //过滤掉要好友才能看到的
                Dynamic dynamic = dynamics.get(i);
                Integer createPsn = dynamic.getCreatePsn();
                //自己也可以看得到
                if (dynamic.getPermission() == 2 && !psnId.equals(createPsn)) {
                    //判断是否是好友关系
                    boolean isFriend = followersService.checkIsFriend(createPsn, psnId);
                    //如果不是好友关系就移除
                    if (!isFriend) {
                        dynamics.remove(i);
                    }
                }
            }
        } else if (type == 2) {//获取关注的动态
            //1获取我的关注人
            List<Integer> psnIds = followersService.getFollowersIds(psnId);
            if (psnIds != null && psnIds.size() > 0) {
                dynamics = dynamicMapper.getAllFollowDynamic(psnIds, 1);
            }
        } else {//获取自己的动态
            List<Integer> psnIds = Arrays.asList(psnId);
            dynamics = dynamicMapper.getAllFollowDynamic(psnIds, 1);
        }
        dynamics.forEach(dynamic -> {
            buildDynInfo(dynamic, dynamicVOs);
        });
        return dynamicVOs;
    }

    /**
     * 加减动态点赞数
     *
     * @param dynId
     * @param type
     * @return
     */
    @Override
    @Transactional
    public BookResult optDynAwardCount(Integer dynId, Integer type) {
        Dynamic dynamic = dynamicMapper.selectByIdForUpdate(dynId);
        Integer count = dynamic.getAwardCount();
        count = type == 1 ? ++count : --count;
        count = count < 0 ? 0 : count;
        dynamicMapper.updateCount(count, dynId);
        return BookResult.ok();
    }

    /**
     * 获取动态详情
     *
     * @param dynId 动态Id
     * @return
     */
    @Override
    public DynamicVO getDynDetailById(Integer dynId) {
        Dynamic dynamic = dynamicMapper.selectById(dynId);
        Person person = personService.selectById(dynamic.getCreatePsn());
        DynamicVO dynamicVO = new DynamicVO();
        //处理动态时间图片和评论数
        dealTimeAndImgAndCommentCount(dynamic);
        //获取动态评论列表
        List<DynComment> dynComments = dynCommentService.getDynAllCommentByDynId(dynId);
        dynamicVO.setDynamic(dynamic);
        dynamicVO.setPerson(person);
        dynamicVO.setDynComment(dynComments);
        return dynamicVO;
    }

    /**
     * 获取动态数量
     *
     * @param psnId
     * @return
     */
    @Override
    public int getCountByPsnId(Integer psnId, Integer dynStatus) {
        return dynamicMapper.getCountByPsnId(psnId, dynStatus);
    }

    private void buildDynInfo(Dynamic dynamic, List<DynamicVO> dynamicVOs) {
        DynamicVO dynamicVO = new DynamicVO();
        Person person = personService.selectById(dynamic.getCreatePsn());
        dealTimeAndImgAndCommentCount(dynamic);
        //处理动态内容
        String content = dynamic.getContent();
        dynamic.setMore(0);
        if (StringUtils.isNotEmpty(content) && content.length() > 120) {
            // 简短内容
            dynamic.setSimpleContent(content.substring(0, 120) + " ...");
            dynamic.setMore(1);
        }
        dynamicVO.setPerson(person);
        dynamicVO.setDynamic(dynamic);
        dynamicVOs.add(dynamicVO);
    }

    /**
     * 处理时间  评论 评论数
     *
     * @param dynamic
     */
    private void dealTimeAndImgAndCommentCount(Dynamic dynamic) {
        //处理时间
        dynamic.setDateDesc(DateUtil.getTimeFormatText(dynamic.getCreateTime()));
        //处理图片
        String imgInfo = dynamic.getImgInfo();
        List<String> url = new ArrayList<>();
        if (StringUtils.isNotEmpty(imgInfo)) {
            String[] split = imgInfo.split(",");
            url = Arrays.asList(split);
        }
        dynamic.setImgUrls(url);
        //构建评论数量
        Integer commentCount = dynCommentService.getDynCommentCount(dynamic.getId());
        dynamic.setCommentCount(commentCount);
    }
}
