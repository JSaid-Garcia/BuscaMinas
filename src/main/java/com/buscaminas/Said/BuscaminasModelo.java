/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buscaminas.Said;

import java.util.Random;

/**
 * Clase que maneja la lógica del juego Buscaminas Universidad Técnica Nacional
 * ITI Programación I - Proyecto Final
 *
 * @author Jainer Said
 * @version 1.0
 * @since 2025-07-26
 */
public class BuscaminasModelo {
    // Atributos del modelo del juego
    private int tamaño;
    private int numeroMinas;
    private boolean[][] minas;
    private boolean[][] destapadas;
    private boolean[][] marcadas;
    private int[][] numeroVecinas;
    private boolean juegoTerminado;
    private boolean juegoGanado;
    private EstadisticasJuego estadisticas;
    
    /**
     * Constructor del modelo de Buscaminas
     * @param tamaño Tamaño del lado del tablero cuadrado (debe ser mayor a 2)
     */
    public BuscaminasModelo(int tamaño) {
        this.tamaño = tamaño;
        this.numeroMinas = 2 * tamaño; // Según especificación: 2*L minas
        this.estadisticas = new EstadisticasJuego();
        inicializarJuego();
    }
    
    /**
     * Inicializa un nuevo juego con todas las estructuras de datos
     */
    public void inicializarJuego() {
        // Inicializar matrices del juego
        minas = new boolean[tamaño][tamaño];
        destapadas = new boolean[tamaño][tamaño];
        marcadas = new boolean[tamaño][tamaño];
        numeroVecinas = new int[tamaño][tamaño];
        
        // Inicializar estado del juego
        juegoTerminado = false;
        juegoGanado = false;
        
        // Configurar el tablero
        colocarMinas();
        calcularNumeroVecinas();
        estadisticas.incrementarJuegosJugados();
    }
    
    /**
     * Coloca las minas aleatoriamente en el tablero
     * Garantiza que no haya más de una mina por casilla
     */
    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;
        
