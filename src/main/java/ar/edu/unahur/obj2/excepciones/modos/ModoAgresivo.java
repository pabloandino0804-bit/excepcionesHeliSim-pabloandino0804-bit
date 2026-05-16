package ar.edu.unahur.obj2.excepciones.modos;

public class ModoAgresivo implements Modo{
    @Override
    public Double getConsumoPorLitro(){
        return 6.0;
    }

    @Override
    public Integer getVelocidadMax(){
        return 280;
    }

    @Override
    public String getNombre(){
        return "Agresivo";
    }
}
