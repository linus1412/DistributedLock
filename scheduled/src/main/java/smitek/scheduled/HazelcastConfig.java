package smitek.scheduled;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.HazelcastInstance;
import net.javacrumbs.shedlock.provider.hazelcast.HazelcastLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static java.util.Collections.singletonList;

@Configuration
@Profile("hazelcast")
public class HazelcastConfig {

  @Bean
  public Config config() {
    Config config = new Config();

    JoinConfig joinConfig = config.getNetworkConfig().getJoin();

    joinConfig.getMulticastConfig().setEnabled(false);
    joinConfig.getTcpIpConfig().setEnabled(true).setMembers(singletonList("127.0.0.1"));

    return config;
  }

  @Bean
  public HazelcastLockProvider lockProvider(HazelcastInstance hazelcastInstance) {
    return new HazelcastLockProvider(hazelcastInstance);
  }

}
