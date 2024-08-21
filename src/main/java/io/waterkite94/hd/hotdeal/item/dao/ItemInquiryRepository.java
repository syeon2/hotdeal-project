package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.item.dao.custom.ItemInquiryRepositoryCustom;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemInquiryEntity;

public interface ItemInquiryRepository extends JpaRepository<ItemInquiryEntity, Long>, ItemInquiryRepositoryCustom {
}
