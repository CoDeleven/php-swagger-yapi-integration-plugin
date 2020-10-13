// This is a generated file. Not intended for manual editing.
package com.codeleven.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.codeleven.language.psi.impl.*;

public interface SwaggerTypes {

  IElementType PROPERTY = new SwaggerTypes("PROPERTY");

  IElementType COMMENT = new SwaggerTokenType("COMMENT");
  IElementType CRLF = new SwaggerTokenType("CRLF");
  IElementType KEY = new SwaggerTokenType("KEY");
  IElementType SEPARATOR = new SwaggerTokenType("SEPARATOR");
  IElementType VALUE = new SwaggerTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new SwaggerPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
