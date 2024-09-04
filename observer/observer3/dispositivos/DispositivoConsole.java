package observer3.dispositivos;

import observer3.estacao.Observer;

public class DispositivoConsole implements Observer {

    @Override
    public void atualizado(double temp, double umid) {
        System.out.println("Temperatura: " + temp + " umidade: " + umid + " alterada com sucesso");
    }
}
