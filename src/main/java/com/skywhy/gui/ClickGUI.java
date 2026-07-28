package com.skywhy.gui;

import com.skywhy.client.SkyWhyClient;
import com.skywhy.module.Module;
import com.skywhy.render.Render2D;
import com.skywhy.render.Render3D;
import com.skywhy.utils.Animation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClickGUI extends Screen {
    private int selectedCategory = 0;
    private int scrollOffset = 0;
    private String searchQuery = "";
    private boolean dragging = false;
    private int dragX, dragY;
    private int guiX = 100, guiY = 50;
    private int guiWidth = 600, guiHeight = 400;
    private Animation fadeIn = new Animation(0, 1, 300, Animation.Easing.OUT_QUAD);
    private List<Module.Category> categories = List.of(Module.Category.values());
    private List<Module> filteredModules = new ArrayList<>();

    public ClickGUI() {
        super(Text.literal("SkyWhy Client"));
        fadeIn.start();
        updateFilter();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Background blur (simulated with dark overlay)
        fill(context, 0, 0, width, height, new Color(0,0,0, 150).getRGB());
        // Main panel
        float alpha = (float) fadeIn.getValue();
        int panelColor = new Color(20, 22, 30, (int)(230 * alpha)).getRGB();
        int borderColor = new Color(0, 150, 255, (int)(200 * alpha)).getRGB();
        Render2D.drawRoundedRect(context, guiX, guiY, guiWidth, guiHeight, 12, panelColor);
        Render2D.drawRoundedOutline(context, guiX, guiY, guiWidth, guiHeight, 12, 2, borderColor);
        // Header
        Render2D.drawString(context, "SkyWhy Client", guiX + 15, guiY + 10, 0xFFFFFF, 1.2f);
        Render2D.drawString(context, "v1.0.0", guiX + 180, guiY + 14, 0x8888AA, 0.8f);
        // Search bar
        int searchX = guiX + guiWidth - 180;
        Render2D.drawRoundedRect(context, searchX, guiY + 8, 160, 28, 6, new Color(40,42,55, (int)(255*alpha)).getRGB());
        Render2D.drawString(context, searchQuery.isEmpty() ? "Search..." : searchQuery, searchX + 10, guiY + 14, 0xAAAAAA, 0.9f);
        // Categories (left panel)
        int catX = guiX + 10;
        int catY = guiY + 50;
        for (int i = 0; i < categories.size(); i++) {
            boolean sel = (i == selectedCategory);
            int bg = sel ? new Color(0,150,255, (int)(200*alpha)).getRGB() : new Color(30,32,45, (int)(180*alpha)).getRGB();
            Render2D.drawRoundedRect(context, catX, catY + i*36, 120, 30, 8, bg);
            Render2D.drawString(context, categories.get(i).name(), catX + 10, catY + i*36 + 8, sel ? 0xFFFFFF : 0xAAAAAA, 0.9f);
            if (mouseX > catX && mouseX < catX+120 && mouseY > catY + i*36 && mouseY < catY + i*36 + 30) {
                selectedCategory = i;
            }
        }
        // Modules (right panel)
        int modX = guiX + 150;
        int modY = guiY + 50;
        int modYOffset = 0;
        for (Module m : filteredModules) {
            if (m.getCategory() != categories.get(selectedCategory)) continue;
            int modBg = m.isEnabled() ? new Color(0,100,200, (int)(150*alpha)).getRGB() : new Color(40,42,55, (int)(180*alpha)).getRGB();
            Render2D.drawRoundedRect(context, modX, modY + modYOffset, 420, 32, 6, modBg);
            Render2D.drawString(context, m.getName(), modX + 12, modY + modYOffset + 8, 0xFFFFFF, 1.0f);
            // Toggle switch
            int toggleX = modX + 380;
            int toggleColor = m.isEnabled() ? 0x00FFAA : 0x666666;
            Render2D.drawRoundedRect(context, toggleX, modY + modYOffset + 6, 28, 18, 9, toggleColor);
            if (m.isEnabled()) {
                Render2D.drawCircle(context, toggleX + 20, modY + modYOffset + 14, 7, 0xFFFFFF);
            } else {
                Render2D.drawCircle(context, toggleX + 8, modY + modYOffset + 14, 7, 0xAAAAAA);
            }
            // Click toggle
            if (mouseX > toggleX && mouseX < toggleX+28 && mouseY > modY + modYOffset + 6 && mouseY < modY + modYOffset + 24) {
                if (GLFW.glfwGetMouseButton(GLFW.glfwGetCurrentContext(), GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS) {
                    m.toggle();
                    SkyWhyClient.INSTANCE.configManager.save();
                }
            }
            modYOffset += 40;
        }
        // Drag logic
        if (dragging) {
            guiX = mouseX - dragX;
            guiY = mouseY - dragY;
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && mouseX > guiX && mouseX < guiX+guiWidth && mouseY > guiY && mouseY < guiY+30) {
            dragging = true;
            dragX = (int)(mouseX - guiX);
            dragY = (int)(mouseY - guiY);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !searchQuery.isEmpty()) {
            searchQuery = searchQuery.substring(0, searchQuery.length()-1);
            updateFilter();
            return true;
        }
        if (keyCode >= 32 && keyCode <= 126) {
            searchQuery += (char) keyCode;
            updateFilter();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void updateFilter() {
        filteredModules = SkyWhyClient.INSTANCE.moduleManager.getModules().stream()
                .filter(m -> m.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                .sorted(Comparator.comparing(Module::getName))
                .toList();
    }

    @Override
    public boolean shouldPause() { return false; }
    @Override
    public boolean shouldCloseOnEsc() { return true; }
              }
