package com.vnl.db.jooq.generator;

import java.util.Arrays;
import org.jooq.codegen.GeneratorStrategy.Mode;
import org.jooq.tools.StringUtils;

/**
 * 自動生成Suffixクラス。必要に応じて命名規則を修正、追加していきます。
 */
enum GenerationSuffix {

    ENTITY(Mode.POJO, "Entity"),
    TABLE(Mode.DEFAULT, "Table"),
    OTHER(null, StringUtils.EMPTY);

    private final Mode mode;
    private final String suffix;

    String getSuffix() {
        return suffix;
    }

    GenerationSuffix(final Mode mode, final String suffix) {
        this.mode = mode;
        this.suffix = suffix;
    }

    /**
     * jOOQのGeneratorStrategyのModeからSuffixを生成します。
     *
     * @param mode GeneratorStrategyのMode
     * @return Suffix
     */
    static GenerationSuffix createByMode(final Mode mode) {
        return Arrays.stream(GenerationSuffix.values())
            .filter(v -> v.mode.equals(mode))
            .findFirst()
            .orElse(OTHER);
    }

}