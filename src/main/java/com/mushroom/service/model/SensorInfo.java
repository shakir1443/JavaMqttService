package com.mushroom.service.model;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sbsatter on 9/18/18.
 */
@NoArgsConstructor
@Data

public class SensorInfo {
    public int id;
    public int user;
    public int device;
    public String date;
    public String time;
    public int humidity;
    public int temperature;
    public int co2;
    public int light;
}
