package com.redis.mock.core;

import com.redis.mock.EnableRedisMockTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.context.annotation.DeterminableImports;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.util.*;

@Slf4j
public class RedisMockConvertersConfigurationImportSelector extends AutoConfigurationImportSelector implements DeterminableImports {

    private static final Set<String> ANNOTATION_NAMES;

    static {
        Set<String> names = new LinkedHashSet<>();
        names.add(EnableRedisMockTemplate.class.getName());
        ANNOTATION_NAMES = Collections.unmodifiableSet(names);
    }


    protected final Map<Class<?>, List<Annotation>> getAnnotations(
            AnnotationMetadata metadata) {
        MultiValueMap<Class<?>, Annotation> annotations = new LinkedMultiValueMap<>();
        Class<?> source = ClassUtils.resolveClassName(metadata.getClassName(), null);
        collectAnnotations(source, annotations, new HashSet<>());
        return Collections.unmodifiableMap(annotations);
    }

    private void collectAnnotations(Class<?> source, MultiValueMap<Class<?>, Annotation> annotations, HashSet<Class<?>> seen) {
        if (source != null && seen.add(source)) {
            for (Annotation annotation : source.getDeclaredAnnotations()) {
                if (!AnnotationUtils.isInJavaLangAnnotationPackage(annotation)) {
                    if (ANNOTATION_NAMES
                            .contains(annotation.annotationType().getName())) {
                        annotations.add(source, annotation);
                    }
                    collectAnnotations(annotation.annotationType(), annotations, seen);
                }
            }
            collectAnnotations(source.getSuperclass(), annotations, seen);
        }
    }

    @Override
    public Set<Object> determineImports(AnnotationMetadata metadata) {
        Set<String> result = new LinkedHashSet<>(getCandidateConfigurations(metadata, null));
        return Collections.unmodifiableSet(result);
    }

    @Override
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        List<String> candidates = new ArrayList<>();
        Map<Class<?>, List<Annotation>> annotations = getAnnotations(metadata);
        annotations.forEach((source, sourceAnnotations) -> collectCandidateConfigurations(
                source, sourceAnnotations, candidates));
        return candidates;
    }

    private void collectCandidateConfigurations(Class<?> source, List<Annotation> annotations, List<String> candidates) {
        for (Annotation annotation : annotations) {
            candidates.addAll(getConfigurationsForAnnotation(source, annotation));
        }
    }

    private Collection<String> getConfigurationsForAnnotation(Class<?> source, Annotation annotation) {
        String[] classes = (String[]) AnnotationUtils.getAnnotationAttributes(annotation, true).get("converters");
        if (classes.length > 0) {
            return Arrays.asList(classes);
        }
        return loadFactoryNames(source);
    }

    protected Collection<String> loadFactoryNames(Class<?> source) {
        return SpringFactoriesLoader.loadFactoryNames(source,
                getClass().getClassLoader());
    }
}
