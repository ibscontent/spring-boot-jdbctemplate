package com.uscode.demo;

import com.uscode.demo.org.uscode.dto.Gift;
import com.uscode.demo.org.uscode.respository.GiftJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class UseJdbcTemplateApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(UseJdbcTemplateApplication.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	GiftJdbcRepository giftJdbcRepository;

	public static void main(String[] args) {
		SpringApplication.run(UseJdbcTemplateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("# run ");
		testJdbcTemplateFunctionality();
	}

	private void testJdbcTemplateFunctionality(){
		LOG.info("# 1. drop the gift table ");
		jdbcTemplate.execute("DROP TABLE IF EXISTS public.gift");

		LOG.info("# 2. create the gift table ");
		String query = "CREATE TABLE IF NOT EXISTS public.gift " +
				"(" +
				"    id SERIAL NOT NULL, " +
				"    name character varying(50), " +
				" 	 price numeric(10,2), "+
				" 	 created_date timestamp, "+
				"    CONSTRAINT gift_pkey PRIMARY KEY (id) " +
				")";
		jdbcTemplate.execute(query);

		LOG.info("# 3. insert data into gift table ");
		List<Gift> gifts = Arrays.asList(
						new Gift(1L, "Gift A", 11.23), new Gift(2L, "Gift B", 20.00),
						new Gift(3L, "Gift C", 31.86), new Gift(4L, "Gift D", 120.00)
		);
		gifts.forEach(gift -> {
			int res = giftJdbcRepository.save(gift);
			LOG.info("added: {}, result: {}", gift.getName(), res);
		});

		LOG.info("# 4. find the count of records in the gift table ");
		LOG.info("records count: {}", giftJdbcRepository.getCount());

		LOG.info("# 5. find the price for the 2nd gift in the gift table ");
		LOG.info("the price of the 2nd gift: {}", giftJdbcRepository.getPriceById(2L));

		LOG.info("# 6. find the name for the 4th gift in the gift table ");
		LOG.info("the name of the 4th gift: {}", giftJdbcRepository.getGiftNameById(4L));

		LOG.info("# 7. find all gifts using row mapper");
		LOG.info("all gifts using row mapper: {}", giftJdbcRepository.findAllByRowMapper());

		LOG.info("# 8. find all gifts using lambda expression");
		LOG.info("all gifts using lambda expression: {}", giftJdbcRepository.findAllUsingLambdaExpression());

		LOG.info("# 9. find all gifts using bean property row mapper");
		LOG.info("all gifts using bean property row mapper: {}", giftJdbcRepository.findAllGiftsByBeanPropertyRowMapper());

		LOG.info("# 10. find all gifts using QueryForList");
		LOG.info("all gifts using QueryForList: {}", giftJdbcRepository.findAllGiftsByQueryForList());

		LOG.info("# 11. find the gifts with price over $30");
		LOG.info("the gifts with price over $30: {}", giftJdbcRepository.findAllGiftsOverPrice(30.00));

		LOG.info("# 12. find the gifts with name including C");
		LOG.info("the gifts with name including C: {}", giftJdbcRepository.findAllGiftsWithNameByRowMapper("C"));

	}

}
