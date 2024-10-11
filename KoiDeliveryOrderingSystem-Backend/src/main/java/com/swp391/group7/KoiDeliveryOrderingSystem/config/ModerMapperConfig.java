package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CertificateDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CustomerDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.PackageDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CheckKoiHealthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CheckingKoiHealth;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
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

        modelMapper.typeMap(CheckingKoiHealth.class, CheckKoiHealthResponse.class).addMappings(mapper ->
        {
            mapper.map(src -> src.getOrderDetail().getId(), CheckKoiHealthResponse::setOrderDetail);
            mapper.map(src -> src.getPackages().getId(),CheckKoiHealthResponse::setPackages);
        }
        );

        return modelMapper;
    }
}
