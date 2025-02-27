package iot.pot.validation;

import iot.pot.database.model.Device;
import iot.pot.messaging.MeasurementNotificationHandler;
import iot.pot.model.enums.MeasurementEnum;
import iot.pot.services.NotificationService;
import iot.pot.utils.ExecutorsPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ThresholdVerifier {
  private final MeasurementNotificationHandler notificationHandler;
  private final NotificationService notificationService;

  public void verifyThreshold(MeasurementEnum measurement, Double lowerThreshold, Double upperThreshold, Double value, Device device) {
      System.out.println("Verifying thresholds...");

      if (Objects.nonNull(lowerThreshold) && (lowerThreshold > value)) {
          CompletableFuture.runAsync(() -> {
              notificationService.createNotification(device, measurement, true);
          }).thenRunAsync(() -> {
              ExecutorsPool.executorService.submit(() -> {
                  try {
                      notificationHandler.sendMessage(
                              device.getUser(),
                              String.format(
                                      measurement.getMeasurementDetails().getLowerThresholdMessageTemplate(),
                                      lowerThreshold,
                                      value
                              )
                      );
                  } catch (Exception e) {
                      System.err.println("Error sending message: " + e.getMessage());
                  }
              });
          });
      }

      if (Objects.nonNull(upperThreshold) && (upperThreshold < value)) {
          CompletableFuture.runAsync(() -> {
              notificationService.createNotification(device, measurement, false);
          }).thenRunAsync(() -> {
              ExecutorsPool.executorService.submit(() -> {
                  try {
                      notificationHandler.sendMessage(
                              device.getUser(),
                              String.format(
                                      measurement.getMeasurementDetails().getUpperThresholdMessageTemplate(),
                                      upperThreshold,
                                      value
                              )
                      );
                  } catch (Exception e) {
                      System.err.println("Error sending message: " + e.getMessage());
                  }
              });
          });
      }
  }

  public void sendWaterAlert(Device device) {
      CompletableFuture.runAsync(() -> {
          notificationService.createNotification(device, MeasurementEnum.WATER_LEVEL, true);
      }).thenRunAsync(() -> {
          ExecutorsPool.executorService.submit(() -> {
              try {
                  notificationHandler.sendMessage(
                          device.getUser(),
                          MeasurementEnum.WATER_LEVEL.getMeasurementDetails().getLowerThresholdMessageTemplate()
                  );

              } catch (Exception e) {
                  System.err.println("Error sending message: " + e.getMessage());
              }
          });
      });
  }
}
