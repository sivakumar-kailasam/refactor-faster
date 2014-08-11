/*
 * Copyright 2013 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.errorprone.refaster;

import com.google.auto.value.AutoValue;

import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.TreeVisitor;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCParens;
import com.sun.tools.javac.tree.TreeInfo;

import javax.annotation.Nullable;

/**
 * {@link UTree} version of {@link ParenthesizedTree}.
 *
 * @author lowasser@google.com (Louis Wasserman)
 */
@AutoValue
public abstract class UParens extends UExpression implements ParenthesizedTree {
  public static UParens create(UExpression expression) {
    return new AutoValue_UParens(expression);
  }

  @Override
  public abstract UExpression getExpression();

  @Override
  @Nullable
  public Unifier unify(JCTree target, @Nullable Unifier unifier) {
    /*
     * During unification, parentheses are liberalized so they don't care if the target expression
     * is parenthesized or not.
     */
    return (unifier == null) ? null : getExpression().unify(TreeInfo.skipParens(target), unifier);
  }

  @Override
  public Kind getKind() {
    return Kind.PARENTHESIZED;
  }

  @Override
  public <R, D> R accept(TreeVisitor<R, D> visitor, D data) {
    return visitor.visitParenthesized(this, data);
  }

  @Override
  public JCParens inline(Inliner inliner) throws CouldNotResolveImportException {
    return inliner.maker().Parens(getExpression().inline(inliner));
  }

  @Override
  public UExpression negate() {
    return create(getExpression().negate());
  }
}
