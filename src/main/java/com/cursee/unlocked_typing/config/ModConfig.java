package com.cursee.unlocked_typing.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import com.cursee.unlocked_typing.UnlockedTyping;

@Version(version = 1)
public class ModConfig extends Config {

    public ModConfig() {
        super(UnlockedTyping.id("main"));
    }

    public ValidatedBoolean displayFormattingExamples = new ValidatedBoolean(true);

    @Override
    public int defaultPermLevel() {
        return 2;
    }
}
