package com.herrkatze.geoactive;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class GeyserCreatorScreen extends Screen {
    protected Component FluidType = Component.translatable("geoactive.fluid_type");
    protected Component TITLE = Component.translatable("geoactive.geyser_creator_title");
    protected Component SET_DELAY = Component.translatable("geoactive.set_delay");
    protected Component SET_AMOUNT = Component.translatable("geoactive.set_amount");
    protected Component SET_IDLE_LENGTH = Component.translatable("geoactive.set_idle_length");
    protected Component SET_ACTIVE_LENGTH = Component.translatable("geoactive.set_active_length");
    protected EditBox fluid;
    protected EditBox delay;
    protected EditBox amount;
    protected EditBox idle;
    protected EditBox active;
    protected Button doneButton;
    protected GeyserCreatorScreen() {
        super(GameNarrator.NO_TITLE);
    }
    protected void init() {


        this.doneButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (p_97691_) -> {
            this.onDone();
        }).bounds(this.width / 2-75 , this.height / 4 + 120 + 12, 150, 20).build());
        this.fluid = new EditBox(this.font, this.width / 2 - 150, 50, 300, 20, Component.translatable("geoactive.fluidInput")) {};
        this.amount = new EditBox(this.font, this.width / 2 - 150, 90, 100, 20, Component.translatable("geoactive.amountInput")) {};
        this.delay = new EditBox(this.font, this.width / 2 + 50 , 90, 100, 20, Component.translatable("geoactive.delayInput")) {};
        this.idle = new EditBox(this.font, this.width / 2 - 150, 130, 100, 20, Component.translatable("geoactive.idleInput")) {};
        this.active = new EditBox(this.font, this.width / 2 + 50 , 130, 100, 20, Component.translatable("geoactive.activeInput")) {};

        this.fluid.setMaxLength(32500);
        this.amount.setMaxLength(100);
        this.delay.setMaxLength(100);
        this.idle.setMaxLength(100);
        this.active.setMaxLength(100);
        this.addWidget(this.fluid);
        this.addWidget(this.amount);
        this.addWidget(this.delay);
        this.addWidget(this.idle);
        this.addWidget(this.active);
        this.setInitialFocus(this.fluid);

    }
    protected void onDone(){
        String fluidstr = fluid.getValue();
        String amountstr = amount.getValue();
        String delaystr = delay.getValue();
        String idlestr = idle.getValue();
        String activestr = active.getValue();
        int amountValue = 0,delayValue = 0,idleValue =0,activeValue=0;
        if(fluidstr == "") {
            fluidstr = "minecraft:lava";
        }
        if (amountstr == "") {amountstr = "0";}
        if (delaystr == "") {delaystr = "0";}
        if (idlestr == "") {idlestr = "0";}
        if (activestr == "") {activestr = "0";}
        try{
            amountValue= Integer.parseInt(amountstr);
            delayValue = Integer.parseInt(delaystr);
            idleValue = Integer.parseInt(idlestr);
            activeValue = Integer.parseInt(activestr);
        }
        catch (NumberFormatException nfe) {
            this.minecraft.setScreen(new GAErrorScreen("geoactive.number_error"));
        }

        GeoActivePacketHandler.CHANNEL.sendToServer(new GeyserCreatorPacket(fluidstr,amountValue,delayValue,idleValue,activeValue));
        this.minecraft.setScreen((Screen)null);
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, TITLE, this.width / 2, 20, 16777215);
        graphics.drawString(this.font, FluidType, this.width / 2-150 , 40, 10526880);
        graphics.drawString(this.font, SET_AMOUNT,this.width/2 - 150,80,10526880);
        graphics.drawString(this.font, SET_DELAY,this.width/2 + 50,80,10526880);
        graphics.drawString(this.font,SET_IDLE_LENGTH,this.width/2-150,160,10526880);
        graphics.drawString(this.font,SET_ACTIVE_LENGTH,this.width/2+50,160,10526880);
        this.fluid.render(graphics, pMouseX, pMouseY, pPartialTick);
        this.delay.render(graphics,pMouseX,pMouseY,pPartialTick);
        this.amount.render(graphics,pMouseX,pMouseY,pPartialTick);
        this.idle.render(graphics,pMouseX,pMouseY,pPartialTick);
        this.active.render(graphics,pMouseX,pMouseY,pPartialTick);
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
    }


}
