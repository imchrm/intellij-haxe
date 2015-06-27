/*
 * Copyright 2000-2013 JetBrains s.r.o.
 * Copyright 2014-2015 AS3Boyan
 * Copyright 2014-2014 Elias Ku
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.plugins.haxe.model;

import com.intellij.plugins.haxe.lang.psi.HaxeImportStatementRegular;
import com.intellij.plugins.haxe.lang.psi.HaxeReferenceExpression;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HaxeImportsModel {
  @NotNull
  public final HaxeFileModel file;

  public HaxeImportsModel(@NotNull HaxeFileModel file) {
    this.file = file;
  }

  public List<HaxeClassModel> getImportedClasses() {
    LinkedList<HaxeClassModel> classes = new LinkedList<HaxeClassModel>();

    classes.add(file.getProject().getClassFromFqName("Std"));

    for (HaxeImportModel importModel : getImports()) {
      HaxeClassModel importedClass = importModel.getImportedClass();
      if (importedClass != null) {
        classes.add(importedClass);
      }
    }
    return classes;
  }

  public List<HaxeImportModel> getImports() {
    ArrayList<HaxeImportModel> imports = new ArrayList<HaxeImportModel>();
    for (PsiElement element : file.file.getChildren()) {
      if (element instanceof HaxeImportStatementRegular) {
        imports.add(new HaxeImportModel((HaxeImportStatementRegular)element));
      }
    }
    return imports;
  }
}
