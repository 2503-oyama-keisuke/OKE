package com.example.OKE.controller;

import com.example.OKE.controller.form.TaskForm;
import com.example.OKE.service.TaskService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
                            @RequestParam(name = "end", required = false) LocalDate end,
                            @RequestParam(name = "status", required = false) Integer status,
                            @RequestParam(name = "content", required = false) String content) {
        ModelAndView mav = new ModelAndView();
        // タスクを期限日時で絞り込み取得
        List<TaskForm> taskList = taskService.findByLimitDateRange(start, end, status, content);
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 現在日時オブジェクト保管
        mav.addObject("today", LocalDate.now());
        // 投稿データオブジェクトを保管
        mav.addObject("tasks", taskList);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("content", content);

        if (!CollectionUtils.isEmpty((Collection<?>) session.getAttribute("errorMessages"))) {
            mav.addObject("errorMessages", session.getAttribute("errorMessages"));
        }
        session.invalidate();

        return mav;
    }

    @GetMapping("/new")
    public ModelAndView addText() {
        ModelAndView mav = new ModelAndView();
        TaskForm task = new TaskForm();
        mav.addObject("formModel", task);
        // 画面遷移先を指定
        mav.setViewName("/new");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") @Validated TaskForm task, BindingResult result) {

        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors() || task.getContent().matches("[　]*")) {
            if (result.hasErrors()) {
                String errorMessage;
                for (FieldError error : result.getFieldErrors()) {
                    errorMessage = error.getDefaultMessage();
                    errorMessages.add(errorMessage);
                }
            }
            if (task.getContent().matches("[　]*")) {
                errorMessages.add("・タスクを入力してください");
            }
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/new");
            mav.addObject("formModel", task);
            mav.addObject("errorMessages", errorMessages);
            return mav;
        }
        Integer status = 1;
        task.setStatus(status);
        // タスクをテーブルに格納
        taskService.saveTask(task);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    @GetMapping({"/edit/{id}", "/edit/"})
    public ModelAndView editContent(@PathVariable(required = false) String id) {
        List<String> errorMessages = new ArrayList<>();
        TaskForm task = null;

        if ((!StringUtils.isBlank(id)) && id.matches("^[0-9]*$")) {
            Integer taskId = Integer.parseInt(id);
            task = taskService.editTask(taskId);
        }
        if (task == null) {
            errorMessages.add("・不正なパラメータです");
            session.setAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/");
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("formModel", task);
        // 画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }

    @PutMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id, Integer
            status, @ModelAttribute("formModel") @Validated TaskForm task, BindingResult result) {

        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors() || task.getContent().matches("[　]*")) {
            if (result.hasErrors()) {
                String errorMessage;
                for (FieldError error : result.getFieldErrors()) {
                    errorMessage = error.getDefaultMessage();
                    errorMessages.add(errorMessage);
                }
            }
            if (task.getContent().matches("[　]*")) {
                errorMessages.add("・タスクを入力してください");
            }
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/edit");
            mav.addObject("formModel", task);
            mav.addObject("errorMessages", errorMessages);
            return mav;
        }

        task.setId(id);
        task.setStatus(status);
        // タスクをテーブルに格納
        taskService.saveTask(task);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
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

    /*
     * ステータス変更処理
     */
    @PutMapping("/editStatus/{id}")
    public ModelAndView editContent(@PathVariable Integer id, Integer status) {
        taskService.saveStatus(id, status);
        return new ModelAndView("redirect:/");
    }

}