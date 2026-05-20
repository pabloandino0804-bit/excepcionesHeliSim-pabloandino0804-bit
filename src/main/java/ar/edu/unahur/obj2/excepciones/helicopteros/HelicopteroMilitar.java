package ar.edu.unahur.obj2.excepciones.helicopteros;

import ar.edu.unahur.obj2.excepciones.misExepciones.MisionAbortadaException;
import ar.edu.unahur.obj2.excepciones.modos.ModoAgresivo;

public class HelicopteroMilitar extends Helicoptero{
    private Double minimoAgresivo = 20.0;

    public HelicopteroMilitar(double combustibleInicial, double capacidad) {
        super(combustibleInicial, capacidad);
    }

    @Override
    protected void validarEstadoDeDespegue() {
        super.validarEstadoDeDespegue();
        if(esAgresivo() && tieneCombustiblebajo()){
            throw new MisionAbortadaException(
                "Modo agresivo activo con solo "
                + getCombustible() +
                " litros. Se necesita al menos "
                + minimoAgresivo +
                " litros para operar este modo."
            );
        }
    }

    private Boolean esAgresivo() {return this.getModoVuelo() instanceof ModoAgresivo;}


    private Boolean tieneCombustiblebajo() {return this.getCombustible() < minimoAgresivo;}

    @Override
    protected String mensaje() {
        return "Sistemas de armas y navegación activados";
    }

    @Override
    protected void finalizarVuelo() {
        agregarMensaje("Mision completa: " + this.getKilometraje() + " km. Regresando a base.");
    }

}
