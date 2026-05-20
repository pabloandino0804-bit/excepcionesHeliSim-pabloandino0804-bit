package ar.edu.unahur.obj2.excepciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.excepciones.helicopteros.Helicoptero;
import ar.edu.unahur.obj2.excepciones.helicopteros.HelicopteroCivil;
import ar.edu.unahur.obj2.excepciones.helicopteros.HelicopteroMilitar;
import ar.edu.unahur.obj2.excepciones.modos.*;
import ar.edu.unahur.obj2.excepciones.misExepciones.EstadoInvalidoException;
import ar.edu.unahur.obj2.excepciones.misExepciones.MisionAbortadaException;
import ar.edu.unahur.obj2.excepciones.misExepciones.UsoDeReservaException;


public class HelicopteroTest {

    private Helicoptero heliCivil;
    private Helicoptero heliMilitar;

    @BeforeEach
    void setUp(){
        heliCivil = new HelicopteroCivil(100.0, 350.0);
        heliMilitar = new HelicopteroMilitar(200.0, 350.0);
    }
    
    /*Tests constructores*/
    @Test
    void dadoCombustibleNegativo_CuandoSeCreaUnHeliCivil_SeLanzaraElEstadoInvalido() {
       assertThrows(
            EstadoInvalidoException.class, 
            () -> new HelicopteroCivil(-1.1, 200.0)
        );
    }
    
    @Test
    void dadoCombustibleNegativo_CuandoSeCreaUnHeliMilitar_SeLanzaraElEstadoInvalido() {
       assertThrows(EstadoInvalidoException.class, () -> new HelicopteroMilitar(-1.1, 200.0));
    }

    /*Tests de getters */
    @Test
    void cuandoSeConsultaSuCombustibleElHelicopteroDevuelveElCombustibleInicial() {
        assertEquals(heliCivil.getCombustible(), 100.0);
    }

    @Test
    void cuandoSeConsultaSuKmElHelicopteroEnviaElKilometraje() {
        assertEquals(heliCivil.getKilometraje(), 0.0);
    }

    @Test
    void cuandoSeConsultaSuCapacidadElHelicopteroDevuelveLaCpacidadMax() {
        assertEquals(heliCivil.getCapacidad(), 350.0);
    }

    /*Tests de consulta */
    @Test
    void alConsultarLaReservaElHeliDevuelveEl10DeSuCapacidad() {
        List<String> listaVacia = new ArrayList<>();
        assertEquals(heliCivil.getBitacora(), listaVacia);
    }

    @Test
    void enModoEficienteElHelicopteroDevuelveElNombreDelModo() {
        Modo eficiente = new ModoEficiente();
        heliCivil.cambiarModo(eficiente);
        assertEquals(heliCivil.getModoVuelo().getNombre(), "Eficiente");
    }

    @Test
    void enModoNormalElHelicopteroDevuelveElNombreDelModo1() {
        Modo normal = new ModoNormal();
        heliCivil.cambiarModo(normal);
        assertEquals(heliCivil.getModoVuelo().getNombre(), "Normal");
    }
    
    @Test
    void enModoAgresivoElHelicopteroDevuelveElNombreDelModo() {
        Modo agresivo = new ModoAgresivo();
        heliCivil.cambiarModo(agresivo);
        assertEquals(heliCivil.getModoVuelo().getNombre(), "Agresivo");
    }

    @Test
    void daTrueSIUnHeliPuedeVolarConLaCantSuperiorQueElCombustibleNecesario() {
        assertTrue(heliCivil.puedeVolar(100.0));
    }

    @Test
    void daTrueSiUnHeliPuedeEntrarEnReserva() {
        assertTrue(heliCivil.entraEnReserva(85.0));
    }

    
    @Test
    void siUnHelicopteroTieneUnBooleanoTrueYNoPuedeVolarCiertaDistanciaEntocesHaraUnVueloParcial() {
        Modo eficiente = new ModoEficiente();
        heliCivil.cambiarModo(eficiente);
        heliCivil.volar(1900.0, true);
        assertEquals(heliCivil.getKilometraje(), 1400.0);
        assertTrue(
            heliCivil.getBitacora().contains("Vuelo parcial "+ heliCivil.getKilometraje() + " km recorridos hasta agotar combustible.")
        );
    }

