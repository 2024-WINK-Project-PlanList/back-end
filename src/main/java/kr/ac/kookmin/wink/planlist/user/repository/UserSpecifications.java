package kr.ac.kookmin.wink.planlist.user.repository;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> searchByEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email + "%");
        };
    }
}
