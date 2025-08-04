/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
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
 * @author Said Garcia Gonzalez
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
        
        // Botón para salir del juego
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalir.addActionListener(e -> {
            mostrarEstadisticasFinales();
            System.exit(0);
        });
        
        panelInferior.add(btnNuevoJuego);
        panelInferior.add(Box.createHorizontalStrut(10)); // Espaciador
        panelInferior.add(btnSalir);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    /**
     * Crea todos los botones del tablero con sus event listeners
     */
    private void crearBotones() {
        for (int i = 0; i < modelo.getTamaño(); i++) {
            for (int j = 0; j < modelo.getTamaño(); j++) {
                JButton boton = new JButton();
                
                // Configuración visual del botón
                boton.setPreferredSize(new Dimension(40, 40));
                boton.setFont(new Font("Arial", Font.BOLD, 12));
                boton.setFocusPainted(false);
                boton.setBorder(BorderFactory.createRaisedBevelBorder());
                
                // Variables finales para usar en el listener
                final int fila = i;
                final int columna = j;
                
                // Event listener para manejar clics del mouse
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        manejarClicBoton(e, fila, columna);
                    }
                });
                
                botones[i][j] = boton;
                panelTablero.add(boton);
            }
        }
    }
    
    /**
     * Maneja los clics en los botones del tablero
     * @param e Evento del mouse
     * @param fila Fila del botón clicado
     * @param columna Columna del botón clicado
     */
    private void manejarClicBoton(MouseEvent e, int fila, int columna) {
        // No procesar clics si el juego ya terminó
        if (modelo.isJuegoTerminado()) {
            return;
        }
        
        boolean accionRealizada = false;
        
        if (SwingUtilities.isLeftMouseButton(e)) {
            // Clic izquierdo - destapar casilla
            accionRealizada = modelo.destapar(fila, columna);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            // Clic derecho - marcar/desmarcar casilla
            accionRealizada = modelo.marcar(fila, columna);
        }
        
        // Actualizar interfaz si se realizó alguna acción
        if (accionRealizada) {
            actualizarInterfaz();
            verificarEstadoJuego();
        }
    }
    
    /**
     * Actualiza todos los componentes de la interfaz gráfica
     */
    private void actualizarInterfaz() {
        actualizarContadorMinas();
        actualizarBotones();
    }
    
    /**
     * Actualiza el contador de minas restantes
     */
    private void actualizarContadorMinas() {
        int minasRestantes = modelo.getNumeroMinas() - modelo.getContadorMarcas();
        labelContadorMinas.setText("Minas restantes: " + minasRestantes);
    }
    
    /**
     * Actualiza la apariencia de todos los botones según el estado del juego
     */
    private void actualizarBotones() {
        for (int i = 0; i < modelo.getTamaño(); i++) {
            for (int j = 0; j < modelo.getTamaño(); j++) {
                JButton boton = botones[i][j];
                
                if (modelo.estaMarcada(i, j)) {
                    // Casilla marcada
                    configurarBotonMarcado(boton);
                } else if (modelo.estaDestapada(i, j)) {
                    // Casilla destapada
                    if (modelo.esMina(i, j)) {
                        configurarBotonMina(boton);
                    } else {
                        configurarBotonDestapado(boton, i, j);
                    }
                } else {
                    // Casilla sin destapar
                    configurarBotonNormal(boton);
                }
            }
        }
    }
    
    /**
     * Configura la apariencia de un botón marcado
     * @param boton Botón a configurar
     */
    private void configurarBotonMarcado(JButton boton) {
        boton.setText("X");
        boton.setBackground(Color.YELLOW);
        boton.setForeground(Color.RED);
        boton.setEnabled(true);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
    }
    
    /**
     * Configura la apariencia de un botón que contiene una mina
     * @param boton Botón a configurar
     */
    private void configurarBotonMina(JButton boton) {
        boton.setText("💣");
        boton.setBackground(Color.RED);
        boton.setForeground(Color.BLACK);
        boton.setEnabled(false);
        boton.setBorder(BorderFactory.createLoweredBevelBorder());
    }
    
    /**
     * Configura la apariencia de un botón destapado (sin mina)
     * @param boton Botón a configurar
     * @param fila Fila del botón
     * @param columna Columna del botón
     */
    private void configurarBotonDestapado(JButton boton, int fila, int columna) {
        int vecinasCount = modelo.getNumeroVecinas(fila, columna);
        
        boton.setText(vecinasCount > 0 ? String.valueOf(vecinasCount) : "");
        boton.setBackground(Color.LIGHT_GRAY);
        boton.setEnabled(false);
        boton.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Asignar colores específicos según el número de minas vecinas
        if (vecinasCount > 0) {
            Color[] coloresNumeros = {
                Color.BLUE,      // 1
                Color.GREEN,     // 2
                Color.RED,       // 3
                Color.MAGENTA,   // 4
                Color.CYAN,      // 5
                Color.ORANGE,    // 6
                Color.PINK,      // 7
                Color.BLACK      // 8
            };
            
            int indiceColor = Math.min(vecinasCount - 1, coloresNumeros.length - 1);
            boton.setForeground(coloresNumeros[indiceColor]);
        }
    }
    
    /**
     * Configura la apariencia de un botón normal (sin destapar)
     * @param boton Botón a configurar
     */
    private void configurarBotonNormal(JButton boton) {
        boton.setText("");
        boton.setBackground(null);
        boton.setForeground(Color.BLACK);
        boton.setEnabled(true);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
    }
    
    /**
     * Verifica el estado del juego y muestra mensajes apropiados
     */
    private void verificarEstadoJuego() {
        if (modelo.isJuegoTerminado()) {
            if (modelo.isJuegoGanado()) {
                manejarVictoria();
            } else {
                manejarDerrota();
            }
            
            mostrarEstadisticasFinales();
            preguntarNuevoJuego();
        }
    }
    
    /**
     * Maneja el caso cuando el jugador gana
     */
    private void manejarVictoria() {
        labelEstado.setText("¡GANASTE!");
        labelEstado.setForeground(Color.GREEN);
        
        JOptionPane.showMessageDialog(this,
            "¡Felicitaciones! Has ganado el juego.\n" +
            "Todas las minas fueron marcadas correctamente.",
            "¡Victoria!",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Maneja el caso cuando el jugador pierde
     */
    private void manejarDerrota() {
        labelEstado.setText("¡PERDISTE!");
        labelEstado.setForeground(Color.RED);
        
        mostrarTodasLasMinas();
        
        JOptionPane.showMessageDialog(this,
            "¡Oh no! Has pisado una mina.\n" +
            "El juego ha terminado.",
            "Juego Terminado",
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra todas las minas en el tablero cuando el juego termina por derrota
     */
    private void mostrarTodasLasMinas() {
        for (int i = 0; i < modelo.getTamaño(); i++) {
            for (int j = 0; j < modelo.getTamaño(); j++) {
                if (modelo.esMina(i, j) && !modelo.estaDestapada(i, j)) {
                    JButton boton = botones[i][j];
                    boton.setText("💣");
                    boton.setBackground(Color.ORANGE);
                    boton.setForeground(Color.BLACK);
                    boton.setEnabled(false);
                    boton.setBorder(BorderFactory.createLoweredBevelBorder());
                }
            }
        }
    }
    
    /**
     * Muestra las estadísticas finales del juego
     */
    private void mostrarEstadisticasFinales() {
        JOptionPane.showMessageDialog(this,
            modelo.getEstadisticas().obtenerEstadisticas(),
            "Estadísticas del Juego",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Pregunta al usuario si desea jugar nuevamente
     */
    private void preguntarNuevoJuego() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Deseas jugar nuevamente?",
            "Nuevo Juego",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            dispose(); // Cerrar ventana actual
            new BuscaminasGUI(); // Crear nueva ventana
        } else {
            // Mostrar estadísticas finales antes de cerrar
            mostrarEstadisticasFinales();
            System.exit(0);
        }
    }
}