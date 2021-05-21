# RestTemplate高阶用法

## 传递 HTTP Header
想要HTTP请求or响应 带上HTTP头怎么做？

- RestTemplate.exchange()

- RequestEntity<T> / ResponseEntity<T>

在RestTemplate.exchange()方法中，是可以传入RequestEntity的，
RequestEntity它里面就可以带入一些请求头。

>示例：

    String uriTemplate = "http://example.com/hotels/{hotel}";
    URI uri = UriComponentsBuilder.fromUriString(uriTemplate).build(42);
    
    //RequestEntity.get，取到的是get的RequestEntity，泛型是Void
    //如果是post，则泛型就是post指定的泛型了
    RequestEntity<Void> requestEntity = RequestEntity.get(uri)
            .header("MyRequestHeader", "MyValue")
            .build();
    
    ResponseEntity<String> response = template.exchange(requestEntity, String.class);
    
    String responseHeader = response.getHeaders().getFirst("MyResponseHeader");
    String body = response.getBody();


## 类型转换
怎么处理类型转换？

- JsonSerializer / JsonDeserializer

- @JsonComponent

提供相应的 JsonSerializer 和 JsonDeserializer，
并在相应的Serializer类上添加@JsonComponent注解，就可以处理相应的类型转换了。

## 解析泛型对象

- RestTemplate.exchange()

- ParameterizedTypeReference<T>

提供一个 ParameterizedTypeReference 对象(ptr)，通过它指定泛型，
然后在RestTemplate.exchange()方法中传入这个ptr对象。在对response结果解析时，就会解析成泛型对象。


