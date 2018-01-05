/*******************************************************************************
 * Â© Indra Sistemas, S.A.
 * 2013 - 2018  SPAIN
 * 
 * All rights reserved
 ******************************************************************************/
package com.indracompany.sofia2.persistence.mongodb.gridfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indracompany.sofia2.persistence.mongodb.template.MongoDbTemplate;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

@Component
@Lazy
public class GridFSConnectorImpl implements GridFSConnector {

	private static final Logger logger = LoggerFactory.getLogger(GridFSConnectorImpl.class);

	@Autowired
	private MongoDbTemplate mongoDbConnector;

	private void uploadGridFsFile(String database, ObjectId fileId, InputStream stream, String metadata)
			throws PersistenceException {
		try {
			logger.info("Uploading file to GridFS. Database = {}, metadata = {}.", database, metadata);
			GridFSBucket bucket = mongoDbConnector.configureGridFSBucket(database);
			GridFSUploadOptions uploadOptions = new GridFSUploadOptions().metadata(parseMetadata(metadata));
			bucket.uploadFromStream(new BsonObjectId(fileId), fileId.toHexString(), stream, uploadOptions);
		} catch (Throwable e) {
			logger.error(
					"Unable to upload file to GridFS. Database = {}, metadata = {}, cause = {}, errorMessage = {}.",
					database, metadata, e.getCause(), e.getMessage());
			throw new PersistenceException("Unable to upload file to GridFS", e);
		}
	}

	@Override
	public ObjectId uploadGridFsFile(String database, InputStream stream, String metadata) throws PersistenceException {
		ObjectId fileId = new ObjectId();
		uploadGridFsFile(database, fileId, stream, metadata);
		return fileId;
	}

	@Override
	public void updateGridFsFile(String database, ObjectId fileId, InputStream stream, String metadata)
			throws PersistenceException {
		removeGridFsFile(database, fileId);
		uploadGridFsFile(database, fileId, stream, metadata);
	}

	@Override
	public void removeGridFsFile(String database, ObjectId fileId) throws PersistenceException {
		try {
			logger.info("Deleting file in GridFS. Database = {}, fileId = {}.", database, fileId);
			GridFSBucket bucket = mongoDbConnector.configureGridFSBucket(database);
			bucket.delete(fileId);
		} catch (Throwable e) {
			logger.error("Unable to delete file in GridFS. Database = {}, fileId = {}, cause = {}, errorMessage = {}.",
					database, fileId, e.getCause(), e.getMessage());
			throw new PersistenceException("Unable to upload file to GridFS", e);
		}
	}

	@Override
	public GridFSDownloadStream readGridFsFile(String database, ObjectId fileId) throws PersistenceException {
		try {
			logger.info("Reading GridFS file. Database = {}, fileId = {}.", database, fileId);
			GridFSBucket bucket = mongoDbConnector.configureGridFSBucket(database);
			return bucket.openDownloadStream(fileId);
		} catch (Throwable e) {
			logger.error("Unable to read GridFS file. Database = {}, fileId = {}, cause = {}, errorMessage = {}.",
					database, fileId, e.getCause(), e.getMessage());
			throw new PersistenceException("Unable to read GridFS file", e);
		}
	}

	private Document parseMetadata(String metadata) {
		if (metadata == null || metadata.isEmpty())
			return new Document();
		try {
			Map<String, Object> parsedMetadata = new ObjectMapper().readValue(metadata,
					new TypeReference<Map<String, Object>>() {
					});
			return new Document(parsedMetadata);
		} catch (IOException e) {
			e.printStackTrace();
			return new Document();
		}
	}
}
