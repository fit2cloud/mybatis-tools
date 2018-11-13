package com.fit2cloud.tools.mybatis.swagger;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * Author: chunxing
 * Date: 2018/7/20  下午5:31
 * Description:
 */
public class ApiModelPropertyAnnotationGenerator extends DefaultCommentGenerator {

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!introspectedColumn.isNullable() && !("id".equalsIgnoreCase(introspectedColumn.getActualColumnName())
                || "create_time".equalsIgnoreCase(introspectedColumn.getActualColumnName()) || "update_time".equalsIgnoreCase(introspectedColumn.getActualColumnName()))) {
            field.addJavaDocLine("@ApiModelProperty(value = \"" + introspectedColumn.getRemarks() + "\", required = true)");
        } else {
            field.addJavaDocLine("@ApiModelProperty(\"" + introspectedColumn.getRemarks() + "\")");
        }
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
    }
}
