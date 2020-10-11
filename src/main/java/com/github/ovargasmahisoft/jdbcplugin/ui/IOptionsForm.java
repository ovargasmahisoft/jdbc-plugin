package com.github.ovargasmahisoft.jdbcplugin.ui;

import com.intellij.psi.PsiPackage;

public interface IOptionsForm {
    boolean getOverwriteIfExists();

    boolean getGenerateDAO();


    boolean getGenerateRepository();

    boolean getGenerateTestClass();

    PsiPackage getBasePackage();


}
