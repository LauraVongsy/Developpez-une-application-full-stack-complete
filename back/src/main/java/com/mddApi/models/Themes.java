package com.mddApi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Theme")
public class Themes {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

}
