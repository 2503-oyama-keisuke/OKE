package com.example.OKE.controller;

import com.example.OKE.controller.form.TaskForm;
import com.example.OKE.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ToDoListController {
    @Autowired
    TaskService taskService;

    @Autowired
    HttpSession session;


    @GetMapping("/new")
    public ModelAndView addText() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        TaskForm taskForm = new TaskForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", taskForm);
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") @Validated TaskForm taskForm, BindingResult result) throws ParseException {

        if(result.hasErrors()){
            List<String> errorMessages = new ArrayList<>();
            String errorMessage;
            for(FieldError error : result.getFieldErrors()){
                errorMessage = error.getDefaultMessage();
                errorMessages.add(errorMessage);
            }
            session.setAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/new");
        }
        // 投稿をテーブルに格納
        taskService.saveTask(taskForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        TaskForm task = taskService.editTask(id);
        mav.addObject("formModel", task);
        // 画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }

    @PutMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id, @ModelAttribute("formModel") @Validated TaskForm task, BindingResult result) throws ParseException {

        if(result.hasErrors()){
            List<String> errorMessages = new ArrayList<>();
            String errorMessage;
            for(FieldError error : result.getFieldErrors()){
                errorMessage = error.getDefaultMessage();
                errorMessages.add(errorMessage);
            }
            session.setAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/edit");
        }

        task.setId(id);
        // 投稿をテーブルに格納
        taskService.saveTask(task);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

}