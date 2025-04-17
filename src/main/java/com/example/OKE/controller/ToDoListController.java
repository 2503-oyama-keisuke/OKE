package com.example.OKE.controller;

import com.example.OKE.controller.form.TaskForm;
import com.example.OKE.repository.entity.Task;
import com.example.OKE.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ToDoListController {
    @Autowired
    TaskService taskService;

    /*
     * タスク内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();

        // 投稿を全件取得
        List<TaskForm> contentData = taskService.findAllTask();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);

        // 現在日時取得・オブジェクト保管
        mav.addObject("today", LocalDateTime.now());


        return mav;

    }
}
