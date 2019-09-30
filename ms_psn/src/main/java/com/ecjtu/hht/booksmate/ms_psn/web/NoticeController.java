package com.ecjtu.hht.booksmate.ms_psn.web;


import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.entity.Notice;
import com.ecjtu.hht.booksmate.ms_psn.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hht
 * @since 2019-04-11
 */
@RestController
public class NoticeController {

    @Autowired
    private INoticeService noticeService;
    /**
     * 获取所有的通知
     * @param request
     * @return
     */
    @GetMapping("/mapper/person/getAllNotice")
    public ModelAndView getAllNotice(HttpServletRequest request, ModelAndView modelAndView){
        Person person=(Person)request.getSession().getAttribute("mapper/person");
        Map<String, List<Notice>> map=  noticeService.getAllNotice(person.getId());
        modelAndView.addObject("noticeMap",map);
        modelAndView.setViewName("front/noticeList");
        return modelAndView;
    }

    /**
     * 忽略通知
     * @param id
     * @return
     */
    @GetMapping("/mapper/person/ignoreNotice")
    public BookResult ignoreNotice(@RequestParam("id") Integer id){
        BookResult bookResult= noticeService.ignoreNotice(id);
        return bookResult;
    }
    /**
     * 标记所有通知为已读
     *   @param  noticeType 通知类型
     */
    @GetMapping("/mapper/person/markAllReaded")
    public BookResult markAllReaded(@RequestParam("noticeType")Integer noticeType, HttpServletRequest request){
        Person person=(Person)request.getSession().getAttribute("mapper/person");
        BookResult bookResult=noticeService.markAllReadedNotice(noticeType,person.getId());
        return bookResult;
    }
}
