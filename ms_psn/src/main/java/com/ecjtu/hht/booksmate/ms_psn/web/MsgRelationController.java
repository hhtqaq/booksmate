package com.ecjtu.hht.booksmate.ms_psn.web;


import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.ms_psn.entity.MsgRelation;
import com.ecjtu.hht.booksmate.ms_psn.service.IMsgRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hht
 * @since 2019-04-23
 */
@RestController
public class MsgRelationController {
    @Autowired
    private IMsgRelationService msgRelationService;

    @GetMapping("/psn/message/tosend")
    public void sendMessageToFrd(@RequestParam("frdId") Integer frdId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Person person = (Person) request.getSession().getAttribute("mapper/person");
        MsgRelation tempRelation=new MsgRelation();
        tempRelation.setSenderId(person.getId());
        tempRelation.setReceiveId(frdId);
        msgRelationService.sendMessageToFrd(tempRelation);
        //重定向到/psn/message/show  消息页面
        response.sendRedirect("/psn/message/show");
    }
}
