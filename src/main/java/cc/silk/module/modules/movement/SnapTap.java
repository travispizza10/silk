package cc.silk.module.modules.movement;

import cc.silk.event.impl.player.TickEvent;
import cc.silk.module.Category;
import cc.silk.module.Module;
import cc.silk.module.setting.BooleanSetting;
import meteordevelopment.orbit.EventHandler;

public final class SnapTap extends Module {

    private static final BooleanSetting forwardBack = new BooleanSetting("W/S", true);
    private static final BooleanSetting leftRight = new BooleanSetting("A/D", true);

    private boolean lastForward = false;
    private boolean lastBack = false;
    private boolean lastLeft = false;
    private boolean lastRight = false;

    public SnapTap() {
        super("Snap Tap", "Prioritizes the last pressed movement key like Wooting keyboards", -1, Category.MOVEMENT);
        this.addSettings(forwardBack, leftRight);
    }

    @EventHandler
    private void onTickEvent(TickEvent event) {
        if (isNull() || mc.currentScreen != null) return;

        if (forwardBack.getValue()) {
            boolean forwardPressed = mc.options.forwardKey.isPressed();
            boolean backPressed = mc.options.backKey.isPressed();

            if (forwardPressed && backPressed) {
                if (!lastForward && forwardPressed) {
                    mc.options.backKey.setPressed(false);
                } else if (!lastBack && backPressed) {
                    mc.options.forwardKey.setPressed(false);
                } else if (lastForward && !lastBack) {
                    mc.options.forwardKey.setPressed(false);
                } else if (lastBack && !lastForward) {
                    mc.options.backKey.setPressed(false);
                }
            }

            lastForward = forwardPressed;
            lastBack = backPressed;
        }

        if (leftRight.getValue()) {
            boolean leftPressed = mc.options.leftKey.isPressed();
            boolean rightPressed = mc.options.rightKey.isPressed();

            if (leftPressed && rightPressed) {
                if (!lastLeft && leftPressed) {
                    mc.options.rightKey.setPressed(false);
                } else if (!lastRight && rightPressed) {
                    mc.options.leftKey.setPressed(false);
                } else if (lastLeft && !lastRight) {
                    mc.options.leftKey.setPressed(false);
                } else if (lastRight && !lastLeft) {
                    mc.options.rightKey.setPressed(false);
                }
            }

            lastLeft = leftPressed;
            lastRight = rightPressed;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        lastForward = false;
        lastBack = false;
        lastLeft = false;
        lastRight = false;
    }
}
