/*
 * Minecraft Keyboard Fix: fix keyboard issues in Minecraft on GNU/Linux
 * Copyright (C) 2019 Leo <liaoyuan@gmail.com>
 * Copyright (C) 2023 Richard Gráčik <r.gracik@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.leo3418.mckeyboardfixplusplus;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import static org.lwjgl.input.Keyboard.*;

/**
 * The main class of this mod.
 */
@Mod(
        name = "Minecraft Keyboard Fix PlusPlus",
        modid = "mckeyboardfixplusplus",
        version = "@version@",
        acceptedMinecraftVersions = "@compatible_versions@",
        clientSideOnly = true
)
public final class MCKeyboardFixPlusPlus {
    /**
     * This constructor is only intended to be called by Minecraft Forge.
     */
    public MCKeyboardFixPlusPlus() {
    }

	public static Configuration config;

	public static String[] keyCharsOriginal;
	public static String[] keyCharsRemapped;

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		keyCharsOriginal = config.getStringList("keyCharsOriginal", config.CATEGORY_CLIENT, new String[]{"+","ľ","š","č","ť","ž","ý","á","í"}, "Original key characters used to remap");
		keyCharsRemapped = config.getStringList("keyCharsRemapped", config.CATEGORY_CLIENT, new String[]{"2","3","4","5","6","7","8","9","10"}, "Remapped integer keycodes");
		if(config.hasChanged())
			config.save();
		
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        int index = 0;
		for (String key : keyCharsOriginal){
			if (key.charAt(0) == Character.toLowerCase(getEventCharacter()))
				KeyBinding.onTick(Integer.parseInt(keyCharsRemapped[index]));
			index++;
		}
    }
}
