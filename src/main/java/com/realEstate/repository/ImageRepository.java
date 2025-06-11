package com.realEstate.repository;

import com.realEstate.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository to manage image entities
public interface ImageRepository extends JpaRepository<Image, Long> {
}