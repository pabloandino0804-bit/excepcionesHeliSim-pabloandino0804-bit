package ar.edu.unahur.obj2.excepciones.helicopteros;

public class HelicopteroCivil extends Helicoptero{

    public HelicopteroCivil(double combustibleInicial, double capacidad) {
        super(combustibleInicial, capacidad);
    }

    @Override
    protected String mensaje() {
        return "Pasajeros y equipaje verificados. Listo para despegue"; 
    }

    @Override
    protected void finalizarVuelo() {
        agregarMensaje("Vuelo civil completado: " + this.getKilometraje() + " km. Pasajeros desembarcados.");
    }
}
