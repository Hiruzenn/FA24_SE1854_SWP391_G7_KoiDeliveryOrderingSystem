package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.CertificateDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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
        return modelMapper;
    }
}
