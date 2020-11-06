package com.github.ovargasmahisoft.jdbcplugin.ui;

@FunctionalInterface
public interface OnRestoreDefaultFunction {

    void onRestore(JdbcConfigurationComponent component);
}
