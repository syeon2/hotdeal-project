package io.waterkite94.hd.hotdeal.member.dao.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodeRedisAdapter {

	private final RedisTemplate<String, String> redisTemplate;

	@Value("${spring.data.redis.authentication-code.expire}")
	private String expiretime;

	private static final String prefix = "email:code";

	public void saveAuthenticationCode(String email, String code) {
		int expiredTime = Integer.parseInt(expiretime);

		redisTemplate.opsForValue().set(generatedKey(email), code, Duration.ofMinutes(expiredTime));
	}

	public String getAuthenticationCode(String email) {
		return redisTemplate.opsForValue().get(generatedKey(email));
	}

	private String generatedKey(String email) {
		return prefix + email;
	}

}
