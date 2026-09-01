package com.imeesa.togglesneakhotkey.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class TogglesneakhotkeyClient implements ClientModInitializer {
    private static KeyBinding toggleSneakModeKeyBinding;
    private static KeyBinding toggleSprintModeKeyBinding;

    private static Identifier category_identifier = Identifier.of("togglesneakhotkey", "togglehotkeys");
    private static KeyBinding.Category category = KeyBinding.Category.create(category_identifier);

    @Override
    public void onInitializeClient() {
        toggleSneakModeKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.togglesneakhotkey.togglesneakmode", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_F9, // The keycode of the key
                category  // The keybinding's category.
         ));

        toggleSprintModeKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.togglesneakhotkey.togglesprintmode", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_F10, // The keycode of the key
                category // The keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleSneakModeKeyBinding.wasPressed()) {
                if (client.player != null) {
                    boolean isSneakToggle = client.options.getSneakToggled().getValue();
                    if (isSneakToggle) client.player.setSneaking(false); // Update the player's sneaking state
                    client.options.getSneakToggled().setValue(!isSneakToggle);
                    client.player.sendMessage(Text.translatable(!isSneakToggle ? "message.togglesneakhotkey.togglesneakenabled" : "message.togglesneakhotkey.togglesneakdisabled"), true);
                }
            }
            while (toggleSprintModeKeyBinding.wasPressed()) {
                if (client.player != null) {
                    boolean isSprintToggle = client.options.getSprintToggled().getValue();
                    client.options.getSprintToggled().setValue(!isSprintToggle);
                    client.player.sendMessage(Text.translatable(!isSprintToggle ? "message.togglesneakhotkey.togglesprintenabled" : "message.togglesneakhotkey.togglesprintdisabled"), true);
                }
            }
        });
    }
}
