package com.swd392.funfundbe.config;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ToString
public class MomoConfig {
    public final String configName = "Momo";
    @Value("${momo.access-key}")
    public String accessKey;
    @Value("${momo.secret-key}")
    public String secretKey;
    @Value("${momo.payment-url}")
    public String paymentUrl;
    @Value("${momo.partner-code}")
    public String partnerCode;
    @Value("${momo.order-info}")
    public String orderInfo;
    @Value("${momo.request-type}")
    public String requestType;
    @Value("${momo.extra-data}")
    public String extraData;
    @Value("${momo.lang}")
    public String lang;
    @Value("${momo.web-redirect-url}")
    public String webRedirectUrl;
    @Value("${momo.mobile-redirect-url}")
    public String mobileRedirectUrl;
    @Value("${momo.ipn-url}")
    public String ipnUrl;
}
