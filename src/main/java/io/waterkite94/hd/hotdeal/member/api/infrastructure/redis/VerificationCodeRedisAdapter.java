package io.waterkite94.hd.hotdeal.member.api.infrastructure.redis;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VerificationCodeRedisAdapter {

	private final RedisTemplate<String, String> redisTemplate;

	private static final String PREFIX = "email:code:";
	private static final Integer EXPIRE_TIME = 5;

	public void saveVerificationCode(String email, String verificationCode) {
		redisTemplate.opsForValue().set(
			generatedKey(email),
			verificationCode,
			Duration.ofMinutes(EXPIRE_TIME));
	}

	public Optional<String> getVerificationCode(String email) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(generatedKey(email)));
	}

	private String generatedKey(String email) {
		return PREFIX.concat(email);
	}

}
