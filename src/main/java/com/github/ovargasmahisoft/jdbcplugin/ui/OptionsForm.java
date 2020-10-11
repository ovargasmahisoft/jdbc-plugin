package com.github.ovargasmahisoft.jdbcplugin.ui;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiPackage;

import javax.swing.*;

public class OptionsForm implements IOptionsForm {
    private JPanel mainPanel;
    private JCheckBox overwriteCheckBox;

    private JCheckBox generateDAOCheckBox;
    private JTextField basePackageTextField;
    private JButton basePackageButton;

    private JPanel daoPanel;
    private JCheckBox generateRepositoryCheckBox;
    private JCheckBox generateTestCheckBox;

    private PsiPackage basePackage;

    public OptionsForm(Project project) {

        basePackageButton.addActionListener(e -> {
            basePackage = getPackagePath(project);
            basePackageTextField.setText(basePackage.getQualifiedName());
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public boolean getOverwriteIfExists() {
        return overwriteCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateDAO() {
        return generateDAOCheckBox.isSelected();
    }

    @Override
    public PsiPackage getBasePackage() {
        return basePackage;
    }

    @Override
    public boolean getGenerateRepository() {
        return generateRepositoryCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateTestClass() {
        return generateTestCheckBox.isSelected();
    }

    private PsiPackage getPackagePath(Project project) {
        PackageChooserDialog dialog = new PackageChooserDialog("Select Package", project);
        dialog.show();
        return dialog.getSelectedPackage();
    }

}
