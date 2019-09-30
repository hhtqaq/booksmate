package com.ecjtu.hht.booksmate.ms_psn.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.util.DateUtil;
import com.ecjtu.hht.booksmate.ms_psn.entity.Dynamic;
import com.ecjtu.hht.booksmate.ms_psn.service.IDynamicService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import com.ecjtu.hht.booksmate.ms_psn.vo.DynamicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@RestController
public class DynamicController {

    //fileroot=E:\\booksmate\\bookfile\\
    //fileServer=http://image.booksmate.com/img/file/
    @Value("${fileroot}")
    private String fileroot;
    @Value("${fileServer}")
    private String fileServer;
    @Autowired
    private IDynamicService dynamicService;
  /*  @Autowired
    private IBookinfoService bookinfoService;
    @Autowired
    private IBorrowRegisterService borrowRegisterService;*/
    @Autowired
    private IPersonService personService;
    /**
     * 动态主页
     */
    /*@GetMapping("/psn/dyn/show")
    public ModelAndView showDynMain(ModelAndView modelAndView) {
        //获取新的推荐书籍排行榜
         //bookinfoService.getPageRecommendBooks();
        List<Bookinfo> bookinfos= bookinfoService.getRecommendBooks(5);
        //获取最多阅读排行榜
        List<Map<String, Object>>  maxReadPsns=personService.getMaxReadPsns(5);
        //获取图书人气排行榜
        List<Bookinfo> popularBookinfos= bookinfoService.getPopularBooks(5);
        modelAndView.addObject("polularBookinfos",popularBookinfos);
        modelAndView.addObject("bookinfos",bookinfos);
        modelAndView.addObject("maxReadPsnsMap",maxReadPsns);
        modelAndView.setViewName("front/dynmain");
        return modelAndView;
    }*/

    /**
     *
     * @param modelAndView
     * @param type 动态类型 1=所有 2=关注 3=我自己
     * @return
     */
    @GetMapping("/psn/dyn/ajaxshowPostList")
    public ModelAndView showPostList(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "5")int pageSize, ModelAndView modelAndView, Integer psnId, @RequestParam(defaultValue = "1") Integer type) {
        Page page=new Page(currentPage,pageSize);
        List<DynamicVO> allDyn = dynamicService.getAllDyn(page,psnId,type);
        modelAndView.addObject("allDyn",allDyn);
        modelAndView.setViewName("front/dynmain_post");
        if(CollectionUtils.isEmpty(allDyn)&&currentPage!=1){
           return null;
        }
        return modelAndView;
    }

    /**
     * 动态图片上传
     *
     * @return
     */
    @PostMapping("/mapper/person/dyn/fileupload")
    public Map<String, String> dynImageUpload(MultipartFile file) throws IOException {
        Map<String, String> map = new HashMap<>();
        //  dateDir ="2019/4/17"
        String dateDir = DateUtil.createDateDir(new Date(), fileroot);
        UUID uuid = UUID.randomUUID();
        String originalFilename = file.getOriginalFilename();
        String filename = uuid.toString().replaceAll("-", "");
        //获取文件后缀名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        filename = filename + ext;
        Path path = Paths.get(fileroot + dateDir + File.separator + filename);
        file.transferTo(path);
        map.put("imgpath", fileServer + dateDir + "/" + filename);
        return map;
    }

    /**
     * 发布动态
     *
     * @return
     */
    @PostMapping("/mapper/person/dyn/post")
    public void postDyn(ModelAndView modelAndView, HttpServletResponse response, Dynamic dynamic) throws IOException {
        dynamicService.postDyn(dynamic);
        response.sendRedirect("/psn/dyn/show");
    }

    /**
     * 加减点赞数  type=1 加  type=2 减
     * {dynId:dynId,type:type},
     */
    @GetMapping("/mapper/person/dyn/optAwardCount")
    public BookResult optDynAwardCount(Integer dynId, Integer type){
        BookResult bookResult=dynamicService.optDynAwardCount(dynId,type);
        return bookResult;
    }

    /**
     * 跳转动态详情
     * @param dynId
     * @param modelAndView
     * @return
     */
    @GetMapping("/mapper/person/dyn/detail/{dynId}")
    public ModelAndView showDynDetail(@PathVariable("dynId")Integer dynId, ModelAndView modelAndView){
        DynamicVO dynamicVO= dynamicService.getDynDetailById(dynId);
       modelAndView.addObject("dynamicVO",dynamicVO);
       modelAndView.setViewName("front/dyn_detail");
       return modelAndView;
    }

}
