package com.mddApi.services;

import com.mddApi.models.Themes;
import com.mddApi.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    public List<Themes> getAllThemes(){
        return themeRepository.findAll();
    }
}
