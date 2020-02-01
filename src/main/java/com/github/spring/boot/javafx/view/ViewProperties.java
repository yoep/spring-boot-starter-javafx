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
    /**
     * The title of the window.
     */
    private String title;
    /**
     * The icon of the window to show in the title bar or taskbar.
     */
    private String icon;
    /**
     * The new window will be set as a dialog on top of the current window.
     */
    private boolean dialog;
    /**
     * The indication if the new window should be resizable.
     */
    @Builder.Default
    private boolean resizable = true;
    /**
     * The indication if the new window will be maximized by default.
     */
    private boolean maximized;
    /**
     * The new window will be automatically centered on the primary screen.
     */
    @Builder.Default
    private boolean centerOnScreen = true;
}
