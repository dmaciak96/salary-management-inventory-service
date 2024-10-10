package com.salary.management.inventory_service.repository.listener;

import com.salary.management.inventory_service.model.entity.AbstractDocument;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class UuidGeneratorListener extends AbstractMongoEventListener<AbstractDocument> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<AbstractDocument> event) {
        super.onBeforeConvert(event);
        var abstractDocument = event.getSource();

        if (abstractDocument.getId() == null) {
            abstractDocument.setId(UUID.randomUUID());
        }

        /*We need to set this because when we manually generate UUID
        then audit mechanism don't recognize that entity not exists in database*/
        if (abstractDocument.getCreatedAt() == null) {
            abstractDocument.setCreatedAt(Instant.now());
        }
    }
}
