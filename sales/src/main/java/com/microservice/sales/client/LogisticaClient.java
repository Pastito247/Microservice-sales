package com.microservice.sales.client;

import com.microservice.sales.dto.EnvioRequest;
import com.microservice.sales.dto.EnvioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LogisticaClient {

    private final RestTemplate restTemplate;

    @Value("${logistica.api.url}")
    private String logisticaUrl;

    public EnvioResponse crearEnvioParaVenta(EnvioRequest envioRequest) {
        return restTemplate.postForObject(
            logisticaUrl + "/api/envios",
            envioRequest,
            EnvioResponse.class
        );
    }
}
