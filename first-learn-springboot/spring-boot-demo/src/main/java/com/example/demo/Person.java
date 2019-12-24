package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author by 谢军<xiejunb@yonyou.com> on 2019/4/18.
 */
@Setter
@Getter
@ToString
public class Person {

    private String given;
    private String family;
    private Address address;
}
