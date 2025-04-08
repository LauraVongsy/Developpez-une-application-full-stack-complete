package com.mddApi.controllers;

import com.mddApi.models.Themes;
import com.mddApi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/themes")
    public List<Themes> getAllThemes() {
        return themeService.getAllThemes();
    }
}
