package ar.edu.unahur.obj2.excepciones;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.excepciones.modos.*;

public class ModosTest {
    private Modo eficiente = new ModoEficiente();
    private Modo normal = new ModoNormal();
    private Modo agresivo = new ModoAgresivo();

    @Test
    void ModoEficienteTieneRendimiento14KmYVelocidadMaxima180() {
        assertEquals(eficiente.getNombre(), "Eficiente");
        assertEquals(eficiente.getConsumoPorLitro(), 14.0);
        assertEquals(eficiente.getVelocidadMax(), 180);
    }

    @Test
    void ModoNormalTieneRendimiento9KmYVelocidadMaxima220() {
        assertEquals(normal.getNombre(), "Normal");
        assertEquals(normal.getConsumoPorLitro(), 9.0);
        assertEquals(normal.getVelocidadMax(), 220);
    }

    @Test
    void ModoAgresivoieneRendimiento6KmYVelocidadMaxima280() {
        assertEquals(agresivo.getNombre(), "Agresivo");
        assertEquals(agresivo.getConsumoPorLitro(), 6.0);
        assertEquals(agresivo.getVelocidadMax(), 280);
    }
}
