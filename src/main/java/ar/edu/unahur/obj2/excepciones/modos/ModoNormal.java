package ar.edu.unahur.obj2.excepciones.modos;

public class ModoNormal implements Modo{
    @Override
    public Double getConsumoPorLitro(){
        return 9.0;
    }

    @Override
    public Integer getVelocidadMax(){
        return 220;
    }

    @Override
    public String getNombre(){
        return "Normal";
    }
}
