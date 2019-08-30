package demo.spring.mvc.appcontext.context;

import demo.spring.mvc.appcontext.foo.FooConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Slf4j
public class ContextHierarchyApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ContextHierarchyApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 通过AnnotationConfigApplicationContext加载了FooConfig-->>加载了一个上下文叫fooContext
		ApplicationContext fooContext = new AnnotationConfigApplicationContext(FooConfig.class);
		// ClassPathXmlApplicationContext加载了applicationContext.xml-->>加载了一个上下文叫barContext，并声明fooContext为其父context
		ClassPathXmlApplicationContext barContext = new ClassPathXmlApplicationContext(
				new String[] {"applicationContext.xml"}, fooContext);

		// (上级)父context: fooContext，子context: barContext
		/**
		 * 三种情况：
		 * 1.AOP增强切面Bean，配置在父context，并开启了@EnableAspectJAutoProxy AOP支持，子context没有配置切面bean，也没有开启AOP支持
		 *
		 * 即在子context applicationContext.xml中，下面两个配置都被去掉了
		 * <!--<aop:aspectj-autoproxy/>-->
		 * <!--<bean id="fooAspect" class="demo.spring.mvc.appcontext.foo.FooAspect" />-->
		 * 此时子context中的子bean不会被增强
		 *
		 * 2.AOP增强切面Bean，配置在子context，开启了AOP支持，父context没有配置切面Bean
		 *
		 * 即在父context FooConfig中去掉 @Bean FooAspect，在子context applicationContext.xml中，将第一种情况的配置取消注释
		 * 此时父context中的父bean不会被增强
		 *
		 * 3.我希望只在一个context中配置AOP增强，然后在父子context中的bean都能被增强
		 * 可以在父context中配置AOP增强，开启AOP支持，并在子context中开启AOP支持，即可实现父子context中的bean都被AOP增强。
		 *
		 * 即在父context FooConfig中 声明了AOP切面 FooAspect，@EnableAspectJAutoProxy 开启AOP支持
		 * 子context applicationContext.xml中, 也开启AOP支持 <aop:aspectj-autoproxy/>
		 * 此时父子context中的bean都被AOP增强
		 */
		// 父context取父的bean
		TestBean bean = fooContext.getBean("testBeanX", TestBean.class);
		bean.hello();

		log.info("=============");

		bean = barContext.getBean("testBeanX", TestBean.class); // 子context取子的bean
		bean.hello();

		bean = barContext.getBean("testBeanY", TestBean.class); // 子context取父的bean
		bean.hello();
	}
}
