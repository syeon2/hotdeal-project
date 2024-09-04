package io.waterkite94.hd.hotdeal.item.dao.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.item.dao.persistence.custom.ItemInquiryRepositoryCustom;
import io.waterkite94.hd.hotdeal.item.dao.persistence.entity.ItemInquiryEntity;

public interface ItemInquiryRepository extends JpaRepository<ItemInquiryEntity, Long>, ItemInquiryRepositoryCustom {
}
