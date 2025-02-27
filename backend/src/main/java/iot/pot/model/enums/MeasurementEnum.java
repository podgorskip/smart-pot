package iot.pot.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeasurementEnum {
    AIR_HUMIDITY(new MeasurementDetails(
            "air_humidity",
            "Oops! Air humidity has dropped below threshold [%s allowed, %s current]. Time to grab a humidifier!",
            "Whoa! Air humidity has raised above threshold [%s allowed, %s current]. Feels like a rainforest in here!"
    )),
    SOIL_HUMIDITY(new MeasurementDetails(
            "soil_humidity",
            "Alert! Soil humidity has dropped below threshold [%s allowed, %s current]. Your plants might be thirsty!",
            "Heads up! Soil humidity has raised above threshold [%s allowed, %s current]. Hope your plants don't drown!"
    )),
    TEMPERATURE(new MeasurementDetails(
            "temperature",
            "Brr! Temperature has dropped below threshold [%s allowed, %s current]. Maybe turn up the heat?",
            "Yikes! Temperature has raised above threshold [%s allowed, %s current]. Someone turn on the AC!"
    )),
    INSOLATION(new MeasurementDetails(
            "insolation",
            "Uh-oh! Insolation has dropped below threshold [%s allowed, %s current]. Is it cloudy out there?",
            "Wow! Insolation has raised above threshold [%s allowed, %s current]. Don't forget your sunscreen!"
    )),
    INSOLATION_DIGITAL(new MeasurementDetails(
            "insolation_digital",
            "Oops! The digital insolation is acting up. Did you forget to charge the sun? [%s allowed, %s current].",
            "Whoa! Insolation levels are through the roof! Is the sun showing off again? [%s allowed, %s current]."
    )),
    WATER_LEVEL(new MeasurementDetails(
            "water_alert",
            "Ups! Cannot water your plant. Fill up!",
            "Water level is insane"
    ));

    private final MeasurementDetails measurementDetails;

    @Getter
    @AllArgsConstructor
    public static class MeasurementDetails {
        private String topic;
        private String lowerThresholdMessageTemplate;
        private String upperThresholdMessageTemplate;
    }
}
