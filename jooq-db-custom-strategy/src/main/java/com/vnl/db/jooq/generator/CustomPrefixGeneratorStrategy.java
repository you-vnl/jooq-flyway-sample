package com.vnl.db.jooq.generator;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

public class CustomPrefixGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(final Definition definition, final Mode mode) {

        final String name = super.getJavaClassName(definition, mode);

        switch (mode) {
            case POJO:
                return name + "Entity";
            case DEFAULT:
                return name + "Table";
        }

        return name;
    }
}
