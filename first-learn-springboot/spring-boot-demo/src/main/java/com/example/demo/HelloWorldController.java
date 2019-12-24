package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.List;
import java.util.Map;

/**
 * @author by 谢军<xiejunb@yonyou.com> on 2019/4/16.
 */
@RestController
//@EnableAutoConfiguration
public class HelloWorldController {

//    @RequestMapping(value = "/")
    @RequestMapping("/")
    public String hello() {
        return "hello world 123";
    }

    @RequestMapping("yaml")
    public Object yaml() {
        String yamlStr = "xj: hello world";
        Yaml yaml = new Yaml();
        Object obj = yaml.load(yamlStr);
        System.out.println(obj);
        return obj;
    }

    @RequestMapping("yaml2")
    public Object yaml2() {
        Yaml yaml = new Yaml();
        List<String> obj = (List<String>) yaml.load(this.getClass().getClassLoader()
                .getResourceAsStream("test.yaml"));
        System.out.println(obj);
        return obj;
    }

    @RequestMapping("yaml3")
    public Object yaml3() {
        Yaml yaml = new Yaml();
        //test2 load方法无法处理---标记 需要loadAll方法解析---yaml片段
        Map<String, Object> obj = (Map<String, Object>) yaml.load(this.getClass().getClassLoader()
                .getResourceAsStream("test2.yaml"));
//         Object obj = yaml.loadAll(this.getClass().getClassLoader()
//                .getResourceAsStream("test2.yaml"));
        System.out.println(obj);
        return obj;
    }

    @RequestMapping("yaml4")
    public Object yaml4() {
        Yaml yaml = new Yaml();
        Object obj = yaml.loadAll(this.getClass().getClassLoader()
                .getResourceAsStream("test2.yaml"));
        System.out.println(obj);
        return obj;
    }

    @RequestMapping("yaml5")
    public Object yaml5() {
        Yaml yaml = new Yaml();
        Address obj = yaml.loadAs(this.getClass().getClassLoader()
                .getResourceAsStream("address.yaml"), Address.class);
        System.out.println(obj);
        return obj;
    }

    @RequestMapping("yaml6")
    public Object yaml6() {
        Yaml yaml = new Yaml();
        Person obj = yaml.load(this.getClass().getClassLoader()
                .getResourceAsStream("person.yaml"));
        System.out.println(obj);
        return obj;
    }

    @RequestMapping("yaml7")
    public Object yaml7() {
        Yaml yaml = new Yaml(new Constructor(Person.class));
        Person obj = yaml.load(this.getClass().getClassLoader()
                .getResourceAsStream("person2.yaml"));
        System.out.println(obj);
        return obj;
    }

    @RequestMapping("yaml8")
    public Object yaml8() {
//        Yaml yaml = new Yaml(new Constructor(Person3.class));
//        Person3 obj = (Person3) yaml.load(this.getClass().getClassLoader()
//                .getResourceAsStream("person3.yaml"));

        Constructor cons = new Constructor(Person3.class);
        TypeDescription td = new TypeDescription(Person3.class);
        td.putListPropertyType("address", Address.class);
        cons.addTypeDescription(td);
        Yaml yaml = new Yaml(cons);
        Person3 obj = (Person3) yaml.load(this.getClass().getClassLoader()
                .getResourceAsStream("person3.yaml"));
        System.out.println(obj);
        return obj;
    }
}
