package com.example.demo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author by 谢军<xiejunb@yonyou.com> on 2019/4/18.
 */

@Getter
@Setter
@ToString
public class Address {
    private String lines;
    private String city;
    private String state;
    private Integer postal;
}
