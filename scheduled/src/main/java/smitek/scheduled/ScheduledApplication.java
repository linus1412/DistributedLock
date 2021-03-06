package smitek.scheduled;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SchedulerLock;
import net.javacrumbs.shedlock.provider.hazelcast.HazelcastLockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.ScheduledLockConfigurationBuilder;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

import static java.util.Collections.singletonList;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class ScheduledApplication {

	public static void main(String[] args) {
	  SpringApplication.run(ScheduledApplication.class, args);
  }

  @Component
  public static class ScheduledTasks {

    @Value("${spring.profiles.active}")
    private String appName;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "snapshotter")
    public void reportCurrentTime() {
      log.info("App: {} - The time is now {}", appName, dateFormat.format(new Date()));
    }
  }

  @Bean
  public ScheduledLockConfiguration taskScheduler(LockProvider lockProvider) {
    return ScheduledLockConfigurationBuilder
      .withLockProvider(lockProvider)
      .withPoolSize(10)
      .withDefaultLockAtMostFor(Duration.ofMinutes(10))
      .build();
  }



}
