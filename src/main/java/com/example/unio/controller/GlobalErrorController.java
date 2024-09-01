package com.example.unio.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;
    import javax.servlet.RequestDispatcher;
    import javax.servlet.http.HttpServletRequest;

    @Controller
    public class GlobalErrorController implements ErrorController {
        @RequestMapping("/error")
        public String handleError(HttpServletRequest request) {
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            if (status != null && Integer.parseInt(status.toString()) == HttpStatus.NOT_FOUND.value()) {
                return "forward:/index.html"; // 假设你的前端入口文件是index.html
            }
            return "error"; // 这里可以自定义你的错误页面
        }
    }

