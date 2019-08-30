package demo.spring.reactor.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class SimpleReactorApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SimpleReactorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		test1(); // 简单使用reactor
//		test2(); // 测试线程池的使用
//		test3(); // 测试异常
//		test4(); // 测试Backpressure反压力
		test5(); // 测试Backpressure反压力异常的情况

		/**
		 * 在调用 subscribe()方法之前，没有进行任何实际的动作，这是为什么？
		 * 以Flux.map为例子，
		 * 1.实际上map方法里只对传进来的map方法参数中的lambda做了一层封装(onAssembly)，并没有做subscribe
		 * 2.再来看调用subscribe方法后又做了什么-->>onLastAssembly(this).subscribe(Operators.toCoreSubscriber(actual));
		 * -->>FluxMap.subscribe()-->>this.source.subscribe(); 这时才真正做了subscribe动作
		 * 这就是为什么没有做subscribe动作的时候，什么也不会做，subscribe之前的动作其实就是简单的包了一下。
		 */
	}

	private void test1() throws Exception {
		// 通过Flux.range创建了 1 到 6 的序列
		Flux.range(1, 6)
				// doOnRequest时打印请求了多少个数
				.doOnRequest(n -> log.info("Request {} number", n)) // 注意顺序造成的区别
				// 在序列 1-6 publish完了后，打印Publisher COMPLETE 1
				.doOnComplete(() -> log.info("Publisher COMPLETE 1"))
//				.publishOn(Schedulers.elastic())
				.map(i -> {// map可以实现元素的转换，这里主要是为了演示map的执行是在哪个线程上发生的，所以就打印线程的信息
					log.info("Publish {}, {}", Thread.currentThread(), i);
//					return 10 / (i - 3);
					return i;
				})
				// 再执行一个doOnComplete，可以看看这两个doOnComplete分别是执行在哪个线程上的
				.doOnComplete(() -> log.info("Publisher COMPLETE 2"))
//				.subscribeOn(Schedulers.single())
//				.onErrorResume(e -> {
//					log.error("Exception {}", e.toString());
//					return Mono.just(-1);
//				})
//				.onErrorReturn(-1)
				// 最后执行subscribe
				.subscribe(i -> log.info("Subscribe {}: {}", Thread.currentThread(), i),
						e -> log.error("error {}", e.toString()),
						() -> log.info("Subscriber COMPLETE")//,
//						s -> s.request(4)
				);
		Thread.sleep(2000);
	}

	private void test2() throws Exception {
		// 通过Flux.range创建了 1 到 6 的序列
		Flux.range(1, 6)
				// doOnRequest时打印请求了多少个数
				.doOnRequest(n -> log.info("Request {} number", n)) // 注意顺序造成的区别
				.doOnComplete(() -> log.info("Publisher COMPLETE 1"))
				/**
				 * 每次publishOn执行后，都会影响后续代码的执行线程
				 * 在执行publishOn后，publishOn后面的代码，比如map，就都会执行在elastic线程池里面了
				 * 当然也可以在不同的地方再继续增加publishOn，改变后续代码的执行线程
				 */
				.publishOn(Schedulers.elastic())
				.map(i -> {
					log.info("Publish {}, {}", Thread.currentThread(), i);
//					return 10 / (i - 3);
					return i;
				})
				.doOnComplete(() -> log.info("Publisher COMPLETE 2"))
				// 让一个单独的线程来做订阅subscribe
				.subscribeOn(Schedulers.single())
//				.onErrorResume(e -> {
//					log.error("Exception {}", e.toString());
//					return Mono.just(-1);
//				})
//				.onErrorReturn(-1)
				.subscribe(i -> log.info("Subscribe {}: {}", Thread.currentThread(), i),
						e -> log.error("error {}", e.toString()),
						() -> log.info("Subscriber COMPLETE")//,
//						s -> s.request(4)
				);
		Thread.sleep(2000);
	}

	private void test3() throws Exception {
		// 通过Flux.range创建了 1 到 6 的序列
		Flux.range(1, 6)
				// doOnRequest时打印请求了多少个数
				.doOnRequest(n -> log.info("Request {} number", n)) // 注意顺序造成的区别
				.doOnComplete(() -> log.info("Publisher COMPLETE 1"))
				.publishOn(Schedulers.elastic())
				.map(i -> {
					log.info("Publish {}, {}", Thread.currentThread(), i);
					return 10 / (i - 3); // 为了测试异常，我们人为制造异常，当i=3时，分母是0会出现异常
//					return i;
				})
				.doOnComplete(() -> log.info("Publisher COMPLETE 2")) // 由于第二次pulish发生了异常，所以没有打印Publisher COMPLETE 2
				.subscribeOn(Schedulers.single())
				/**
				 * 希望用一段代码来处理异常时，就用
				 * onErrorResume：用一段特定的lambda来做异常处理
				 */
				.onErrorResume(e -> {
					log.error("Exception {}", e.toString());
					return Mono.just(-1);
				})
				/**
				 * 发生错误时，直接返回默认值
				 * 当Publish 3时， Subscribe订阅到的就是-1
				 */
//				.onErrorReturn(-1)
				.subscribe(i -> log.info("Subscribe {}: {}", Thread.currentThread(), i),
						e -> log.error("error {}", e.toString()),
						() -> log.info("Subscriber COMPLETE")//,
//						s -> s.request(4)
				);
		Thread.sleep(2000);
	}

	private void test4() throws Exception {
		// 通过Flux.range创建了 1 到 6 的序列
		// Flux创建了6个元素的序列，结果是只publish了4个，还有剩余的元素没有被消费掉，所以publish和Subscriber都没有完成
		Flux.range(1, 6)
				// 注意：这个publishOn挪上来了
				.publishOn(Schedulers.elastic())
				.doOnRequest(n -> log.info("Request {} number", n)) // 注意顺序造成的区别
//				.publishOn(Schedulers.elastic())
				.doOnComplete(() -> log.info("Publisher COMPLETE 1"))
				.map(i -> {
					log.info("Publish {}, {}", Thread.currentThread(), i);
//					return 10 / (i - 3);
					return i;
				})
				.doOnComplete(() -> log.info("Publisher COMPLETE 2"))
				.subscribeOn(Schedulers.single())
				.onErrorResume(e -> {
					log.error("Exception {}", e.toString());
					return Mono.just(-1);
				})
//				.onErrorReturn(-1)
				.subscribe(i -> log.info("Subscribe {}: {}", Thread.currentThread(), i),
						e -> log.error("error {}", e.toString()),
						() -> log.info("Subscriber COMPLETE"),
						s -> s.request(4) // 一次只去request4个
				);
		Thread.sleep(2000);
	}

	private void test5() throws Exception {
		// 通过Flux.range创建了 1 到 6 的序列
		Flux.range(1, 6)
				// 注意：这个publishOn挪上来了
				.publishOn(Schedulers.elastic())
				.doOnRequest(n -> log.info("Request {} number", n)) // 注意顺序造成的区别
//				.publishOn(Schedulers.elastic())
				.doOnComplete(() -> log.info("Publisher COMPLETE 1"))
				.map(i -> {
					log.info("Publish {}, {}", Thread.currentThread(), i);
					return 10 / (i - 3); // 抛出异常的情况，request4个，在publish第3个的时候发生异常，打印Subscriber COMPLETE，Subscribe就提前结束了。
//					return i;
				})
				.doOnComplete(() -> log.info("Publisher COMPLETE 2"))
				.subscribeOn(Schedulers.single())
				.onErrorResume(e -> {
					log.error("Exception {}", e.toString());
					return Mono.just(-1);
				})
//				.onErrorReturn(-1)
				.subscribe(i -> log.info("Subscribe {}: {}", Thread.currentThread(), i),
						e -> log.error("error {}", e.toString()),
						() -> log.info("Subscriber COMPLETE"),
						s -> s.request(4) // 一次只去request4个
				);
		Thread.sleep(2000);
	}
}

