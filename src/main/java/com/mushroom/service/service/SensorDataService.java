package com.mushroom.service.service;

import com.mushroom.service.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mushroom.service.model.SensorInfo;

@Service
public class SensorDataService {


    @Autowired
    private SensorRepository sensorRepository;


    public SensorInfo addSensorData(SensorInfo user) {
        return sensorRepository.add(user);
    }
}
