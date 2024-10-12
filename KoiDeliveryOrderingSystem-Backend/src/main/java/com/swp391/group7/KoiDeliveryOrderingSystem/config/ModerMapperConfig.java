package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CertificateDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CheckingKoiHealthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CheckingKoiHealth;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModerMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(Certificate.class, CertificateDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getOrders().getId(),CertificateDTO::setOrderId);
        });

        modelMapper.typeMap(CheckingKoiHealth.class, CheckingKoiHealthResponse.class).addMappings(mapper ->
        {
            mapper.map(src -> src.getOrderDetail().getId(), CheckingKoiHealthResponse::setOrderDetail);
            mapper.map(src -> src.getPackages().getId(), CheckingKoiHealthResponse::setPackages);
        }
        );

        return modelMapper;
    }
}
