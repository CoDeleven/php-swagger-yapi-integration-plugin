// This is a generated file. Not intended for manual editing.
package com.codeleven.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.codeleven.language.psi.SwaggerTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.codeleven.language.psi.*;

public class SwaggerPropertyImpl extends ASTWrapperPsiElement implements SwaggerProperty {

  public SwaggerPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SwaggerVisitor visitor) {
    visitor.visitProperty(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SwaggerVisitor) accept((SwaggerVisitor)visitor);
    else super.accept(visitor);
  }

}
