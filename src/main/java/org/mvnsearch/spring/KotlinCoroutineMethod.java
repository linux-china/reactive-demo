package org.mvnsearch.spring;

import kotlinx.coroutines.flow.Flow;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class KotlinCoroutineMethod {
    private String name;
    private int paramCount = 0;
    private Class<?> returnType;
    private Class<?> inferredReturnType;
    private boolean flow;
    private boolean suspend;

    public KotlinCoroutineMethod(Method method) {
        this.name = method.getName();
        this.returnType = method.getReturnType();
        if (this.returnType.equals(Flow.class)) {
            this.flow = true;
        }
        Type[] parameterTypes = method.getGenericParameterTypes();
        this.paramCount = parameterTypes.length;
        if (paramCount > 0) {
            Type lastParamType = parameterTypes[paramCount - 1];
            if (lastParamType.getTypeName().startsWith("kotlin.coroutines.Continuation<")) {
                this.suspend = true;
                if (lastParamType.getTypeName().contains("kotlin.Unit>")) {

                } else {
                    this.inferredReturnType = parseInferredClass(lastParamType);
                }
                //kotlin.coroutines.Continuation<? super kotlin.Unit>
                //"(Lkotlin/coroutines/Continuation<-Lkotlin/Unit;>;)Ljava/lang/Object;"
            }
        }
    }

    public static Class<?> parseInferredClass(Type genericType) {
        Class<?> inferredClass = null;
        if (genericType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericType;
            Type[] typeArguments = type.getActualTypeArguments();
            if (typeArguments.length > 0) {
                final Type typeArgument = typeArguments[0];
                if (typeArgument instanceof ParameterizedType) {
                    inferredClass = (Class<?>) ((ParameterizedType) typeArgument).getActualTypeArguments()[0];
                } else if (typeArgument instanceof Class<?>) {
                    inferredClass = (Class<?>) typeArgument;
                } else {
                    String typeName = typeArgument.getTypeName();
                    if (typeName.contains(" ")) {
                        typeName = typeName.substring(typeName.lastIndexOf(" ") + 1);
                    }
                    if (typeName.contains("<")) {
                        typeName = typeName.substring(0, typeName.indexOf("<"));
                    }
                    try {
                        inferredClass = Class.forName(typeName);
                    } catch (Exception e) {

                    }
                }
            }
        }
        if (inferredClass == null && genericType instanceof Class) {
            inferredClass = (Class<?>) genericType;
        }
        return inferredClass;
    }
}
