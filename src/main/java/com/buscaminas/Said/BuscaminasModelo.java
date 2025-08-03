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
 * @author Said
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
     *
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
     * Coloca las minas aleatoriamente en el tablero Garantiza que no haya más
     * de una mina por casilla
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
     *
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
     *
     * @param fila Fila a verificar
     * @param columna Columna a verificar
     * @return true si la coordenada está dentro de los límites del tablero
     */
    private boolean esValida(int fila, int columna) {
        return fila >= 0 && fila < tamaño && columna >= 0 && columna < tamaño;
    }
}
