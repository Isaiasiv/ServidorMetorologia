package main.servidormeteorologia;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import main.servidorMeteorologia.grpc.*;

public class ExemploCliente {

        public static void main(String[] args) {
            ManagedChannel canal = ManagedChannelBuilder.forAddress("localhost", 9090)
                    .usePlaintext()
                    .build();

            MeteorologiaServiceGrpc.MeteorologiaServiceBlockingStub stub =
                    MeteorologiaServiceGrpc.newBlockingStub(canal);

            // Testar cadastrarCidade
            CidadeRequest cidade = CidadeRequest.newBuilder().setNome("Urutaí").build();
            CidadeResponse response = stub.cadastrarCidade(cidade);
            System.out.println(response.getMensagem());

            // Testar listarCidades
            CidadesResponse cidades = stub.listarCidades(Empty.newBuilder().build());
            System.out.println("Cidades cadastradas: " + cidades.getCidadesList());

            // Testar obterTemperaturaAtual
            TemperaturaResponse temp = stub.obterTemperaturaAtual(cidade);
            System.out.println("Temperatura atual: " + temp.getTemperatura());

            canal.shutdown();
        }
    }

