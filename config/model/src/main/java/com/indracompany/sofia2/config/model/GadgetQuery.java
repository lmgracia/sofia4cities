/**
 * Copyright Indra Sistemas, S.A.
 * 2013-2018 SPAIN
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*******************************************************************************
 * © Indra Sistemas, S.A.
 * 2013 - 2018  SPAIN
 *
 * All rights reserved
 ******************************************************************************/
package com.indracompany.sofia2.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Configurable;

import com.indracompany.sofia2.config.model.base.AuditableEntityWithUUID;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GADGET_QUERY")
@Configurable
public class GadgetQuery extends AuditableEntityWithUUID {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "GADGET_ID", referencedColumnName = "ID", nullable = false)
	@Getter
	@Setter
	private Gadget gadget;
	/*
	 * @ManyToOne
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE)
	 * 
	 * @ForeignKey(name = "FK_GADGET_QUERY_ONTOLOGIA")
	 * 
	 * @JoinColumn(name = "ONTOLOGIA_ID", referencedColumnName = "ID", nullable =
	 * false) private Ontologia ontologiaId;
	 * 
	 * 
	 * public Ontologia getOntologiaId() { return ontologiaId; }
	 * 
	 * public void setOntologiaId(Ontologia ontologiaId) { this.ontologiaId =
	 * ontologiaId; }
	 */

	@Column(name = "QUERY", nullable = false)
	@NotNull
	@Lob
	@Getter
	@Setter
	private String query;

	@Column(name = "POSITION_ID")
	@Getter
	@Setter
	private Integer positionId;

	@Column(name = "IDENTIFICATION", length = 50)
	@Getter
	@Setter
	private String identification;

}