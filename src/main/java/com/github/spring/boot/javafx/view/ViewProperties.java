package com.github.spring.boot.javafx.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewProperties {
    private String title;
    private String icon;
    private boolean dialog;
    private boolean maximizable;
    private boolean centerOnScreen;
}
