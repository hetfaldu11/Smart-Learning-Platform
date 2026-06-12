package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseDetail;
import org.hibernate.boot.jaxb.mapping.spi.JaxbPersistentAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {

}
