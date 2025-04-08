package com.mddApi.repositories;

import com.mddApi.models.Themes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Themes, Integer> {
    List<Themes> findAll();
}
