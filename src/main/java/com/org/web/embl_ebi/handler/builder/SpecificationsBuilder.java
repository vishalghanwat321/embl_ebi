package com.org.web.embl_ebi.handler.builder;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote A generic builder class to construct a {@link Specifications}.
 * @since 2020
 */
public class SpecificationsBuilder<T> {

    private Specifications<T> specifications;
    private boolean isEmpty = true;

    public SpecificationsBuilder<T> addSpecification(Specification<T> specification) {
        if (null == specification)
            return this;
        if (isEmpty) {
            specifications = Specifications.where(specification);
            isEmpty = false;
        } else
            specifications = specifications.and(specification);
        return this;
    }

    public Specifications<T> build() {
        return specifications;
    }
}
