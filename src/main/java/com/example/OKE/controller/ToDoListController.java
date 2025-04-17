package com.example.OKE.controller;

import com.example.OKE.controller.form.TaskForm;
import com.example.OKE.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ToDoListController {
    @Autowired
    TaskService taskService;
    @Autowired
    HttpSession session;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top(@RequestParam(name = "start", required = false) LocalDate start,
                            @RequestParam(name = "end", required = false)LocalDate end,
                            @RequestParam(name = "status", required = false)Short status,
                            @RequestParam(name = "content", required = false)String content) {
        ModelAndView mav = new ModelAndView();
        // タスクを期限日時で絞り込み取得
        List<TaskForm> taskList = taskService.findByLimitDateRange(start, end, status, content);

        Object error = session.getAttribute("error");
        Object formTaskId = session.getAttribute("formTaskId");
        if(error != null) {
            mav.addObject("formTaskId", formTaskId);
            mav.addObject("error", error);
            session.removeAttribute("error");
        }
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("tasks", taskList);
        mav.addObject("start", start);
        mav.addObject("end", end);

        return mav;
    }

    /*
     * タスク削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteTask(@PathVariable Integer id) {
        //テーブルから投稿を削除
        taskService.deleteTask(id);
        //rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

}
