package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
public class ChatSuffix extends Module {
    private String suffix = " §b| §fSkyWhy";
    public ChatSuffix() { super("ChatSuffix", Category.MISC); }
    @Override
    public void onTick() {
        if (mc.player != null && mc.player.getCurrentChatMessage() != null) {
            // Изменяем сообщение чата (перехват)
        }
    }
}
