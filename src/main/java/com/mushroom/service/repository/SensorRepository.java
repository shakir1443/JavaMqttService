package com.mushroom.service.repository;
import com.mushroom.service.model.SensorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class SensorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public SensorInfo add(SensorInfo sensorinfo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("sensorinfo")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("user", sensorinfo.getUser());
        parameters.put("device", sensorinfo.getDevice());
        parameters.put("date", sensorinfo.getDate());
        parameters.put("time", sensorinfo.getTime());
        parameters.put("hum", sensorinfo.getHumidity());
        parameters.put("temp", sensorinfo.getTemperature());
        parameters.put("co2", sensorinfo.getCo2());
        parameters.put("light", sensorinfo.getLight());


        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        if (id == null) {
            log.error("Failed to insert {}", sensorinfo);
            System.out.println("Failed to insert");

            return null;
        }
        sensorinfo.setId(id.intValue());
        return sensorinfo;
    }
}
