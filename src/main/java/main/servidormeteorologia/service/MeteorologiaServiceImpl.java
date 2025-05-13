package main.servidormeteorologia.service;

import main.servidorMeteorologia.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.*;

@GrpcService
public class MeteorologiaServiceImpl extends MeteorologiaServiceGrpc.MeteorologiaServiceImplBase {

    private final Map<String, List<Double>> dadosClimaticos = new HashMap<>();

    @Override
    public void cadastrarCidade(CidadeRequest request, StreamObserver<CidadeResponse> responseObserver) {
        String cidade = request.getNome();
        if (!dadosClimaticos.containsKey(cidade)) {
            List<Double> temperaturas = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                temperaturas.add(15 + (35 - 15) * random.nextDouble());
            }
            dadosClimaticos.put(cidade, temperaturas);
        }
        CidadeResponse response = CidadeResponse.newBuilder().setMensagem("Cidade cadastrada: " + cidade).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listarCidades(Empty request, StreamObserver<CidadesResponse> responseObserver) {
        CidadesResponse response = CidadesResponse.newBuilder().addAllCidades(dadosClimaticos.keySet()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void obterTemperaturaAtual(CidadeRequest request, StreamObserver<TemperaturaResponse> responseObserver) {
        List<Double> temps = dadosClimaticos.get(request.getNome());
        TemperaturaResponse response = TemperaturaResponse.newBuilder().setTemperatura(temps.get(0)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void previsaoCincoDias(CidadeRequest request, StreamObserver<PrevisaoResponse> responseObserver) {
        PrevisaoResponse response = PrevisaoResponse.newBuilder().addAllTemperaturas(dadosClimaticos.get(request.getNome())).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void estatisticasClimaticas(CidadeRequest request, StreamObserver<EstatisticasResponse> responseObserver) {
        List<Double> temps = dadosClimaticos.get(request.getNome());
        double media = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double max = temps.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double min = temps.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        EstatisticasResponse response = EstatisticasResponse.newBuilder()
                .setMedia(media).setMaxima(max).setMinima(min).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
