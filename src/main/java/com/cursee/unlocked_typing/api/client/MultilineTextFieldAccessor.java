package com.cursee.unlocked_typing.api.client;

public interface MultilineTextFieldAccessor {
    void unlocked_typing$forEachLine(LineConsumer consumer);

    interface LineConsumer {
        void accept(int beginIndex, int endIndex);
    }
}
