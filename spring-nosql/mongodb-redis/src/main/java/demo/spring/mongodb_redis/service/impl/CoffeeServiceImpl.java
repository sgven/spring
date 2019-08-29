package demo.spring.mongodb_redis.service.impl;

import demo.spring.mongodb_redis.entity.Coffee;
import demo.spring.mongodb_redis.entity.CoffeeCache;
import demo.spring.mongodb_redis.repository.CoffeeCacheRepository;
import demo.spring.mongodb_redis.repository.CoffeeRepository;
import demo.spring.mongodb_redis.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;

    @Override
    public Coffee save(Coffee entity) {
        return coffeeRepository.save(entity);
    }

    @Override
    public Coffee findById(String id) {
        Optional<CoffeeCache> cache = coffeeCacheRepository.findById(id);
        if (cache.isPresent()) {
            CoffeeCache coffeeCache = cache.get();
            log.info("Coffee {} found in cache", coffeeCache);
            return Coffee.builder()
                    .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
        } else {
            Optional<Coffee> row = coffeeRepository.findById(id);
            row.ifPresent(coffee -> {
                // 设置redis缓存
                CoffeeCache newCache = CoffeeCache.builder()
                        .id(coffee.getId())
                        .name(coffee.getName())
                        .price(coffee.getPrice())
                        .build();
                log.info("Save Coffee {} to cache", coffee);
                coffeeCacheRepository.save(newCache);
            });
            log.info("Coffee {} found in db", row.get());
            return row.get();
        }
    }

    @Override
    public Coffee findByName(String name) {
        Optional<CoffeeCache> cache = coffeeCacheRepository.findOneByName(name);
        if (cache.isPresent()) {
            CoffeeCache coffeeCache = cache.get();
            log.info("Coffee {} found in cache", coffeeCache);
            return Coffee.builder()
                    .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
        } else {
            Coffee row = coffeeRepository.findByName(name);
            // 设置redis缓存
            CoffeeCache newCache = CoffeeCache.builder()
                    .id(row.getId())
                    .name(row.getName())
                    .price(row.getPrice())
                    .build();
            log.info("Save Coffee {} to cache", row);
            coffeeCacheRepository.save(newCache);
            log.info("Coffee {} found in db", row);
            return row;
        }
    }

    @Override
    public void findAll() {
        Iterable<CoffeeCache> caches = coffeeCacheRepository.findAll();
        caches.forEach(cache -> {
            log.info("Coffee {} found in cache", cache);
        });
    }

    @Override
    public void deleteAll() {
        coffeeRepository.deleteAll();
    }
}
