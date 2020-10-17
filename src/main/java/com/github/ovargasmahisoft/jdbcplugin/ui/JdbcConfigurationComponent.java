package com.github.ovargasmahisoft.jdbcplugin.ui;

import com.google.common.base.Strings;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcConfigurationComponent {
    private JTextField doTemplateTF;
    private JPanel mainPanel;
    private JTextField doPrefixTF;
    private JTextField doSuffixTF;
    private JTextField repoPrefixTF;
    private JTextField repoSuffixTF;
    private JTextField implPrefixTF;
    private JTextField implSuffixTF;
    private JButton button1;
    private JTextField repoTemplateTF;
    private JTextField implTemplateTF;
    private JButton button2;
    private JButton button3;
    private JTextField testTemplateTF;
    private JButton button4;
    private JButton restoreToDefaultButton;
    private JPanel templatesPanel;
    private JPanel conventionPanel;
    private JTextField doPackageTF;
    private JTextField repoPackageTF;
    private JTextField implPackageTF;
    private JPanel packagesPanel;
    private JCheckBox defaultDoTemplateCB;
    private JCheckBox defaultRepoTemplateCB;
    private JCheckBox defaultImplTemplateCB;
    private JCheckBox defaultTestTemplateCB;
    private JLabel doExampleLbl;
    private JLabel repoExampleLbl;
    private JLabel implExampleLbl;
    private JLabel doPackageLbl;
    private JLabel repoPackageLbl;
    private JLabel implPackageLbl;

    public JdbcConfigurationComponent() {
        conventionPanel.setBorder(BorderFactory.createTitledBorder("Naming convention"));
        templatesPanel.setBorder(BorderFactory.createTitledBorder("Templates"));
        packagesPanel.setBorder(BorderFactory.createTitledBorder("Packages"));

        FocusListener onFocusListener = onFocus();

        doPrefixTF.addFocusListener(onFocusListener);
        doSuffixTF.addFocusListener(onFocusListener);
        implPrefixTF.addFocusListener(onFocusListener);
        implSuffixTF.addFocusListener(onFocusListener);
        repoPrefixTF.addFocusListener(onFocusListener);
        repoSuffixTF.addFocusListener(onFocusListener);
        doPackageTF.addFocusListener(onFocusListener);
        repoPackageTF.addFocusListener(onFocusListener);
        implPackageTF.addFocusListener(onFocusListener);

        doPrefixTF.addPropertyChangeListener(this::OnChange);
        doSuffixTF.addPropertyChangeListener(this::OnChange);
        implPrefixTF.addPropertyChangeListener(this::OnChange);
        implSuffixTF.addPropertyChangeListener(this::OnChange);
        repoPrefixTF.addPropertyChangeListener(this::OnChange);
        repoSuffixTF.addPropertyChangeListener(this::OnChange);
        doPackageTF.addPropertyChangeListener(this::OnChange);
        repoPackageTF.addPropertyChangeListener(this::OnChange);
        implPackageTF.addPropertyChangeListener(this::OnChange);

    }


    public JComponent getPreferredFocusedComponent() {
        return doTemplateTF;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    /*
        Naming convention properties 
    */

    public String getDoPrefix() {
        return doPrefixTF.getText();
    }

    public void setDoPrefix(String value) {
        doPrefixTF.setText(value);
    }

    public String getDoSuffix() {
        return doSuffixTF.getText();
    }

    public void setDoSuffix(String value) {
        doSuffixTF.setText(value);
    }

    public String getRepoPrefix() {
        return repoPrefixTF.getText();
    }

    public void setRepoPrefix(String value) {
        repoPrefixTF.setText(value);
    }

    public String getRepoSuffix() {
        return repoSuffixTF.getText();
    }

    public void setRepoSuffix(String value) {
        repoSuffixTF.setText(value);
    }

    public String getImplPrefix() {
        return implPrefixTF.getText();
    }

    public void setImplPrefix(String value) {
        implPrefixTF.setText(value);
    }

    public String getImplSuffix() {
        return implSuffixTF.getText();
    }

    public void setImplSuffix(String value) {
        implSuffixTF.setText(value);
    }

    /*
        Template settings
    */

    public boolean getUseDefaultDoTemplate() {
        return defaultDoTemplateCB.isSelected();
    }

    public void setUseDefaultDoTemplate(boolean value) {
        defaultDoTemplateCB.setSelected(value);
    }

    public boolean getUseDefaultRepoTemplate() {
        return defaultRepoTemplateCB.isSelected();
    }

    public void setUseDefaultRepoTemplate(boolean value) {
        defaultRepoTemplateCB.setSelected(value);
    }

    public boolean getUseDefaultImplTemplate() {
        return defaultImplTemplateCB.isSelected();
    }

    public void setUseDefaultImplTemplate(boolean value) {
        defaultImplTemplateCB.setSelected(value);
    }

    public String getDoTemplate() {
        return doTemplateTF.getText();
    }

    public void setDoTemplate(String value) {
        doTemplateTF.setText(value);
    }

    public String getRepoTemplate() {
        return repoTemplateTF.getText();
    }

    public void setRepoTemplate(String value) {
        repoTemplateTF.setText(value);
    }

    public String getImplTemplate() {
        return implTemplateTF.getText();
    }

    public void setImplTemplate(String value) {
        implTemplateTF.setText(value);
    }
    
    /*
        Package
    */

    public String getDoPackage() {
        return doPackageTF.getText();
    }

    public void setDoPackage(String value) {
        doPackageTF.setText(value);
    }

    public String getRepoPackage() {
        return repoPackageTF.getText();
    }

    public void setRepoPackage(String value) {
        repoPackageTF.setText(value);
    }

    public String getImplPackage() {
        return implPackageTF.getText();
    }

    public void setImplPackage(String value) {
        implPackageTF.setText(value);
    }


    private void refresh() {
        doExampleLbl.setText(getDoPrefix() + "Product" + getDoSuffix());
        repoExampleLbl.setText(getRepoPrefix() + "Product" + getRepoSuffix());
        implExampleLbl.setText(getImplPrefix() + "Product" + getImplSuffix());

        doPackageLbl.setText(List.of("my.group.basepackage",
                getDoPackage()).stream().filter(x -> !Strings.isNullOrEmpty(x))
                .collect(Collectors.joining(".")));
        repoPackageLbl.setText(List.of("my.group.basepackage",
                getRepoPackage()).stream().filter(x -> !Strings.isNullOrEmpty(x))
                .collect(Collectors.joining(".")));
        implPackageLbl.setText(List.of("my.group.basepackage",
                getImplPackage()).stream().filter(x -> !Strings.isNullOrEmpty(x))
                .collect(Collectors.joining(".")));
    }

    private void OnChange(PropertyChangeEvent e) {
        refresh();
    }

    private FocusListener onFocus() {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                refresh();
            }

            @Override
            public void focusLost(FocusEvent e) {
                refresh();
            }
        };
    }

}
