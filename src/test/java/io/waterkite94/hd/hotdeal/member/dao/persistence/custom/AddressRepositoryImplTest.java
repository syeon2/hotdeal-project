package io.waterkite94.hd.hotdeal.member.dao.persistence.custom;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressMapper;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.AddressEntity;
import io.waterkite94.hd.hotdeal.member.domain.Address;

class AddressRepositoryImplTest extends IntegrationTestSupport {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressMapper addressMapper;

	@BeforeEach
	void before() {
		addressRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName(value = "주소 정보를 수정합니다.")
	void updateAddress() {
		// given
		String memberId = "memberId";

		AddressEntity addressDto = createAddressEntity("city", "state", "address", "12345", memberId);
		addressRepository.save(addressDto);

		// when
		Address updateDto = createAddressDto("newCity", "newState", "newAddress", "12345", memberId);
		addressRepository.updateAddress(memberId, updateDto);

		// then
		Optional<AddressEntity> findAddressOptional = addressRepository.findByMemberId(memberId);
		assertThat(findAddressOptional).isPresent()
			.get()
			.extracting("city", "state", "address", "zipcode")
			.containsExactlyInAnyOrder(updateDto.getCity(), updateDto.getState(), updateDto.getAddress(),
				updateDto.getZipcode());

	}

	private AddressEntity createAddressEntity(String city, String state, String address, String number,
		String memberId) {
		return AddressEntity.builder()
			.city(city)
			.state(state)
			.address(address)
			.zipcode(number)
			.memberId(memberId)
			.build();
	}

	private Address createAddressDto(String city, String state, String address, String number,
		String memberId) {
		return Address.builder()
			.city(city)
			.state(state)
			.address(address)
			.zipcode(number)
			.memberId(memberId)
			.build();
	}
}
