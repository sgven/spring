package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author by 谢军<xiejunb@yonyou.com> on 2019/4/18.
 */
@Setter
@Getter
@ToString
public class Person3 {

    private String given;
    private String family;
    private List<Address> address;
}