        // Colocar minas hasta alcanzar el número requerido
        while (minasColocadas < numeroMinas) {
            int fila = random.nextInt(tamaño);
            int columna = random.nextInt(tamaño);
            
            // Solo colocar mina si la casilla está vacía
            if (!minas[fila][columna]) {
                minas[fila][columna] = true;
                minasColocadas++;
            }
        }
    }
    
    /**
     * Calcula el número de minas vecinas para cada casilla del tablero
     */
    private void calcularNumeroVecinas() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (!minas[i][j]) {
                    numeroVecinas[i][j] = contarMinasVecinas(i, j);
                }
            }
        }
    }
    
    /**
     * Cuenta las minas vecinas de una casilla específica
     * @param fila Fila de la casilla a evaluar
     * @param columna Columna de la casilla a evaluar
     * @return Número de minas en las 8 casillas adyacentes
     */
    private int contarMinasVecinas(int fila, int columna) {
        int contador = 0;
        
        // Revisar las 8 casillas adyacentes
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                
                if (esValida(nuevaFila, nuevaColumna) && minas[nuevaFila][nuevaColumna]) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    /**
     * Verifica si una coordenada es válida dentro del tablero
     * @param fila Fila a verificar
     * @param columna Columna a verificar
     * @return true si la coordenada está dentro de los límites del tablero
     */
    private boolean esValida(int fila, int columna) {
        return fila >= 0 && fila < tamaño && columna >= 0 && columna < tamaño;
    }
    
    /**
     * Destapa una casilla del tablero
     * @param fila Fila de la casilla a destapar
     * @param columna Columna de la casilla a destapar
     * @return true si la operación fue exitosa, false si no se pudo realizar
     */
    public boolean destapar(int fila, int columna) {
        // Validar que la operación sea posible
        if (!esValida(fila, columna) || destapadas[fila][columna] || 
            marcadas[fila][columna] || juegoTerminado) {
            return false;
        }
        
        // Destapar la casilla
        destapadas[fila][columna] = true;
        
        // Verificar si se destapó una mina
        if (minas[fila][columna]) {
            juegoTerminado = true;
            juegoGanado = false;
            estadisticas.incrementarJuegosPerdidos();
            return true;
        }
        
        // Si no hay minas vecinas, destapar automáticamente las casillas adyacentes
        if (numeroVecinas[fila][columna] == 0) {
            destaparVecinas(fila, columna);
        }
        
        verificarVictoria();
        return true;
    }
    
    /**
     * Destapa recursivamente las casillas vecinas cuando no hay minas alrededor
     * Implementa la funcionalidad de "flood fill" del Buscaminas tradicional
     * @param fila Fila de la casilla desde donde expandir
     * @param columna Columna de la casilla desde donde expandir
     */
    private void destaparVecinas(int fila, int columna) {
        // Revisar las 8 casillas adyacentes
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                
                // Validar y destapar casillas válidas
                if (esValida(nuevaFila, nuevaColumna) && 
                    !destapadas[nuevaFila][nuevaColumna] && 
                    !marcadas[nuevaFila][nuevaColumna] && 
                    !minas[nuevaFila][nuevaColumna]) {
                    
                    destapadas[nuevaFila][nuevaColumna] = true;
                    
                    // Continuar expansión si no hay minas vecinas
                    if (numeroVecinas[nuevaFila][nuevaColumna] == 0) {
                        destaparVecinas(nuevaFila, nuevaColumna);
                    }
                }
            }
        }
    }
    
    /**
     * Marca o desmarca una casilla
     * @param fila Fila de la casilla a marcar/desmarcar
     * @param columna Columna de la casilla a marcar/desmarcar
     * @return true si la operación fue exitosa, false si no se pudo realizar
     */
    public boolean marcar(int fila, int columna) {
        // Validar que la operación sea posible
        if (!esValida(fila, columna) || destapadas[fila][columna] || juegoTerminado) {
            return false;
        }
        
        // Verificar límite de marcas (no puede superar el número de minas)
        if (!marcadas[fila][columna] && contarMarcas() >= numeroMinas) {
            return false;
        }
        
        // Alternar el estado de marca
        marcadas[fila][columna] = !marcadas[fila][columna];
        verificarVictoria();
        return true;
    }
    
    /**
     * Cuenta el número de casillas marcadas actualmente
     * @return Número total de marcas colocadas en el tablero
     */
    private int contarMarcas() {
        int contador = 0;
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (marcadas[i][j]) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    /**
     * Verifica si el jugador ha ganado el juego
     * Condición de victoria: todas las minas están marcadas y no hay marcas incorrectas
     */
    private void verificarVictoria() {
        if (juegoTerminado) return;
        
        int minasCorrectasMarcadas = 0;
        int marcasIncorrectas = 0;
        
        // Contar marcas correctas e incorrectas
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (marcadas[i][j]) {
                    if (minas[i][j]) {
                        minasCorrectasMarcadas++;
                    } else {
                        marcasIncorrectas++;
                    }
                }
            }
        }
        
        // Verificar condición de victoria
        if (minasCorrectasMarcadas == numeroMinas && marcasIncorrectas == 0) {
            juegoTerminado = true;
            juegoGanado = true;
            estadisticas.incrementarJuegosGanados();
        }
    }
    
    // Métodos getter para acceder a los atributos del modelo
    
    /**
     * @return Tamaño del lado del tablero
     */
    public int getTamaño() { 
        return tamaño; 
    }
    
    /**
     * @return Número total de minas en el tablero
     */
    public int getNumeroMinas() { 
        return numeroMinas; 
    }
    
    /**
     * Verifica si hay una mina en las coordenadas especificadas
     * @param fila Fila a verificar
     * @param columna Columna a verificar
     * @return true si hay una mina en esa posición
     */
    public boolean esMina(int fila, int columna) { 
        return minas[fila][columna]; 
    }
    
    /**
     * Verifica si una casilla está destapada
     * @param fila Fila a verificar
     * @param columna Columna a verificar
     * @return true si la casilla está destapada
     */
    public boolean estaDestapada(int fila, int columna) { 
        return destapadas[fila][columna]; 
    }
    
    /**
     * Verifica si una casilla está marcada
     * @param fila Fila a verificar
     * @param columna Columna a verificar
     * @return true si la casilla está marcada
     */
    public boolean estaMarcada(int fila, int columna) { 
        return marcadas[fila][columna]; 
    }
    
    /**
     * Obtiene el número de minas vecinas de una casilla
     * @param fila Fila de la casilla
     * @param columna Columna de la casilla
     * @return Número de minas en las casillas adyacentes
     */
    public int getNumeroVecinas(int fila, int columna) { 
        return numeroVecinas[fila][columna]; 
    }
    
    /**
     * @return true si el juego ha terminado (ganado o perdido)
     */
    public boolean isJuegoTerminado() { 
        return juegoTerminado; 
    }
    
    /**
     * @return true si el juego terminó con victoria
     */
    public boolean isJuegoGanado() { 
        return juegoGanado; 
    }
    
    /**
     * @return Objeto con las estadísticas del juego
     */
    public EstadisticasJuego getEstadisticas() { 
        return estadisticas; 
    }
    
    /**
     * @return Número actual de marcas colocadas
     */
    public int getContadorMarcas() { 
        return contarMarcas(); 
    }
}
 