package com.github.ovargasmahisoft.jdbcplugin.ui;

import com.intellij.psi.PsiPackage;

public interface IOptionsForm {
    boolean getOverwriteIfExists();

    boolean getGenerateDAO();

    PsiPackage getDaoPackage();

    boolean getGenerateRepository();

    PsiPackage getRepositoryPackage();

    boolean getGenerateGet();

    boolean getGenerateGetMany();

    boolean getGenerateCreate();

    boolean getGenerateCreateMany();

    boolean getGenerateUpdate();

    boolean getGenerateUpdateMany();

    boolean getGenerateDelete();

    boolean getGenerateDeleteMany();
}
