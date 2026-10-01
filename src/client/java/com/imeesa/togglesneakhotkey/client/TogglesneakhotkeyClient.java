package com.imeesa.togglesneakhotkey.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TogglesneakhotkeyClient implements ClientModInitializer {
    private static KeyMapping toggleSneakModeKeyMapping;
    private static KeyMapping toggleSprintModeKeyMapping;

    private static Identifier category_identifier = Identifier.fromNamespaceAndPath("togglesneakhotkey", "togglehotkeys");
    private static KeyMapping.Category category = KeyMapping.Category.register(category_identifier);

    @Override
    public void onInitializeClient() {
        toggleSneakModeKeyMapping = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.togglesneakhotkey.togglesneakmode",
                InputConstants.Type.values()[0],
                InputConstants.KEY_F9,
                category
        ));

        toggleSprintModeKeyMapping = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.togglesneakhotkey.togglesprintmode",
                InputConstants.Type.values()[0],
                InputConstants.KEY_F10,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleSneakModeKeyMapping.consumeClick()) {
                if (client.player != null) {
                    boolean isSneakToggle = client.options.toggleCrouch().get();

                    // if (isSneakToggle) client.crouch = false; // never worked anyways

                    client.options.toggleCrouch().set(!isSneakToggle);
                    client.options.save();
                    client.player.sendSystemMessage(Component.translatable(!isSneakToggle ? "message.togglesneakhotkey.togglesneakenabled" : "message.togglesneakhotkey.togglesneakdisabled"));
                }
            }
            while (toggleSprintModeKeyMapping.consumeClick()) {
                if (client.player != null) {
                    boolean isSprintToggle = client.options.toggleSprint().get();
                    client.options.toggleSprint().set(!isSprintToggle);
                    client.options.save();
                    client.player.sendSystemMessage(Component.translatable(!isSprintToggle ? "message.togglesneakhotkey.togglesprintenabled" : "message.togglesneakhotkey.togglesprintdisabled"));
                }
            }
        });
    }
}
