package com.ecjtu.hht.booksmate.ms_psn.web;


import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.entity.PrivateLetters;
import com.ecjtu.hht.booksmate.ms_psn.service.IMsgRelationService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import com.ecjtu.hht.booksmate.ms_psn.service.IPrivateLettersService;
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
 * 前端控制器
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@RestController
public class PrivateLettersController {
    @Autowired
    private IMsgRelationService msgRelationService;
    @Autowired
    private IPrivateLettersService privateLettersService;
    @Autowired
    private IPersonService personService;

    /**
     * 跳转人员消息页面
     */
    @GetMapping("/psn/message/show")
    public ModelAndView showMessage(ModelAndView modelAndView, HttpServletRequest request) {
        Person person = (Person) request.getSession().getAttribute("mapper/person");
        //获取所有的聊天人员列表
        List<Map<String, Object>> maps = msgRelationService.getAllChatPsnList(person.getId());
        modelAndView.addObject("chatMaps", maps);
        modelAndView.setViewName("front/messages");
        return modelAndView;
    }

    /**
     * 显示消息列表
     *
     * @param frdId
     * @param request
     * @param modelAndView
     * @return
     */
    @GetMapping("/psn/message/showMsgList")
    public ModelAndView showMsgList(@RequestParam("frdId") Integer frdId, HttpServletRequest request, ModelAndView modelAndView) {
        Person person = (Person) request.getSession().getAttribute("mapper/person");
        List<PrivateLetters> letters = privateLettersService.getMsgListByFrdId(person.getId(), frdId);
        modelAndView.setViewName("front/message_list");
        Person friend = personService.selectById(frdId);
        modelAndView.addObject("friend", friend);
        modelAndView.addObject("letters", letters);
        modelAndView.addObject("frdId", frdId);
        return modelAndView;
    }

    /**
     * 发送消息
     *
     * @param senderPsnId
     * @param receivePsnId
     * @param content
     * @return
     */
    @GetMapping("/psn/message/send")
    public BookResult sendMessage(String senderPsnId, String receivePsnId, String content) {
        PrivateLetters privateLetters = new PrivateLetters();
        privateLetters.setReceivePsnId(Integer.parseInt(receivePsnId));
        privateLetters.setSenderPsnId(Integer.parseInt(senderPsnId));
        privateLetters.setContent(content);
        privateLettersService.sendMessage(privateLetters);
        return BookResult.ok();
    }
}
