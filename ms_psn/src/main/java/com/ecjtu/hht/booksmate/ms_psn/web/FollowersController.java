package com.ecjtu.hht.booksmate.ms_psn.web;


import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.entity.Followers;
import com.ecjtu.hht.booksmate.ms_psn.service.IFollowersService;
import com.ecjtu.hht.booksmate.ms_psn.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@RestController
public class FollowersController {

    @Autowired
    private IFollowersService followersService;

    @Autowired
    private INoticeService noticeService;
    /**
     * 检查是否关注
     * @param frdId
     * @param psnId
     * @return
     */
    @GetMapping("/mapper/person/ajaxCheckIsFollow")
    public BookResult ajaxCheckIsFollow(String frdId, String psnId){
        boolean isFollow=followersService.checkIsFollow(psnId,frdId);
        return BookResult.ok(isFollow);
    }

    /**
     * 显示我的关注列表
     * @param modelAndView
     * @param request
     * @return
     */
    @GetMapping("/psn/followers/show")
    public ModelAndView showFollowers(ModelAndView modelAndView, HttpServletRequest request){
        Person person=(Person)request.getSession().getAttribute("mapper/person");
        List<Person> personList=followersService.showAllFollowers(person.getId());
        modelAndView.setViewName("front/followers");
        modelAndView.addObject("persons",personList);
        return modelAndView;
    }

    /**
     * 关注别人
     * @param followers
     * @return
     */
    @GetMapping("/mapper/person/ajaxFollowPsn")
    public BookResult ajaxFollowPsn(Followers followers){
        BookResult result= followersService.followPsn(followers);
        return result;
    }

    /**
     * 互相关注
     * @param followers
     * @param noticeId   通知id
     * @return
     */
    @GetMapping("/mapper/person/ajaxFollowPsnEach")
    public BookResult ajaxFollowPsnEach(Followers followers,Integer noticeId){
        BookResult result= followersService.followPsnEach(followers,noticeId);
        return result;
    }

    /**
     * 显示我的粉丝列表
     * @return
     */
    @GetMapping("/psn/followering/show")
    public ModelAndView showFollowering(ModelAndView modelAndView, HttpServletRequest request){
        Person person=(Person)request.getSession().getAttribute("mapper/person");
        List<Person> followeringList=followersService.showAllFollowering(person.getId());
        modelAndView.addObject("followerings", followeringList);
        modelAndView.setViewName("front/followering");
        return modelAndView;
    }

    /**
     * 取消关注
     * @param followers
     * @return
     */
    @GetMapping("/mapper/person/ajaxCancleFollow")
    public BookResult ajaxCancleFollow(Followers followers){
        BookResult bookResult= followersService.cancleFollow(followers);
        return bookResult;
    }

}
