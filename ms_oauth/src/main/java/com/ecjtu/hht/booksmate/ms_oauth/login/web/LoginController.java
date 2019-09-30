package com.ecjtu.hht.booksmate.ms_oauth.login.web;

import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.common.util.CaptchaUtil;
import com.ecjtu.hht.booksmate.ms_oauth.login.service.LoginService;
import com.ecjtu.hht.booksmate.ms_oauth.login.vo.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private IPersonService personService;

    /**
     * 用于生成带四位数字验证码的图片
     */
    @RequestMapping(value = "/r/validateEncoding")
    @ResponseBody
    public String imagecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        //返回验证码和图片的map
        Map<String, Object> map = CaptchaUtil.getImageCode(100, 37, os);
        //把code 放在session中
        request.getSession().setAttribute("code", map.get("strEnsure").toString().toLowerCase());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
        } catch (IOException e) {
            return "";
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return null;
    }

    /**
     * 用户登录
     *
     * @param request
     * @param session
     * @param loginForm
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/oauth/login")
    public BookResult login(HttpServletRequest request,
                            HttpSession session, LoginForm loginForm) {
        //验证验证码
        BookResult result = loginService.validateCode(loginForm.getValidateCode(), session);
        //如果验证通过
        if (result.getStatus().equals(200L)) {
            //验证用户名密码
            result = loginService.validatePassword(loginForm.getEmail(), loginForm.getPassword());
            if (result.getStatus().equals(200L)) {
                //登陆成功 设置到session
                request.getSession().setAttribute("person", result.getData());
            }
        }
        return result;
    }

    @GetMapping("/oauth/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Person person = (Person) request.getSession().getAttribute("person");
        if (person != null) {
            request.getSession().removeAttribute("person");
        }
        response.sendRedirect("/");
    }

    @GetMapping("/oauth/list")
    public BookResult getPersonList() {
        return personService.getAllPerson();
    }
}
