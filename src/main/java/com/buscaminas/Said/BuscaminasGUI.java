/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buscaminas.Said;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que maneja la interfaz gráfica del juego Buscaminas
 * Universidad Técnica Nacional ITI
 * Programación I - Proyecto Final
 * 
 * @author Said
 * @version 1.0
 * @since 2025-07-27
 */
public class BuscaminasGUI extends JFrame {
    // Componentes de la interfaz gráfica
    private BuscaminasModelo modelo;
    private JButton[][] botones;
    private JLabel labelContadorMinas;
    private JLabel labelEstado;
    private JPanel panelTablero;
    
  /**
     * Constructor de la interfaz gráfica
     * Inicializa la ventana y muestra las estadísticas iniciales
     */
    public BuscaminasGUI() {
        mostrarEstadisticasIniciales();
        iniciarNuevoJuego();
    }
    
    /**
     * Muestra las estadísticas al inicio del programa
     */
    private void mostrarEstadisticasIniciales() {
        EstadisticasJuego estadisticasIniciales = new EstadisticasJuego();
        JOptionPane.showMessageDialog(this, 
            estadisticasIniciales.obtenerEstadisticas(), 
            "Estadísticas Iniciales", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Inicia un nuevo juego solicitando el tamaño del tablero al usuario
     */
    private void iniciarNuevoJuego() {
        int tamaño = solicitarTamañoTablero();
        if (tamaño > 0) {
            modelo = new BuscaminasModelo(tamaño);
            configurarInterfaz();
        } else {
            // Usuario canceló, cerrar aplicación
            System.exit(0);
        }
    }
    
    /**
     * Solicita al usuario el tamaño del tablero con validación
     * @return Tamaño del tablero válido, o -1 si el usuario cancela
     */
    private int solicitarTamañoTablero() {
        while (true) {
            String input = JOptionPane.showInputDialog(this,
                "Ingrese el tamaño del lado del tablero (mayor a 2):",
                "Nuevo Juego",
                JOptionPane.QUESTION_MESSAGE);
            
            // Usuario canceló el diálogo
            if (input == null) {
                return -1;
            }
            
            try {
                int tamaño = Integer.parseInt(input.trim());
                if (tamaño > 2) {
                    return tamaño;
                } else {
                    JOptionPane.showMessageDialog(this,
                        "El tamaño debe ser mayor a 2. Intente nuevamente.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un número válido.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Configura todos los componentes de la interfaz gráfica principal
     */
    private void configurarInterfaz() {
        // Configuración básica de la ventana
        setTitle("Buscaminas - Universidad Técnica Nacional ITI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        // Panel superior con información del juego
        crearPanelSuperior();
        
        // Panel central con el tablero de juego
        crearPanelTablero();
        
        // Panel inferior con botones de menú
        crearPanelInferior();
        
        // Finalizar configuración de ventana
        pack();
        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);
    }
    
    /**
     * Crea el panel superior con información del juego
     */
    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout());
        
        // Etiqueta para mostrar minas restantes
        labelContadorMinas = new JLabel("Minas restantes: " + modelo.getNumeroMinas());
        labelContadorMinas.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Etiqueta para mostrar estado del juego
        labelEstado = new JLabel("¡Buena suerte!");
        labelEstado.setFont(new Font("Arial", Font.BOLD, 14));
        labelEstado.setForeground(Color.BLUE);
        
        panelSuperior.add(labelContadorMinas);
        panelSuperior.add(Box.createHorizontalStrut(20)); // Espaciador
        panelSuperior.add(labelEstado);
        
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    /**
     * Crea el panel del tablero con la grilla de botones
     */
    private void crearPanelTablero() {
        panelTablero = new JPanel(new GridLayout(modelo.getTamaño(), modelo.getTamaño(), 2, 2));
        panelTablero.setBorder(BorderFactory.createLoweredBevelBorder());
        panelTablero.setBackground(Color.GRAY);
        
        botones = new JButton[modelo.getTamaño()][modelo.getTamaño()];
        crearBotones();
        
        add(panelTablero, BorderLayout.CENTER);
    }
    
    /**
     * Crea el panel inferior con botones de menú
     */
    private void crearPanelInferior() {
        JPanel panelInferior = new JPanel(new FlowLayout());
        
        // Botón para nuevo juego
        JButton btnNuevoJuego = new JButton("Nuevo Juego");
        btnNuevoJuego.setFont(new Font("Arial", Font.BOLD, 12));
        btnNuevoJuego.addActionListener(e -> {
            dispose(); // Cerrar ventana actual
            new BuscaminasGUI(); // Crear nueva instancia
        });
    }
    
    
    
}