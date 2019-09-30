package com.ecjtu.hht.booksmate.ms_psn.web;


import com.ecjtu.hht.booksmate.ms_psn.service.IRecentAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
public class RecentAccessController {

    @Autowired
    private IRecentAccessService recentAccessService;

    //获取最近访客记录  最多显示四条
    @GetMapping("/mapper/person/ajaxGetRecentAccess/{psnId}")
    public ModelAndView ajaxGetRecentAccess(@PathVariable("psnId") Integer psnId, ModelAndView modelAndView) {
        List<Map<String, Object>> recentMap = recentAccessService.getRecentAccess(psnId);
        modelAndView.addObject("recentMap", recentMap);
        modelAndView.setViewName("front/homepage_recent");
        return modelAndView;
    }
}
