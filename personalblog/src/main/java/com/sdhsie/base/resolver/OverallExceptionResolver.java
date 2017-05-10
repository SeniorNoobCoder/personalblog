package com.sdhsie.base.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 *系统全局异常处理器
 *@author 王旭
 *@time 2015-10-4 下午3:38:41
 */
@Controller
public class OverallExceptionResolver implements HandlerExceptionResolver {
    
    
    /**
     * 进行全局异常的过滤和处理
     */
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        //handler为当前处理器适配器执行的对象
//        String message = null;
        //判断是否为系统自定义异常。
//        if(ex instanceof CustomException) {
//            message = ((CustomException) ex).getMessage();
//        }
//        
//        else {
//            message = "系统出错啦，稍后再试试！";
//        }
        
        
        ModelAndView modelAndView = new ModelAndView();
        //跳转到相应的处理页面
        modelAndView.addObject("errorMsg", ex);
        modelAndView.setViewName("base/error");
        
        return modelAndView;
    }
    

}