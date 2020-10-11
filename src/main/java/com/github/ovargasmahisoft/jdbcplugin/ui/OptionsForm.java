package com.github.ovargasmahisoft.jdbcplugin.ui;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiPackage;

import javax.swing.*;

public class OptionsForm implements IOptionsForm {
    private JPanel mainPanel;
    private JCheckBox overwriteCheckBox;

    private JCheckBox generateDAOCheckBox;
    private JTextField daoPackageTextField;
    private JButton daoPackageButton;

    private JPanel daoPanel;
    private JCheckBox generateRepositoryCheckBox;
    private JTextField repositoryPackageTextField;
    private JButton repositoryPackageButton;

    private JCheckBox getCheckBox;
    private JCheckBox getManyCheckBox;
    private JCheckBox createCheckBox;
    private JCheckBox createManyCheckBox;
    private JCheckBox updateCheckBox;
    private JCheckBox updateManyCheckBox;
    private JCheckBox deleteCheckBox;
    private JCheckBox deleteManyCheckBox;
    private PsiPackage daoPackage;
    private PsiPackage repositoryPackage;

    public OptionsForm(Project project) {

        daoPackageButton.addActionListener(e -> {
            daoPackage = getPackagePath(project);
            daoPackageTextField.setText(daoPackage.getQualifiedName());
        });

        repositoryPackageButton.addActionListener(e -> {
            repositoryPackage = getPackagePath(project);
            repositoryPackageTextField.setText(daoPackage.getQualifiedName());
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
    public PsiPackage getDaoPackage() {
        return daoPackage;
    }

    @Override
    public boolean getGenerateRepository() {
        return generateRepositoryCheckBox.isSelected();
    }

    @Override
    public PsiPackage getRepositoryPackage() {
        return repositoryPackage;
    }

    @Override
    public boolean getGenerateGet() {
        return getCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateGetMany() {
        return getManyCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateCreate() {
        return createCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateCreateMany() {
        return createManyCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateUpdate() {
        return updateCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateUpdateMany() {
        return updateManyCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateDelete() {
        return deleteCheckBox.isSelected();
    }

    @Override
    public boolean getGenerateDeleteMany() {
        return deleteManyCheckBox.isSelected();
    }

    private PsiPackage getPackagePath(Project project) {
        PackageChooserDialog dialog = new PackageChooserDialog("Select Package", project);
        dialog.show();
        return dialog.getSelectedPackage();
    }

}
