package uz.oliymahad.userservice.service;
import org.springframework.stereotype.Component;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;

/**
 * @param <D> Dto
 * @param <K> Id Long
 * @param <E> Id Long
 * @param <P> Pageable pageable
 */

@Component
public interface BaseService<D ,K,E,P> {

    RestAPIResponse add(D d);
    RestAPIResponse getList(P p);
    RestAPIResponse get(K id);
    RestAPIResponse delete(K id);
    RestAPIResponse edit(K id, D d);
}
