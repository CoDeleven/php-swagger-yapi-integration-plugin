package com.codeleven.language;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import com.jetbrains.php.lang.PhpLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwaggerTokenType extends IElementType {
    public SwaggerTokenType(@NotNull String debugName, @Nullable Language language) {
        super(debugName, PhpLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "SwaggerTokenType." + super.toString();
    }
}
