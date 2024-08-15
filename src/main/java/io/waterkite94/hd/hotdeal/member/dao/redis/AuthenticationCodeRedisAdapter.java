package io.waterkite94.hd.hotdeal.member.dao.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodeRedisAdapter {

	private final RedisTemplate<String, String> redisTemplate;

	private static final String PREFIX = "email:code:";
	private static final Integer EXPIRE_TIME = 5;

	public void saveAuthenticationCode(String email, String code) {
		redisTemplate.opsForValue().set(generatedKey(email), code, Duration.ofMinutes(EXPIRE_TIME));
	}

	public String getAuthenticationCode(String email) {
		return redisTemplate.opsForValue().get(generatedKey(email));
	}

	private String generatedKey(String email) {
		return PREFIX + email;
	}

}
