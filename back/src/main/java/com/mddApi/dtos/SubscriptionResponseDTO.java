package com.mddApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionResponseDTO {
    private Integer themeId;
    private String themeName;
    private String themeDescription;

    @Override
    public String toString() {
        return "SubscriptionResponseDTO{" +
                "themeId=" + themeId +
                ", themeName='" + themeName + '\'' +
                ", themeDescription='" + themeDescription + '\'' +
                '}';
    }
}
