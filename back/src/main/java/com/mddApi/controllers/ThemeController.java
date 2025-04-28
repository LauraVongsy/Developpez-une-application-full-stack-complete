package com.mddApi.controllers;

import com.mddApi.models.Themes;
import com.mddApi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller handling all the themes related endpoints
 */
@RestController
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    /**
     * gets all the existing themes
     * @return
     */
    @GetMapping("/themes")
    public List<Themes> getAllThemes() {
        return themeService.getAllThemes();
    }
}
