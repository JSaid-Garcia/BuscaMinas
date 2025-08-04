/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buscaminas.Said;
import javax.swing.*;

/**
 * Clase principal del proyecto Buscaminas
 * Universidad Técnica Nacional ITI
 * Programación I - Proyecto Final
 * 
 * @author Said Garcia Gonzalez
 * @version 1.0
 * @since 2025-07-27
 */
public class Main {
    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer el look and feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Crear e iniciar la interfaz gráfica
            new BuscaminasGUI();
        });
    }
}
