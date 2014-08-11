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

import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.TreeVisitor;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCSkip;

import javax.annotation.Nullable;

/**
 * A {@link UTree} representation of an {@link EmptyStatementTree}
 *
 * @author lowasser@google.com (Louis Wasserman)
 */
public enum USkip implements UStatement, EmptyStatementTree {
  INSTANCE;

  @Override
  public JCSkip inline(Inliner inliner) {
    return inliner.maker().Skip();
  }

  @Override
  public <R, D> R accept(TreeVisitor<R, D> visitor, D data) {
    return visitor.visitEmptyStatement(this, data);
  }

  @Override
  public Kind getKind() {
    return Kind.EMPTY_STATEMENT;
  }

  @Override
  @Nullable
  public Unifier unify(JCTree target, Unifier unifier) {
    return (target instanceof JCSkip) ? unifier : null;
  }

  @Override
  public String toString() {
    return "USkip{}";
  }
}
