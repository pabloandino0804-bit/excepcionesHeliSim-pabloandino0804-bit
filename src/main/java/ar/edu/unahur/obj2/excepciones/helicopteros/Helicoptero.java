package ar.edu.unahur.obj2.excepciones.helicopteros;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.excepciones.misExepciones.EstadoInvalidoException;
import ar.edu.unahur.obj2.excepciones.misExepciones.UsoDeReservaException;
import ar.edu.unahur.obj2.excepciones.modos.Modo;
import ar.edu.unahur.obj2.excepciones.modos.ModoEficiente;

public abstract class Helicoptero {
    
    protected double combustible;
    protected double kilometraje = 0.0;
    protected double capacidad;
    protected List<String> bitácora = new ArrayList<>();
    protected Modo modoVuelo = new ModoEficiente();

    public Helicoptero(double combustibleInicial, double capacidad) {
        if (combustibleInicial < 0){
            throw new EstadoInvalidoException(
                "No se puede inicializar un helicoptero con combustible negativo. "
                + "valor recibido: " + combustibleInicial
            );
        }
        this.combustible = combustibleInicial;
        this.capacidad = capacidad;
    }

    // Metodos que modifican el objeto
    // -------------------------------
    public void volar(Double distanciaKm, Boolean volarHastaDondePueda) {
        validarEstadoDeDespegue();

        prepararVuelo();

        ejecutarVuelo(distanciaKm, volarHastaDondePueda);

        finalizarVuelo();
    }
    
    private void ejecutarVuelo(Double distanciaKm, Boolean volarHastaQuePueda) {
        Double combustibleNecesario = this.calcularCombustibleNecesario(distanciaKm);

        Boolean usaReserva = this.consumeReserva(combustibleNecesario);

        if(!this.puedeVolar(distanciaKm)) {

            if (volarHastaQuePueda) {
                volarParcialmente();
                return;
            }

            throw new EstadoInvalidoException("No alcanza el combustible suficiente para vuelo completo");
        };

        this.combustible -= combustibleNecesario;
        this.kilometraje += distanciaKm;
        
        if (usaReserva) {
            throw new UsoDeReservaException(getReserva());
        }
        
    }

    private void volarParcialmente() {
        Double distanciaRecorrida = calcularDistanciaDisponible();

        this.kilometraje += distanciaRecorrida;

        this.combustible = 0.0;

        agregarMensaje(
            "Vuelo parcial "
            + distanciaRecorrida
            + " km recorridos hasta agotar combustible."
        );
    }

    protected void agregarMensaje(String mensaje) {
        bitácora.add(mensaje);
    }

    public void cambiarModo(Modo modoVuelo) {
        this.modoVuelo = modoVuelo;
    }

    //Metodos protegidos y Metodos privados
    protected void validarEstadoDeDespegue() {
        if (combustible <= 0.0) {
            throw new EstadoInvalidoException("Combustible insufuciente");
        }
    }

    protected void prepararVuelo() {
        this.antesDeVolar();
    }

    protected void antesDeVolar() {
        this.agregarMensaje(this.mensaje());
    }

    protected abstract String mensaje();

    protected abstract void finalizarVuelo();

    private Boolean consumeReserva(Double combustibleNecesario) {
        return (combustible - combustibleNecesario) < getReserva();
    }

    private double getReserva() {
        return capacidad * 0.1;
    }

    // Metodos de consulta
    // -------------------
    public Double calcularTiempoVuelo(Double distanciaKm) {
        return distanciaKm / modoVuelo.getVelocidadMax();
    }

    public Double calcularDistanciaDisponible() {
        return combustible * modoVuelo.getConsumoPorLitro();
    }

    public Boolean puedeVolar(Double distanciaKm) {
        return combustible >= this.calcularCombustibleNecesario(distanciaKm);
    }

    public Double calcularCombustibleNecesario(Double distanciaKm) {
        return distanciaKm / modoVuelo.getConsumoPorLitro();
    }

    public Boolean entraEnReserva(Double distanciaKm) {
        return consumeReserva(distanciaKm);
    }

    // Getters
    // -------
    public double getCombustible() {
        return combustible;
    }
    
    public double getKilometraje() {
        return kilometraje;
    }

    public double getCapacidad() {
        return capacidad;
    }
    
    //
    public Modo getModoVuelo() {
        return modoVuelo;
    }

    public List<String> getBitacora() {
        return bitácora;
    }
    

    //
    public  Boolean intentarVolar(Double distancia, Boolean puedeVolarHastaAqui){
        try {
            volar(distancia, puedeVolarHastaAqui);
            this.agregarMensaje("Vuelo exitoso");
            return true;
        }catch(UsoDeReservaException e){
            this.agregarMensaje("Vuelo fue realizado utilizando reserva de combustible");
            return true;
        }catch (EstadoInvalidoException e){
            this.agregarMensaje("Vuelo cancelado " + e.getMessage());
            return false;
        }

    }
}