    /*Tests de excepciones */
    @Test
    void siElHelicopteroTieneReservaRestanteLanzaraLaExcepcionDeReserva() {
        assertThrows(
            UsoDeReservaException.class, 
            () -> heliCivil.volar(1350.0, true)
        );
    }

    @Test
    void siElHelicopteroTieneBooleanFalsoYNoPuedeVolarLanzaraUnaExcepcion() {
        assertThrows(
            EstadoInvalidoException.class, 
            () -> heliCivil.volar(1900.0, false)
        );
    }

    @Test
    void siElHelicopteroNoTieneCombustibleAlValidarSuDespegueLanzaraLaExcepcionDEstadoInvalido() {
        Helicoptero heli = new HelicopteroCivil(0.0, 150.0);
        assertThrows(
            EstadoInvalidoException.class, 
            () -> heli.volar(40.0, true)
        );
    }

    @Test
    void cuandoIntentaVolarDevolveraUnValorTrue(){
        assertTrue(heliCivil.intentarVolar(170.0, true));
        assertTrue(heliCivil.getBitacora().contains("Vuelo exitoso"));
    }

    @Test
    void cuandoIntentaVolarParciamenteTambienDaVueloCumplido(){
        assertTrue(heliCivil.intentarVolar(1900.0, true));
        assertTrue(heliCivil.getBitacora().contains("Vuelo exitoso"));
    }

    @Test
    void cuandoElHelicopteroTieneReservaDevolveraVerdero(){
        assertTrue(heliCivil.intentarVolar(1350.0, true));
        assertTrue(heliCivil.getBitacora().contains("Vuelo fue realizado utilizando reserva de combustible"));
    }

    @Test
    void siElHelicopteroIntentaVolaryFallaEntoncesDaFalse(){
        Helicoptero heli = new HelicopteroCivil(0.0, 150.0);
        assertFalse(heli.intentarVolar(40.0, false));
    }

    /*Tests de helicoptero civil */
    @Test
    void antesDeVolarElHeliCivilRegistraUnMensajeYDespuesFinalizaDandoUnMensaje() {
        Modo normal = new ModoNormal();
        heliCivil.cambiarModo(normal);
        heliCivil.volar(450.0, heliCivil.getCapacidad() > 100.0);
        assertTrue(heliCivil.getBitacora().contains("Pasajeros y equipaje verificados. Listo para despegue"));
        assertTrue(heliCivil.getBitacora().contains("Vuelo civil completado: " + 450.0 + " km. Pasajeros desembarcados."));
        assertEquals(heliCivil.calcularTiempoVuelo(450.0), 2.0454545454545454);
    }
    
    /*Tests de helicoptero militar*/
    @Test
    void siUnHeliMilitarTienePocoCombustibleYSuModoEsAgresivoLanzaraUnError() {
        Helicoptero heliMilitar2 = new HelicopteroMilitar(15.0, 350.0);
        Modo modoAgresivo = new ModoAgresivo();
        heliMilitar2.cambiarModo(modoAgresivo);
        assertThrows(
            MisionAbortadaException.class, 
            () -> heliMilitar2.volar(23.0, true)
        );
    }

    @Test 
    void AntesDeVolarElHeliMilitarRegistraUnMensajeYDespuesFinalizaDandoUnMensaje() {
        Modo modoAgresivo = new ModoAgresivo();
        heliMilitar.cambiarModo(modoAgresivo);
        heliMilitar.volar(357.7, heliMilitar.getCapacidad()> 100.0);
        assertTrue(heliMilitar.getBitacora().contains("Sistemas de armas y navegación activados"));
        assertTrue(heliMilitar.getBitacora().contains("Mision completa: " + 357.7 + " km. Regresando a base."));
        assertEquals(heliMilitar.calcularTiempoVuelo(357.7), 1.2774999999999999);
    }
}
