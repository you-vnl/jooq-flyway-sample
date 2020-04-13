package com.vnl.db.jooq.generator;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

/**
 * jOOQ自動生成クラスのカスタムSuffixストラテジクラス
 */
public class CustomSuffixGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(final Definition definition, final Mode mode) {
        return super.getJavaClassName(definition, mode) + GenerationSuffix.createByMode(mode).getSuffix();
    }
}
