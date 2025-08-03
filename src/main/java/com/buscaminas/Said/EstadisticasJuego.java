/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buscaminas.Said;

/**
 * Clase que maneja las estadísticas del juego Buscaminas
 * Universidad Técnica Nacional ITI
 * Programación I - Proyecto Final
 * 
 * @author Said
 * @version 1.0
 * @since 2025-07-25
 */
public class EstadisticasJuego {
    // Atributos para llevar el control de estadísticas
    private int juegosJugados;
    private int juegosGanados;
    private int juegosPerdidos;
    
    /**
     * Constructor de la clase EstadisticasJuego
     * Inicializa todos los contadores en cero
     */
    public EstadisticasJuego() {
        this.juegosJugados = 0;
        this.juegosGanados = 0;
        this.juegosPerdidos = 0;
    }
    
    /**
     * Incrementa el contador de juegos jugados
     */
    public void incrementarJuegosJugados() {
        juegosJugados++;
    }
    
    /**
     * Incrementa el contador de juegos ganados
     */
    public void incrementarJuegosGanados() {
        juegosGanados++;
    }
    
    /**
     * Incrementa el contador de juegos perdidos
     */
    public void incrementarJuegosPerdidos() {
        juegosPerdidos++;
    }
    
    /**
     * Obtiene las estadísticas formateadas como String
     * @return String con las estadísticas completas del juego
     */
    public String obtenerEstadisticas() {
        double porcentajeVictoria = juegosJugados > 0 ? 
            (juegosGanados * 100.0 / juegosJugados) : 0.0;
            
        return String.format(
            "=== ESTADÍSTICAS DEL JUEGO ===\n" +
            "Juegos jugados: %d\n" +
            "Juegos ganados: %d\n" +
            "Juegos perdidos: %d\n" +
            "Porcentaje de victoria: %.1f%%",
            juegosJugados,
            juegosGanados,
            juegosPerdidos,
            porcentajeVictoria
        );
    }
    
    /**
     * Obtiene el número de juegos jugados
     * @return Número total de juegos jugados
     */
    public int getJuegosJugados() { 
        return juegosJugados; 
    }
    
    /**
     * Obtiene el número de juegos ganados
     * @return Número total de juegos ganados
     */
    public int getJuegosGanados() { 
        return juegosGanados; 
    }
    
    /**
     * Obtiene el número de juegos perdidos
     * @return Número total de juegos perdidos
     */
    public int getJuegosPerdidos() { 
        return juegosPerdidos; 
    }
}
