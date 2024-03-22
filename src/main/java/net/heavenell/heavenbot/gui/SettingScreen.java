package net.heavenell.heavenbot.gui;

import net.heavenell.heavenbot.setting.HeavenBotSetting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;


public class SettingScreen extends Screen {
    private final HeavenBotSetting setting;
    private static final int WIDGET_WIDTH = 150;
    private static final int WIDGET_HEIGHT = 20;

    public SettingScreen(HeavenBotSetting setting){
        super(Text.translatable("heavenbot.setting_title"));
        this.setting = setting;
    }
    @Override
    protected void init(){
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(6).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(1); // Create a single-column grid

        adder.add(ButtonWidget.builder(Text.translatable("heavenbot.test_button"), button -> System.out.println("YOU CLICKED THE TEST BUTTON")).dimensions(0, 0,WIDGET_WIDTH,WIDGET_HEIGHT).build());

        // Add cycling buttons for settings
        adder.add(CyclingButtonWidget.onOffBuilder(setting.isAutoGreeting())
                .build(0, 0,WIDGET_WIDTH,WIDGET_HEIGHT, Text.translatable("heavenbot.autogreet"), (button, enabled) -> setting.toggleAutoGreeting()));

        adder.add(CyclingButtonWidget.onOffBuilder(setting.isAutoResponse())
                .build(0, 0,WIDGET_WIDTH,WIDGET_HEIGHT, Text.translatable("heavenbot.autoresponse"), (button, enabled) -> setting.toggleAutoResponse()));

        adder.add(CyclingButtonWidget.onOffBuilder(setting.isAutoAccept())
                .build(0, 0,WIDGET_WIDTH,WIDGET_HEIGHT, Text.translatable("heavenbot.autoaccept"), (button, enabled) -> setting.toggleAutoAccept()));

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.5f);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.getMatrices().push();
        context.getMatrices().scale(2.0f, 2.0f, 2.0f);
        drawCenteredText(context, this.textRenderer, this.title, this.width / 2 / 2, 15, 0xFFFFFF);
        context.getMatrices().pop();
    }
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void drawCenteredText(DrawContext context, TextRenderer textRenderer, Text text, int centerX, int y, int color){
        OrderedText orderedText = text.asOrderedText();
        context.drawText(textRenderer, text, centerX - textRenderer.getWidth(orderedText) / 2, y, color, false);
    }
}
