package io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.custom.ItemInquiryRepositoryCustom;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.ItemInquiryEntity;

public interface ItemInquiryRepository extends JpaRepository<ItemInquiryEntity, Long>, ItemInquiryRepositoryCustom {
}
