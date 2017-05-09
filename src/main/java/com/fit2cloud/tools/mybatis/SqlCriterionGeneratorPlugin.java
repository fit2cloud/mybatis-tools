package com.fit2cloud.tools.mybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

public class SqlCriterionGeneratorPlugin extends PluginAdapter {


    public SqlCriterionGeneratorPlugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {

        InnerClass criteria = null;
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) {
                criteria = innerClass;
                break;
            }
        }

        if (criteria == null) {
            return true;
        }


        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String"), "value"));

        StringBuilder sb = new StringBuilder();
        sb.append("andSqlCriterion");
        method.setName(sb.toString());
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());

        sb.setLength(0);
        sb.append("addCriterion(\"(\" + value + \")\")");
        sb.append(";");
        method.addBodyLine(sb.toString());
        method.addBodyLine("return (Criteria) this;");

        criteria.addMethod(method);

        return true;
    }

}
