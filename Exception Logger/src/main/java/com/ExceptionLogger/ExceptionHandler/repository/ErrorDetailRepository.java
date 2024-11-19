package com.ExceptionLogger.ExceptionHandler.repository;

import com.ExceptionLogger.ExceptionHandler.model.ErrorDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorDetailRepository extends MongoRepository<ErrorDetail, String> {
}