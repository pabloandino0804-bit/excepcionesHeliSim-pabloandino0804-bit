package ar.edu.unahur.obj2.excepciones.modos;

public class ModoEficiente implements Modo{
    @Override
    public Double getConsumoPorLitro(){
        return 14.0;
    }

    @Override
    public Integer getVelocidadMax(){
        return 180;
    }

    @Override 
    public String getNombre(){
        return "Eficiente";
    }
}
