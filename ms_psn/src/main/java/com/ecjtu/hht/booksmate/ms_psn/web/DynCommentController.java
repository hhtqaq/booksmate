package com.ecjtu.hht.booksmate.ms_psn.web;


import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.ms_psn.entity.DynComment;
import com.ecjtu.hht.booksmate.ms_psn.service.IDynCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hht
 * @since 2019-04-17
 */
@RestController
public class DynCommentController {
    @Autowired
    private IDynCommentService dynCommentService;
    /**
     * 回复动态
     * @return
     */
    @GetMapping("/mapper/person/dyn/reply")
    public BookResult replyDyn(DynComment comment){
        BookResult bookResult= null;
        try {
            bookResult = dynCommentService.saveReplyDyn(comment);
        } catch (Exception e) {
            return BookResult.build(400,"动态回复失败");
        }
        return bookResult;
    }
    @GetMapping("/mapper/person/dyn/optCommentCount")
    public BookResult optCommentCount(Integer commentId,Integer type){
        BookResult bookResult= dynCommentService.optCommentCount(commentId,type);
        return bookResult;
    }
    @GetMapping("/psn/dyn/ajaxGetDynComment")
    public ModelAndView getDynComment(Integer dynId, ModelAndView modelAndView){
        List<DynComment> dynComments = dynCommentService.getDynAllCommentByDynId(dynId);
        modelAndView.addObject("dynComments",dynComments);
        modelAndView.setViewName("front/dyn_detail_comment");
        return modelAndView;
    }
}
