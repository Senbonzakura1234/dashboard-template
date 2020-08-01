package com.app.manager.controller;

import com.app.manager.entity.File;
import com.app.manager.model.SelectOption;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalVariables {
    @ModelAttribute
    public void addAttributes(Model model) {
        List<SelectOption> statusList = new ArrayList<>();
        for (File.StatusEnum item: File.StatusEnum.values()
        ) {
            statusList.add(new SelectOption(item.name(), item.name(),
                    item == File.StatusEnum.ALL));
        }
        model.addAttribute("statusList", statusList);
        model.addAttribute("msg", "File Manager");
    }
}
