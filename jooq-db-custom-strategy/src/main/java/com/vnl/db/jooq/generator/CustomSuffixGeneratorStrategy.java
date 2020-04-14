package com.vnl.db.jooq.generator;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;
import org.jooq.tools.StringUtils;

/**
 * jOOQ自動生成クラスのカスタムSuffixストラテジクラス
 */
public class CustomSuffixGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(final Definition definition, final Mode mode) {
        final String suffix =
            switch (mode) {
                case POJO -> "Entity";
                case DEFAULT -> "Table";
                default -> StringUtils.EMPTY;
            };

        return super.getJavaClassName(definition, mode) + suffix;
    }
}
