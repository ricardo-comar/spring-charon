package com.github.ricardocomar.springcharon.etlconsumer.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.avro.Schema;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;

/**
 * CustomSchemaRegistryClient
 */
public class CustomSchemaRegistryClient extends MockSchemaRegistryClient {

    private static final String SCHEMA_FIELD = "SCHEMA$";
    private static final String CONFIG_NAMESPACES = "schema.registry.namespaces";

    public CustomSchemaRegistryClient(final Map<String, ?> configs) {
        for (final Map.Entry<String, Schema> clazz : this.findNamespaceSchemas(configs).entrySet()) {
            try {
                this.register(clazz.getKey(), clazz.getValue());
            } catch (IOException | RestClientException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, Schema> findNamespaceSchemas(final Map<String, ?> configs) {

        final Map<String, Schema> namespacesMap = new HashMap<>();
        if (configs.containsKey(CONFIG_NAMESPACES)) {
            final String namespaces = (String) configs.get(CONFIG_NAMESPACES);

            final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                    false);
            provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

            for(final String namespace : namespaces.split(",")) {
                for(final BeanDefinition bean : provider.findCandidateComponents(namespace)) {
                    try {
                        final Class<?> clazz = Class.forName(bean.getBeanClassName());
                        if (clazz.getDeclaredField(SCHEMA_FIELD) != null) {
                            System.err.println("Registering class " + clazz.getCanonicalName());

                            namespacesMap.put(clazz.getCanonicalName(), 
                                    (Schema) clazz.getDeclaredField(SCHEMA_FIELD).get(clazz));
                        }
                    } catch (final Exception e) {
                    }
                }
            }
        }

        return namespacesMap;

    }

}
