package com.github.ovargasmahisoft.jdbcplugin.ui;

import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextField

interface IOptionsForm {
    fun overwriteIfExists(): Boolean
    fun generateDAO(): Boolean
    fun daoPackagePath(): String?
    fun generateRepository(): Boolean
    fun repositoryPackagePath(): String?
    fun generateGet(): Boolean
    fun generateGetMany(): Boolean
    fun generateCreate(): Boolean
    fun generateCreateMany(): Boolean
    fun generateUpdate(): Boolean
    fun generateUpdateMany(): Boolean
    fun generateDelete(): Boolean
    fun generateDeleteMany(): Boolean
}

class OptionsForm : IOptionsForm {
    lateinit var mainPanel: JPanel
    lateinit var overwriteCheckBox: JCheckBox

    lateinit var generateDAOCheckBox: JCheckBox
    lateinit var daoPackageTextField: JTextField
    lateinit var daoPackageButton: JButton

    lateinit var daoPanel: JPanel
    lateinit var generateRepositoryCheckBox: JCheckBox
    lateinit var repositoryPackageTextField: JTextField
    lateinit var repositoryPackageButton: JButton

    lateinit var getCheckBox: JCheckBox
    lateinit var getManyCheckBox: JCheckBox
    lateinit var createCheckBox: JCheckBox
    lateinit var createManyCheckBox: JCheckBox
    lateinit var updateCheckBox: JCheckBox
    lateinit var updateManyCheckBox: JCheckBox
    lateinit var deleteCheckBox: JCheckBox
    lateinit var deleteManyCheckBox: JCheckBox

    fun mainPanel() = mainPanel

    override fun overwriteIfExists() = overwriteCheckBox.isSelected

    override fun generateDAO() = generateDAOCheckBox.isSelected
    override fun daoPackagePath() = daoPackageTextField.text

    override fun generateRepository() = generateRepositoryCheckBox.isSelected
    override fun repositoryPackagePath() = repositoryPackageTextField.text

    override fun generateGet() = getCheckBox.isSelected
    override fun generateGetMany() = getManyCheckBox.isSelected

    override fun generateCreate() = createCheckBox.isSelected
    override fun generateCreateMany() = createManyCheckBox.isSelected

    override fun generateUpdate() = updateCheckBox.isSelected
    override fun generateUpdateMany() = updateManyCheckBox.isSelected

    override fun generateDelete() = deleteCheckBox.isSelected
    override fun generateDeleteMany() = deleteManyCheckBox.isSelected
}
