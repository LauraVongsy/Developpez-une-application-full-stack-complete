package com.mddApi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequestDTO {
    @NotNull(message = "ThemeId can't be null when subscribing")
    private Integer themeId;
}
